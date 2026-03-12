package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class ShooterDoors extends SubsystemBase {
    private Servo shootDoorLeft, shootDoorRight;
    private boolean shootL, shootR;
    public ShooterDoors(Servo shootDoorLeft, Servo shootDoorRight){
        this.shootDoorLeft = shootDoorLeft;
        this.shootDoorRight = shootDoorRight;
    }

    public void shootLeft(boolean shootL){
        if (!shootL) {
            shootDoorLeft.setPosition(.7);
        } else {
            shootDoorLeft.setPosition(.39);
        }
        TelemetryUtil.addData("Hsoidfhiosdhfosdif ", shootL);
    }
    public void shootRight(boolean shootR){
        if (!shootR) {
            shootDoorRight.setPosition(.39);
        } else {
            shootDoorRight.setPosition(.7);
        }
    }
}
