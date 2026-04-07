package org.firstinspires.ftc.teamcode.catscan.subsystems;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.command.WaitCommand;

@Config
public class TheDoors extends SubsystemBase {
    private Servo doorLeft, doorRight;
    private NormalizedColorSensor  colorSensorR;
    private boolean leftUp = false, rightUp = false;
    private boolean sortOn = false;
    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    double gateWaitTime = 1.25;
    public TheDoors(Servo doorLeft, Servo doorRight,  NormalizedColorSensor colorSensorR){
        this.colorSensorR = colorSensorR;
        this.doorLeft = doorLeft;
        this.doorRight = doorRight;
    }
    public TheDoors(Servo doorLeft, Servo doorRight){
        this.doorLeft = doorLeft;
        this.doorRight = doorRight;
    }

    public void open(boolean leftUp, boolean rightUp){
        this.rightUp = rightUp;
        this.leftUp = leftUp;
        if(leftUp){
            doorLeft.setPosition(.78);//up
        } else {
            doorLeft.setPosition(.6);//down
        }
        if(rightUp){
            doorRight.setPosition(.74);//up
        } else {
            doorRight.setPosition(.91);//down
        }
    }
    public void setSortOn(boolean sortOn){
        this.sortOn = sortOn;
    }

    public boolean getSortOn(){
        return sortOn;
    }

    public void periodic(){
        /*
        if(sortOn){
            float gain = 5;
            final float[] hsv2 = new float[3];

            colorSensorR.setGain(gain);
            NormalizedRGBA rgb2 = colorSensorR.getNormalizedColors();
            Color.colorToHSV(rgb2.toColor(), hsv2);
            TelemetryUtil.addData("hsv2",hsv2[0]);
            if ((hsv2[0] >= 142 && hsv2[0] < 250)) {
                //purple
                TelemetryUtil.addData("purple");
                doorLeft.setPosition(.6);//down
                doorRight.setPosition(.74);//up
                new WaitCommand(500).schedule();
                gateTimer.reset();
            } else if ((hsv2[0] > 115 && hsv2[0] < 190)) {
                //green
                TelemetryUtil.addData("green");
                doorLeft.setPosition(.78);//up
                doorRight.setPosition(.91);//down
                new WaitCommand(500).schedule();
                gateTimer.reset();
            } else {
                if (gateTimer.seconds() > gateWaitTime) {
                    //whatever we wanna do when nothing is sensed
                }
            }



        }
         */
    }
    //right is purple, left is green
}