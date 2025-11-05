package org.firstinspires.ftc.teamcode.catscan4102.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheIntake extends SubsystemBase {
    private DcMotorEx intake;
    public TheIntake(DcMotorEx intake){
        this.intake = intake;
    }

    public void on(){
        intake.setPower(1);
    }

    public void off(){
        intake.setPower(0);
    }
}
