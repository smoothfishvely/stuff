package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;
@Configurable
public class TheLimelight extends SubsystemBase {
    Limelight3A limelight;
    private int motifId;
    private double ty, tx;
    private double targetHeight = .3429;
    double power;
    Pose3D pose = new Pose3D(new Position(DistanceUnit.INCH, 0,0,0,0),
            new YawPitchRollAngles(AngleUnit.DEGREES, 0,0,0,0));

    private static double aimTolerance = 0.67;
    private static double kp = .025;
    private static double ki = 0;
    private static double kd = .0038;
    private static double kf = .02;
    PIDFController pidf = new PIDFController(kp,ki,kd,kf);

    double lastVel = 1300;
    private double aimIntegral = 0; //not a pid value
    private double aimLastError = 0;


    LLResult result;

    private static double degreeOffset = -5; //adjust
    private final ElapsedTime aimTimer = new ElapsedTime();
    double timeDiff;
    public TheLimelight(Limelight3A limelight){
        this.limelight = limelight;
        result = limelight.getLatestResult();
    }

    public int getMotif(){
        return motifId;
    }

    public double getTy() {return ty;}

    public double getTx() {return tx;}

    public double AimPID() {

            double error = tx - degreeOffset;

            aimIntegral += error * timeDiff;

            double aimDerivative = (error - aimLastError) / timeDiff;
            aimLastError = error;

            //checks for tolerance
            if (Math.abs(error) < aimTolerance) {
                aimIntegral = 0;
                return 0;
            }

            double output = kp * error + (ki * aimIntegral)
                    + (kd * aimDerivative) + (kf * Math.signum(error));
            output = Range.clip(output, -1, 1);// limits within -1, 1
            return output;
    }

    public void setPipeline(int p){
        limelight.pipelineSwitch(p);
    }
    public void setDegreeOffset(double degree) {
        degreeOffset = degree;
    }
    public double getGoalDistanceM() {
        return ((targetHeight) / (Math.tan(Math.toRadians(ty))));
    }

    public double getAimPower() {
        power = Range.clip(power, -1, 1); // maxes power at -1 and 1
        return power;
    }
    public Pose3D getBotPose() {
        return pose;
    }

    @Override
    public void periodic(){
        result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            motifId = fr.getFiducialId();
        }
        tx = result.getTx();
        ty = result.getTy();
        timeDiff = aimTimer.seconds();
        //TelemetryUtil.addData("timer", timeDiff);

        aimTimer.reset();
        pose = result.getBotpose();
        /*
        pidf.setPIDF(kp, ki, kd, kf);
        pidf.setTolerance(aimTolerance);
        power = pidf.calculate(tx - degreeOffset, 0);
        */
        if(!result.isValid()){
            TelemetryUtil.addData("ll result invalid");
        }
    }
}