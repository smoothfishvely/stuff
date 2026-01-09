package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TheDoors extends SubsystemBase {
    private Servo doorLeft, doorRight;
    private NormalizedColorSensor colorSensor;
    private boolean leftUp = false, rightUp = false;
    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    double gateWaitTime = 1.25;
    public TheDoors(Servo doorLeft, Servo doorRight, NormalizedColorSensor colorSensor){
        this.colorSensor = colorSensor;
        this.doorLeft = doorLeft;
        this.doorRight = doorRight;
    }

    public void open(boolean leftUp, boolean rightUp){
        this.rightUp = rightUp;
        this.leftUp = leftUp;
        if(leftUp){
            doorLeft.setPosition(.80);//up
        } else {
            doorLeft.setPosition(.45);//down
        }
        if(rightUp){
            doorRight.setPosition(.45);//up
        } else {
            doorRight.setPosition(.80);//down
        }
    }

    //right is purple, left is green
}