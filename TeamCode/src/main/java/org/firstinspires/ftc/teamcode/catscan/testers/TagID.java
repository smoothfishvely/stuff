package org.firstinspires.ftc.teamcode.catscan.testers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import android.graphics.Color;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

//@TeleOp(name = "ll test")
public class TagID extends LinearOpMode {
    Limelight3A ll;
    Servo sortLeft, sortRight;

    DcMotor intake;

    int motifId;
    NormalizedColorSensor colorSensor; // NormalizedColorSensor js makes the color sensor's values more consistent

    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    float gateWaitTime = 1; // The time, in seconds, that the gate waits before closing
    @Override
    public void runOpMode() throws InterruptedException {
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        sortLeft = hardwareMap.get(Servo.class, "sortLeft");
        sortRight = hardwareMap.get(Servo.class, "sortRight");
        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        ll.start();
        ll.pipelineSwitch(2);

        float gain = 5;
        final float[] hsvValues = new float[3];
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "intake_color");
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight)colorSensor).enableLight(true);
        }

        while (opModeIsActive()) {
            LLResult result = ll.getLatestResult();
            if(result.isValid()) {
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    motifId = fr.getFiducialId();
                }
            }else{
                telemetry.addLine("ts not valid!");
            }
            NormalizedRGBA rgbColors = colorSensor.getNormalizedColors();
            Color.colorToHSV(rgbColors.toColor(), hsvValues);

            telemetry.addLine() // Adds in the values detected by the color sensor (For the most part, only Hue is important for tuning)
                    .addData("Hue", "%.3f", hsvValues[0])
                    .addData("Saturation", "%.3f", hsvValues[1])
                    .addData("Value", "%.3f", hsvValues[2]);

            if (hsvValues[0]>200 && hsvValues[0] < 250){ // Is the detected color within the range of purple?
                telemetry.addLine("ts purple");
                sortRight.setPosition(0.45);
                sortLeft.setPosition(0.45);
                gateTimer.reset(); // Resets gateTimer. Once gateTimer is greater than gateWaitTime, the gates will close.
            }
            else if (hsvValues[0]>140 && hsvValues[0]<190){ // Is the detected color within the range of green?
                telemetry.addLine("ts green");
                sortRight.setPosition(0.8);
                sortLeft.setPosition(0.8);
                gateTimer.reset(); // Resets gateTimer so that the gates can close after the time defined by gateWaitTime has passed.
            }

            if (hsvValues[0]<140 || hsvValues[0]>250){ // Is the detected color outside of the ranges of both green and purple
                // If the time, in seconds, since gateTimer.reset() was called is greater than the close time of the gate, then the gate closes
                if (gateTimer.seconds() > gateWaitTime){
                    telemetry.addLine("ts neutral");
//                    colorSorter.setPosition(0);
                }
            }
            if (gamepad1.b) {
                intake.setPower(.7);
            } else {
                intake.setPower(0);
            }
            telemetry.addData("April Tag ID: ", motifId);
            telemetry.update();
        }
    }

}
