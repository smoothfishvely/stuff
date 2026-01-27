package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import java.util.List;

public class TheLimelight extends SubsystemBase {
    Limelight3A limelight;
    private int motifId;
    private double ty, tx;
    private double llHeightCM = 39.399972;
    private static double aimTolerance = 0.5;
    private static double aimKp = 0; //tune pids
    private static double aimKI = 0;
    private static double aimKd = 0;
    private static double aimKs = 0;
    private double aimIntegral = 0; //not a pid value
    private double aimLastError = 0;
    double lastVel = 1300;
    LLResult result;

    private double degreeOffset = 0; //adjust
    private final ElapsedTime aimTimer = new ElapsedTime();
    public TheLimelight(Limelight3A limelight){
        this.limelight = limelight;
    }

    public int getMotif(){
        return motifId;
    }

    public double getTy() {return ty;}

    public double getTx() {return tx;}



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
                    + (aimKd * aimDerivative) + (aimKs * Math.signum(error));
            output = Range.clip(output, -1, 1);// limits within -1, 1
            return output;
        }
        else {
            return 0;
        }
    }

    public void setPipeline(int p){
        limelight.pipelineSwitch(p);
    }

    @Override
    public void periodic(){
        result = limelight.getLatestResult();
        if(result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                motifId = fr.getFiducialId();
                ty = fr.getTargetYDegrees();
                tx = fr.getTargetXDegrees();
            }
        } else {
            TelemetryUtil.addData("ll result invalid");
        }
    }
    public double getGoalDistanceCM() {
        if (result.isValid()) {
            return ((llHeightCM - 74.93) / (Math.tan(ty)));
        }
        else {
            return 172;
        }
    }
    //need to calculate coefficients for power and stuff
    /*public double distanceBasedVelocity() {
        if  (result.isValid()) {

        }
    }*/
}