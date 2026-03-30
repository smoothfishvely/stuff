package org.firstinspires.ftc.teamcode.catscan.testers;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.catscan.pipelines.PurpleOrGreen;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Configurable
@TeleOp(name = "cameratest")
public class CameraTest extends LinearOpMode {
    double purple = 0;
    double green = 0;
    public Servo sortLeft, sortRight;
    public DcMotor intake;
    public DigitalChannel intakeBB;
    final int cameraWidth = 1280;
    final int cameraHeight = 720;
    private boolean intakeDIDDY;
    public static double intakePow;
    PurpleOrGreen diddy = new PurpleOrGreen();

    @Override
    public void runOpMode() {

        sortLeft = hardwareMap.get(Servo.class, "sortLeft");
        sortRight = hardwareMap.get(Servo.class, "sortRight");
        intake = hardwareMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        intakeBB = hardwareMap.get(DigitalChannel.class, "intakeBB");
        intakeBB.setMode(DigitalChannel.Mode.INPUT);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(diddy);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT, OpenCvWebcam.StreamFormat.MJPEG);
            }


            @Override
            public void onError(int errorCode) {


            }
        });


        waitForStart();
        while(opModeIsActive()){

            if (diddy.hasPurpleBall()) {
                sortLeft.setPosition(.6);
                sortRight.setPosition(.74);
            }
            else if (diddy.hasGreenBall()) {
                sortLeft.setPosition(.78);
                sortRight.setPosition(.91);
            }

            intakeDIDDY = intakeBB.getState();


            if (gamepad1.b) {
                intake.setPower(.7);
            }
            else {
                intake.setPower(0);
            }

            if (gamepad1.a) {
                webcam.stopStreaming();
            }
            if (gamepad1.x) {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT, OpenCvWebcam.StreamFormat.MJPEG);
            }

            telemetry.addData("Green Data", diddy.debuggingGreen());
            telemetry.addData("Purple Data", diddy.debuggingPurple());
            telemetry.addData("has purp?", diddy.hasPurpleBall());
            telemetry.addData("has green?", diddy.hasGreenBall());
            telemetry.addData("BB", intakeDIDDY);


            telemetry.update();
        }




    }
}