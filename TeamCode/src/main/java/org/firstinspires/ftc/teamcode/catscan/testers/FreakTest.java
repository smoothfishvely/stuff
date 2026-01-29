package org.firstinspires.ftc.teamcode.catscan.testers;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

//@TeleOp(name = "skibidi")
public class FreakTest extends LinearOpMode {
    DcMotor frontRight, frontLeft, backRight, backLeft, intake;
    Servo sortLeft, sortRight, kickLeft, kickRight, hoodLeft, hoodRight;
    MotorEx shooterLeft, shooterRight;
    Limelight3A ll;
    private ElapsedTime adjTimer;
    private boolean aToggle, prevA, rkUp, lkUp;
    private double shooterPower, shooterAdj, adjWaitTime, hoodPos, targetHeight = .4572;
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterLeft = new MotorEx(hardwareMap, "shooterLeft");
        shooterRight = new MotorEx(hardwareMap, "shooterRight");
        kickLeft = hardwareMap.get(Servo.class, "kickerLeft");
        kickRight = hardwareMap.get(Servo.class, "kickerRight");
        hoodLeft = hardwareMap.get(Servo.class, "hoodLeft");
        hoodRight = hardwareMap.get(Servo.class, "hoodRight");
        shooterRight.setInverted(true);
        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        shooterLeft.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setVeloCoefficients(0.5, 0.0012, 0.32);
        shooterLeft.setVeloCoefficients(0.5, 0.0012, 0.32);
        shooterLeft.setFeedforwardCoefficients(0.92, 0.47, 0.3);
        shooterRight.setFeedforwardCoefficients(0.92, 0.47, 0.3);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        Gamepad karel = new Gamepad();
        Gamepad karelNow = new Gamepad();
        ll = hardwareMap.get(Limelight3A.class, "limelight");
        ll.pipelineSwitch(0);
        String pipeline = "red";

        waitForStart();
        ll.start();
        aToggle = prevA = false; // default all toggles false

        kickLeft.setPosition(.5);
        kickRight.setPosition(.5);

        shooterPower = 1.5;
        shooterAdj = .05; // the increment that shooter power is adjusted by
        adjWaitTime = .25;
        hoodPos = .2;
        adjTimer = new ElapsedTime();
        while (opModeIsActive()) {
            karel.copy(karelNow);
            karelNow.copy(gamepad1);
            double y = gamepad2.left_stick_y;
            double x = gamepad2.left_stick_x * 1.1;
            double rx = gamepad2.right_stick_x * .85;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double fl = Math.pow((y - x - rx) / d, 5);
            double bl = Math.pow((y + x - rx) / d, 5);
            double fr = Math.pow((y + x + rx) / d, 5);
            double br = Math.pow((y - x + rx) / d, 5);

            frontLeft.setPower(fl);
            backLeft.setPower(bl);
            frontRight.setPower(fr);
            backRight.setPower(br);

            if (gamepad2.b){
                ll.pipelineSwitch(1);
                pipeline = "Blue";
            }else if (gamepad2.a){
                ll.pipelineSwitch(0);
                pipeline = "Red";
            }

            if (gamepad1.a && !prevA) { // Toggle for button A
                aToggle = !aToggle;
            }
            prevA = gamepad1.a; // Updates A state for toggle

            if (aToggle) { // Turns shooter motors on and off when a is toggled
                shooterLeft.set(shooterPower);
                shooterRight.set(shooterPower);
            } else {
                shooterLeft.set(0);
                shooterRight.set(0);
                kickLeft.setPosition(.5);
                kickRight.setPosition(.28);
            }

            if (gamepad1.dpad_up && adjTimer.seconds() > adjWaitTime) { // Increases shooterPower when up is pressed
                shooterPower += shooterAdj;
                adjTimer.reset();
            }
            if (gamepad1.dpad_down && adjTimer.seconds() > adjWaitTime) { // Decreases shooterPower when down is pressed
                shooterPower -= shooterAdj;
                adjTimer.reset();
            }

            /*
            if (gamepad1.left_bumper){
                kickLeft.setPosition(.5+(40.0/355));//up
                kickRight.setPosition(.54);
            } else if (gamepad1.right_bumper){
                kickLeft.setPosition(.5);//down
                kickRight.setPosition(.5-(40.0/355));
            }
            */

            if (karelNow.left_bumper && !karel.left_bumper) {
                rkUp = !rkUp;

                if(rkUp){
                    kickRight.setPosition(.54);
                } else if(!rkUp){
                    kickRight.setPosition(.5-(40.0/355));
                }
            }

            if (karelNow.right_bumper && !karel.right_bumper) {
                lkUp = !lkUp;

                if (lkUp) {
                    kickLeft.setPosition(.5 + (40.0 / 355));//up
                } else if (!lkUp) {
                    kickLeft.setPosition(.5); //down
                }
            }

            if (gamepad1.b) {
                intake.setPower(1);
            } else {
                intake.setPower(0);
            }

            if (gamepad1.dpad_left) {
                hoodPos += .02;
                hoodLeft.setPosition(hoodPos);
                hoodRight.setPosition(hoodPos);
            } else if (gamepad1.dpad_right && hoodPos != .2) {
                hoodPos -= .02;
                hoodLeft.setPosition(hoodPos);
                hoodRight.setPosition(hoodPos);
            }

            if (gamepad1.y) {
                hoodPos = .2;
                hoodLeft.setPosition(hoodPos);
                hoodRight.setPosition(hoodPos);
            }

            LLResult result = ll.getLatestResult();
            double tyRad = Math.toRadians(result.getTy());
            double llDistance = (targetHeight/Math.tan(tyRad));

            telemetry.addLine("Pipeline: "+pipeline);
            telemetry.addData("target offset (degrees):", result.getTx());
            telemetry.addData("absolute target distance: (meters)", llDistance);
            telemetry.addData("Velocity: ", shooterLeft.getVelocity());
            telemetry.addData("Corrected Velocity: ", shooterLeft.getCorrectedVelocity());
            telemetry.addData("Motor Power: ", shooterPower);
            telemetry.addData("Hood Position: ", hoodPos);
            telemetry.addData("Left Kicker Up: ", lkUp);
            telemetry.addData("Right Kicker Up: ", rkUp);
            telemetry.update();
        }
    }
}

