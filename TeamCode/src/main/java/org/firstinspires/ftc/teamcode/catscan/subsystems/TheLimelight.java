package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import java.util.List;
@Configurable
public class TheLimelight extends SubsystemBase {
    Limelight3A limelight;
    private int motifId;
    private double ty, tx;
    private double targetHeight = .3429;

    private static double aimTolerance = 0.5;
    private double aimIntegral = 0; //not a pid value
    private double aimLastError = 0;
    double lastVel = 1300;
    LLResult result;

    private static double degreeOffset = 0; //adjust
    private final ElapsedTime aimTimer = new ElapsedTime();
    public TheLimelight(Limelight3A limelight){
        this.limelight = limelight;
        result = limelight.getLatestResult();
    }

    public int getMotif(){
        return motifId;
    }

    public double getTy() {return ty;}

    public double getTx() {return tx;}
/*
    public double AimPID() {
        if (result.isValid()) {
            double timeDiff = aimTimer.seconds();
            aimTimer.reset();

            double error = tx - degreeOffset;

            aimIntegral += error * timeDiff;

            double aimDerivative = (error - aimLastError) / timeDiff;
            aimLastError = error;

            //checks for tolerance
            if (Math.abs(error) < aimTolerance) {
                aimIntegral = 0;
                return 0;
            }

            double output = aimKp * error + (aimKI * aimIntegral)
                    + (aimKd * aimDerivative) + (aimKf * Math.signum(error));
            output = Range.clip(output, -1, 1);// limits within -1, 1
            return output;
        }
        else {
            return 0;
        }
    }*/

    public void setPipeline(int p){
        limelight.pipelineSwitch(p);
    }
    public double getGoalDistanceM() {
        return ((targetHeight) / (Math.tan(Math.toRadians(ty))));
    }

    @Override
    public void periodic(){
        result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            motifId = fr.getFiducialId();
        }
        ty = result.getTy();
        tx = result.getTx();
        if(!result.isValid()){
            TelemetryUtil.addData("ll result invalid");
        }
    }
}