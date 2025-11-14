package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheKickerLeft extends SubsystemBase {
    private Servo kickLeft;
    public boolean up = false;
    public TheKickerLeft(Servo kickLeft){
        this.kickLeft = kickLeft;
    }

    public void setUp(){
        up = !up;
    }

    @Override
    public void periodic(){
        if(up){
            kickLeft.setPosition(.43);
        } else {
            kickLeft.setPosition(.65);
        }
    }
}