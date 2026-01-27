package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TheTransfer extends SubsystemBase {
    private DcMotorEx transfer;
    private boolean on;
    public TheTransfer(DcMotorEx transfer){
        this.transfer = transfer;
    }

    public void setOn(){
        on = !on;
        if(on){
            transfer.setPower(.54);
        } else {
            transfer.setPower(0);
        }
    }
}
