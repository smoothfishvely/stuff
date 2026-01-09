package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class TheIntake extends SubsystemBase {
    private DcMotorEx intake;
    boolean on = false;
    public TheIntake(DcMotorEx intake){
        this.intake = intake;
    }

    public void setOn(){
        on = !on;
        if(on){
            intake.setPower(1);
        } else {
            intake.setPower(0);
        }
    }
}
