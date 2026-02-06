package org.firstinspires.ftc.teamcode.catscan.subsystems;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

@Configurable
public class TheShooter extends SubsystemBase {
    MotorEx shooterLeft, shooterRight;
    TelemetryManager tm;
    public static double kp = 0.0029;
    public static double ki = 0;
    public static double kd = 0.00003;
    public static double kf = 0.0005;
    PIDFController epstein;
    public static double velocity = 0;
    public TheShooter(MotorEx shooterLeft, MotorEx shooterRight){
        tm = PanelsTelemetry.INSTANCE.getTelemetry();
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
        epstein = new PIDFController(kp,ki,kd,kf);
    }

    public double getVelocity(){
        return velocity;
    }

    public void setVelocity(double vel){
        velocity = vel;
    }

    public void add(){
        velocity += 20;
    }

    public void subtract(){
        velocity -= 20;
    }

    @Override
    public void periodic(){
        if(velocity != 0) {
            epstein.setPIDF(kp, ki, kd, kf);
            double power = epstein.calculate(shooterLeft.getVelocity(), velocity);
            shooterLeft.set(power);
            shooterRight.set(power);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
        }
        tm.debug("velocity", shooterLeft.getVelocity());
        tm.debug("target v", velocity);
        tm.update();
    }
}
