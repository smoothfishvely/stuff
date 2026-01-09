package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TheHood extends SubsystemBase {
    private Servo hoodLeft, hoodRight;
    private double hoodPos;
    private double hoodPosL, hoodPosR;
    public TheHood (Servo hoodLeft, Servo hoodRight) {
        this.hoodLeft = hoodLeft;
        this.hoodRight = hoodRight;
        hoodPos = .2;
        hoodPosL = .2;
        hoodPosR = .81;
    }

    public void up(){
        hoodPosL += .05;
        hoodPosR -= .05;
    }

    public void down(){
        hoodPosL -= .05;
        hoodPosR += .05;
    }

    public double getPos(){
        return hoodPosL;
    }

    public void setPos(double posL, double posR){
        hoodPosL = posL;
        hoodPosR = posR;
        hoodLeft.setPosition(hoodPosL);
        hoodRight.setPosition(hoodPosR);
    }

    public void setPos(){
        hoodLeft.setPosition(hoodPosL);
        hoodRight.setPosition(hoodPosR);
    }
}
