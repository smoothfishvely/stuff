package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TheHood extends SubsystemBase {
    private Servo hoodLeft, hoodRight;
    private double hoodPos;
    public TheHood (Servo hoodLeft, Servo hoodRight) {
        this.hoodLeft = hoodLeft;
        this.hoodRight = hoodRight;
        hoodPos = .2;
    }

    public void up(){
        hoodPos += .02;
    }

    public void down(){
        hoodPos -= .02;
    }

    @Override
    public void periodic(){
        hoodLeft.setPosition(hoodPos);
        hoodRight.setPosition(hoodPos);
    }
}
