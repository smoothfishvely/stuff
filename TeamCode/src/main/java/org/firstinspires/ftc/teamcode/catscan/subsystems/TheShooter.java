package org.firstinspires.ftc.teamcode.catscan.subsystems;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

@Configurable
public class   TheShooter extends SubsystemBase {
    MotorEx shooterLeft, shooterRight;
    public static double kp = 0.03;
    public static double ki = 0;
    public static double kd = 0.0012;
    public static double kf = 0.001;
    public static double newKp = 0.005;
    public static double newKi = 0;
    public static double newKd = 0;
    public static double newKs = 0.087;
    public static double Kv = 0.00045;
    PIDFController epstein;
    public static double targetVelocity;
    private final ElapsedTime aimTimer = new ElapsedTime();
    double timeDiff;
    private double pidIntegral = 0; //not a pid value
    private double lastError = 0;
    private double error = 0;
    public TheShooter(MotorEx shooterLeft, MotorEx shooterRight){
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
        epstein = new PIDFController(kp, ki, kd, kf);
        targetVelocity = 0;
    }

    public double calculate() {
            error = targetVelocity - shooterLeft.getVelocity();

            pidIntegral += error * timeDiff;
            pidIntegral = Range.clip(pidIntegral, -1, 1);

            double derivative = (error - lastError) / timeDiff;

            lastError = error;
            double feedforward = (newKs * Math.signum(error)) + (Kv * targetVelocity);
            double output = (error * newKp) + (newKi * pidIntegral) +
                    (newKd * derivative) + feedforward;


            output = Range.clip(output, -1, 1);

            //sigma sigma boy sigma boy sigma boy

            return output;
    }

    public double getTargetVelocity(){
        return targetVelocity;
    }

    public double getVelocity() {
        return shooterLeft.getVelocity();
    }

    public void setVelocity(double vel){
        targetVelocity = vel;
    }
    public void add(){
        targetVelocity += 20;
    }

    public void subtract(){
        targetVelocity -= 20;
    }

    @Override
    public void periodic(){

        /*
        if(targetVelocity != 0) {
            epstein.setPIDF(kp, ki, kd, kf);
            double power = epstein.calculate(shooterLeft.getVelocity(), targetVelocity);
            shooterLeft.set(power);
            shooterRight.set(power);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
        }
         */
        if(targetVelocity != 0) {
            double power = calculate();
            shooterLeft.set(power);
            shooterRight.set(power);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
        }


        timeDiff = aimTimer.seconds();
        aimTimer.reset();
    }
}
