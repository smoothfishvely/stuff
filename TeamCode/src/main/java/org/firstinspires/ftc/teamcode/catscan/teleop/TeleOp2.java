package org.firstinspires.ftc.teamcode.catscan.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.graph.GraphManager;
import com.bylazar.graph.PanelsGraph;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;


//2shooter door, 2clutch, 2intake retract

@Configurable
@Config
@TeleOp(name="awesome test")
public class TeleOp2 extends LinearOpMode {
    MotorEx shooterLeft, shooterRight;
    DcMotor frontRight, frontLeft, backRight, backLeft, intake, transfer;
    Servo sortLeft, sortRight, hoodLeft, hoodRight, shootDoorLeft,
            shootDoorRight, clutchLeft, clutchRight, retractLeft, retractRight;
    Limelight3A ll;
    TelemetryManager tm;
    GraphManager gm;
    double hoodPosL = .2, hoodPosR = .81;
    public static double vel = 0;
    public static double kp = .75;
    public static double ki = 0;
    public static double kd = .4;
    public static double ks = 0.92;
    public static double kv = 0.47;
    public static double ka = 0.3;
    boolean shootOn, transOn, shootL, shootR, intOn;
    @Override
    public void runOpMode() throws InterruptedException {
        tm = PanelsTelemetry.INSTANCE.getTelemetry();
        gm = PanelsGraph.INSTANCE.getManager();
        shootDoorLeft = hardwareMap.get(Servo.class, "shootDoorLeft");
        shootDoorRight = hardwareMap.get(Servo.class, "shootDoorRight");
        //clutchLeft = hardwareMap.get(Servo.class, "clutchLeft");
        //clutchRight = hardwareMap.get(Servo.class, "clutchRight");
        //retractLeft = hardwareMap.get(Servo.class, "retractLeft");
        //retractRight = hardwareMap.get(Servo.class, "retractRight");
        transfer = hardwareMap.dcMotor.get("transfer");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterLeft = new MotorEx(hardwareMap, "shooterLeft");
        shooterRight = new MotorEx(hardwareMap, "shooterRight");
        hoodLeft = hardwareMap.get(Servo.class, "hoodLeft");
        hoodRight = hardwareMap.get(Servo.class, "hoodRight");
        //sortLeft = hardwareMap.get(Servo.class, "sortLeft");
        //sortRight = hardwareMap.get(Servo.class, "sortRight");
        shooterLeft.setInverted(true);
        intake = hardwareMap.dcMotor.get("intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        shooterLeft.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setVeloCoefficients(kp, ki, kd);
        shooterLeft.setVeloCoefficients(kp, ki, kd);
        shooterRight.setFeedforwardCoefficients(ks, kv, ka);
        shooterLeft.setFeedforwardCoefficients(ks, kv, ka);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        Gamepad karel = new Gamepad();
        Gamepad karelNow = new Gamepad();
        //ll = hardwareMap.get(Limelight3A.class, "limelight");
        //ll.pipelineSwitch(0);

        waitForStart();
        //ll.start();
        while(opModeIsActive()){
            shooterRight.setVeloCoefficients(kp, ki, kd);
            shooterLeft.setVeloCoefficients(kp, ki, kd);
            shooterRight.setFeedforwardCoefficients(ks, kv, ka);
            shooterLeft.setFeedforwardCoefficients(ks, kv, ka);
            karel.copy(karelNow);
            karelNow.copy(gamepad1);
            double y = gamepad2.left_stick_y;
            double x = gamepad2.left_stick_x * 1.1;
            double rx = gamepad2.right_stick_x * .85;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double fl = Math.pow((y - x - rx),5) / d;
            double bl = Math.pow((y + x - rx),5) / d;
            double fr = Math.pow((y + x + rx),5) / d;
            double br = Math.pow((y - x + rx),5) / d;
            frontLeft.setPower(fl);
            backLeft.setPower(bl);
            frontRight.setPower(fr);
            backRight.setPower(br);
            if(karelNow.a && !karel.a){
                shootOn = !shootOn;
                if(!shootOn) {
                    vel = 0;
                    transfer.setPower(0);
                    shootDoorLeft.setPosition(.3);
                    shootL = false;
                    transOn = false;
                }
            } else if(karelNow.dpad_up && !karel.dpad_up){
                vel += .02;
                shootOn = true;
            } else if(karelNow.dpad_down && !karel.dpad_down){
                vel -= .02;
                shootOn = true;
            }
            if(karelNow.left_trigger > 0){
                vel = .98;
                hoodPosL = .41;
                hoodPosR = .6;
                shootOn = true;
                hoodLeft.setPosition(hoodPosL);
                hoodRight.setPosition(hoodPosR);
            } else if(karelNow.right_trigger > 0){
                vel = 1.3;
                hoodPosL = .45;
                hoodPosR = .56;
                shootOn = true;
                hoodLeft.setPosition(hoodPosL);
                hoodRight.setPosition(hoodPosR);
            }
            shooterRight.setVelocity(vel);
            shooterLeft.setVelocity(vel);
            if(karelNow.b && !karel.b){
                transOn = !transOn;
                if(transOn){
                    transfer.setPower(.567);
                } else {
                    transfer.setPower(0);
                }
            }
            if (karelNow.left_bumper && !karel.left_bumper) {
                shootL = !shootL;
                if (shootL) {
                    shootDoorLeft.setPosition(.3);
                } else {
                    shootDoorLeft.setPosition(.05);
                }
            }
            if (karelNow.right_bumper && !karel.right_bumper) {
                shootR = !shootR;
                if (shootR) {
                    shootDoorRight.setPosition(.3);
                } else {
                    shootDoorRight.setPosition(.05);
                }
            }
            if (karelNow.dpad_left && !karel.dpad_left) { //"up"
                hoodPosL += .05;
                hoodPosR -= .05;
                hoodLeft.setPosition(hoodPosL);
                hoodRight.setPosition(hoodPosR);
            } else if (karelNow.dpad_right && !karel.dpad_right) {
                hoodPosL -= .05;
                hoodPosR += .05;
                hoodLeft.setPosition(hoodPosL);
                hoodRight.setPosition(hoodPosR);
            } else if(karelNow.x && !karel.x){
                hoodPosL = .2;
                hoodPosR = .81;
                hoodLeft.setPosition(hoodPosL);
                hoodRight.setPosition(hoodPosR);
            }
            if(karelNow.y && !karel.y){
                intOn = !intOn;
                if(intOn){
                    intake.setPower(1);
                } else {
                    intake.setPower(0);
                }
            }
            telemetry.addData("hood left", hoodLeft.getPosition());
            telemetry.addData("hood right", hoodRight.getPosition());
            telemetry.addData("velocity", shooterLeft.getVelocity());
            telemetry.addData("target v", vel);
            tm.debug("hood left", hoodLeft.getPosition());
            tm.debug("velocity", shooterLeft.getVelocity());
            tm.debug("target v", vel);
            gm.addData("velocity", shooterLeft.getVelocity());
            gm.addData("target v", vel);
            gm.update();
            tm.update(telemetry);
            telemetry.update();
        }
    }
}