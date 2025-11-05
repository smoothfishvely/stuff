package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheKickerLeft extends SubsystemBase {
    private Servo kickLeft;
    public TheKickerLeft(Servo kickLeft){
        this.kickLeft = kickLeft;
    }

    public void up(){
        kickLeft.setPosition(.5+(40.0/355));
    }

    public void down(){
        kickLeft.setPosition(.5);
    }
}