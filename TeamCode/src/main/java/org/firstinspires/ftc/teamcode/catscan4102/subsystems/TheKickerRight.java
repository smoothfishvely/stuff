package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheKickerRight extends SubsystemBase {
    private Servo kickRight;
    public TheKickerRight(Servo kickRight){
        this.kickRight = kickRight;
    }

    public void up(){
        kickRight.setPosition(.54);
    }

    public void down(){
        kickRight.setPosition(.5-(40.0/355));
    }
}