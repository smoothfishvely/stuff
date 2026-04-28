package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheLights extends SubsystemBase {

    private Servo rightLED, midLED, leftLED;

    public TheLights(Servo rightLED, Servo midLED, Servo leftLED) {
        this.rightLED = rightLED;
        this.midLED = midLED;
        this.leftLED = leftLED;
    }

    public void setIndividualPower(double rightPower, double midPower, double leftPower) {
        rightLED.setPosition(rightPower);
        midLED.setPosition(midPower);
        leftLED.setPosition(leftPower);
    }

    public void celebration(){
        for(int v = 0; v < 3; v++) {
            for (double i = 0; i < 1; i += .02) {
                setIndividualPower(i, i, i);
            }
            for(double i = 1; i > 0; i -= .02){
                setIndividualPower(i, i, i);
            }
        }
    }
}
