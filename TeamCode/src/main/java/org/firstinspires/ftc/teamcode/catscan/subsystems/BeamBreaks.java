package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class BeamBreaks extends SubsystemBase {
    private DigitalChannel rightTopBB, rightMidBB, leftTopBB, bottomBB;
    private int numBalls;
    private boolean rightTop, rightMid, leftTop, bottom;
    public BeamBreaks (DigitalChannel rightTopBB, DigitalChannel rightMidBB,
                       DigitalChannel leftTopBB, DigitalChannel bottomBB) {
        this.rightTopBB = rightTopBB;
        this.rightMidBB = rightMidBB;
        this.leftTopBB = leftTopBB;
        this.bottomBB = bottomBB;
    }

    public int getNumBalls() {
        numBalls = 0;

        if (rightTop) numBalls++;
        if (rightMid) numBalls++;
        if (leftTop) numBalls++; // redundant i think bc we wont use this in tele
        if (bottom) numBalls++;

        return numBalls;
    }

    @Override
    public void periodic() {

        rightTop = rightTopBB.getState();
        rightMid = rightMidBB.getState();
        leftTop = leftTopBB.getState();
        bottom = bottomBB.getState();

        TelemetryUtil.addData("right top:" + rightTop);
        TelemetryUtil.addData("right mid:" + rightMid);
        TelemetryUtil.addData("left top:" + leftTop);
        TelemetryUtil.addData("bottom:" + bottom);
    }

}
