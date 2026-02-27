package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class BeamBreaks extends SubsystemBase {
    private DigitalChannel rightTopBB, rightMidBB, leftTopBB, bottomBB;
    private int numBalls;
    public boolean rightTop = false;
    public boolean rightMid = false;
    public boolean leftTop = false;
    public boolean bottom = false;



    public boolean realRightTop, realRightMid, realLeftTop, realBottom;
    public BeamBreaks (DigitalChannel rightTopBB, DigitalChannel rightMidBB,
                       DigitalChannel leftTopBB, DigitalChannel bottomBB) {

        this.rightTopBB = rightTopBB;
        this.rightMidBB = rightMidBB;
        this.leftTopBB = leftTopBB;
        this.bottomBB = bottomBB;
        this.rightTopBB.setMode(DigitalChannel.Mode.INPUT);
        this.rightMidBB.setMode(DigitalChannel.Mode.INPUT);
        this.leftTopBB.setMode(DigitalChannel.Mode.INPUT);
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
    }

    @Override
    public void periodic() {

        rightTop = rightTopBB.getState();
        rightMid = rightMidBB.getState();
        leftTop = leftTopBB.getState();
        bottom = bottomBB.getState();

        if (rightTop) {
            realRightTop = true;
        }
        if (rightMid && realRightTop) {
            realRightMid = true;
        }
        if (bottom && realRightMid && realRightTop) {
            realBottom = true;
        }

        numBalls = 0;
        if (rightTop) numBalls++;
        if (rightMid) numBalls++;
        if (bottom) numBalls++;

        TelemetryUtil.addData("right top:", rightTop);
        TelemetryUtil.addData("right mid:", rightMid);
        TelemetryUtil.addData("left top:", leftTop);
        TelemetryUtil.addData("bottom:", bottom);
    }
}
