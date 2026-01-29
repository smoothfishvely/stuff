package org.firstinspires.ftc.teamcode.catscan.subsystems;
import com.bylazar.configurables.annotations.Configurable;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

@Configurable
public class TheShooter extends SubsystemBase {
    MotorEx shooterLeft, shooterRight;
    public static double kp = 1.3;
    public static double ki = 0;
    public static double kd = 1.25;
    public static double ks = 232;
    public static double kv = 1.2;
    public static double ka = 0;
    double velocity;
    public TheShooter(MotorEx shooterLeft, MotorEx shooterRight){
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
        velocity = 0;
    }

    public double getVelocity(){
        return velocity;
    }

    public void setVelocity(double vel){
        velocity = vel;
    }

    public void add(){
        velocity += 30;
    }

    public void subtract(){
        velocity -= 30;
    }

    @Override
    public void periodic(){
        shooterLeft.setVelocity(velocity);
        shooterRight.setVelocity(velocity);
        shooterRight.setVeloCoefficients(kp, ki, kd);
        shooterLeft.setVeloCoefficients(kp, ki, kd);
        shooterRight.setFeedforwardCoefficients(ks, kv, ka);
        shooterLeft.setFeedforwardCoefficients(ks, kv, ka);
    }
}