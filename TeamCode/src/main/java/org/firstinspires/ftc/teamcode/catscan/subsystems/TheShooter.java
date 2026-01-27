package org.firstinspires.ftc.teamcode.catscan.subsystems;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class TheShooter extends SubsystemBase {
    MotorEx shooterLeft, shooterRight;
    double shooterPower;
    boolean on = false;
    public TheShooter(MotorEx shooterLeft, MotorEx shooterRight){
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
        shooterPower = 1;
    }

    public double getShooterPower(){
        return shooterPower;
    }

    public void setOn(){
        on = !on;
    }

    public void setOn(boolean on){
        this.on = on;
    }

    public boolean getOn(){
        return on;
    }

    public void setPower(double power){
        shooterPower = power;
    }


    public void add(){
        shooterPower += .05;
    }

    public void subtract(){
        shooterPower -= .05;
    }

    @Override
    public void periodic(){
        if(on){
            shooterLeft.set(shooterPower);
            shooterRight.set(shooterPower);
        } else {
            shooterLeft.set(0);
            shooterRight.set(0);
        }
    }
}