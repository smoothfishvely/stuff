package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.SensorDigitalDevice;

public class BeamBreaks extends SubsystemBase {
    private DigitalChannel rightTopBB, rightMidBB, intakeBB, bottomBB;
    private int numBalls;
    public boolean rightTop = false;
    public boolean rightMid = false;
    public boolean intake = false;
    public boolean bottom = false;
    private final ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);



    public boolean realRightTop, realRightMid, realIntake, realBottom;
    public BeamBreaks (DigitalChannel rightTopBB, DigitalChannel rightMidBB,
                       DigitalChannel intakeBB, DigitalChannel bottomBB) {

        this.rightTopBB = rightTopBB;
        this.rightMidBB = rightMidBB;
        this.intakeBB = intakeBB;
        this.bottomBB = bottomBB;
        this.rightTopBB.setMode(DigitalChannel.Mode.INPUT);
        this.rightMidBB.setMode(DigitalChannel.Mode.INPUT);
        this.intakeBB.setMode(DigitalChannel.Mode.INPUT);
        this.bottomBB.setMode(DigitalChannel.Mode.INPUT);


    }

    public int getNumBalls() {
        return numBalls;
    }

    public boolean isAllFalse() {
        if (numBalls == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setFalse() {
        realRightTop = false;
        realRightMid = false;
        realBottom = false;
        realIntake = false;
    }

    @Override
    public void periodic() {

        rightTop = rightTopBB.getState();
        rightMid = rightMidBB.getState();
        intake = intakeBB.getState();
        bottom = bottomBB.getState();

        if (rightTop) {
            realRightTop = true;
        }
        if (rightMid && rightTop) {
            realRightMid = true;
        }
        if (bottom && rightMid && rightTop) {
            realBottom = true;
        }
        if (intake && bottom && rightMid && rightTop) {
            realIntake = true;
        }

        numBalls = 0;
        if (realRightTop) numBalls++;
        if (realRightMid) numBalls++;
        if (bottom) numBalls++;
        if (intake) numBalls++;

        //TelemetryUtil.addData("right top:", rightTop);
        //TelemetryUtil.addData("right mid:", rightMid);
        //TelemetryUtil.addData("intake:", intake);
        //TelemetryUtil.addData("bottom:", bottom);
    }
}
