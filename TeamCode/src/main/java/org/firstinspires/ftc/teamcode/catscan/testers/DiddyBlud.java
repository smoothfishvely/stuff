package org.firstinspires.ftc.teamcode.catscan.testers;

import com.bylazar.configurables.annotations.Configurable;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

@Configurable
public class DiddyBlud extends SubsystemBase {
    MotorEx shooterLeft, shooterRight;

    public static double kp = 1.3;
    public static double ki = 0;
    public static double kd = 1.25;
    public static double ks = 0;
    public static double kv = 0;
    public static double ka = 0;
    PIDFController epstein = new PIDController(kp,ki,kd);
    SimpleMotorFeedforward feedforward =
            new SimpleMotorFeedforward(ks, kv, ka);
    public static double targetVelocity;

    public DiddyBlud(MotorEx shooterLeft, MotorEx shooterRight){
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;

    }

    @Override
    public void periodic(){
        epstein.setP(kp);
        epstein.setI(ki);
        epstein.setD(kd);
        epstein.setSetPoint(targetVelocity);
        double power = epstein.calculate(shooterRight.getVelocity()) + feedforward.calculate(targetVelocity, 10); //2nd valued is acceleration so idk what to do wtith that
        shooterLeft.set(power);
        shooterRight.set(power);
    }
}