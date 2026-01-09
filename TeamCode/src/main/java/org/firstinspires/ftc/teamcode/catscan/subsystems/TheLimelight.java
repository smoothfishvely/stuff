package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import java.util.List;

public class TheLimelight extends SubsystemBase {
    Limelight3A limelight;
    private int motifId;
    public TheLimelight(Limelight3A limelight){
        this.limelight = limelight;
    }

    public int getMotif(){
        return motifId;
    }

    public void setPipeline(int p){
        limelight.pipelineSwitch(p);
    }

    @Override
    public void periodic(){
        LLResult result = limelight.getLatestResult();
        if(result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                motifId = fr.getFiducialId();
            }
        } else {
            TelemetryUtil.addData("ll result invalid");
        }
    }
}
