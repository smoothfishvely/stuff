package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheKickerRight extends SubsystemBase {
    private Servo kickRight;
    private boolean up = false;
    public TheKickerRight(Servo kickRight){
        this.kickRight = kickRight;
    }

    public void setUp(){
        up = !up;
    }

    @Override
    public void periodic(){
        if(up){
            kickRight.setPosition(.55);
        } else {
            kickRight.setPosition(.35);
        }
    }
}