package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TheTransfer extends SubsystemBase {
    private DcMotorEx transfer;
    public TheTransfer(DcMotorEx transfer){
        this.transfer = transfer;
    }
    public void setPower(double power){
        transfer.setPower(power);
    }
    public void setOn(boolean on){
        if(on){
            transfer.setPower(.53);
        } else {
            transfer.setPower(.2);
        }
    }
}
//adb connect 192.168.43.1:5555
