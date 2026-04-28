package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.pipelines.PurpleOrGreen;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Configurable
public class Bot {
    public DcMotorEx frontRight, frontLeft, backRight, backLeft, intake, transfer;
    TelemetryManager tm;
    public Servo sortLeft, sortRight, kickLeft, kickRight, hoodLeft, hoodRight, shootDoorLeft, shootDoorRight, rightLED, midLED, leftLED;
    public MotorEx shooterLeft, shooterRight;
    public TheHood hood;
    public DigitalChannel rightTopBB, rightMidBB, bottomBB, intakeBB;
    private static double aimKp = .1; //tune pids
    private static double aimKI = 0;
    private static double aimKd = 0;
    private static double aimKf = 0.001;
    public TheIntake theIntake;
    public TheShooter shooter;
    public ShooterDoors shooterDoors;
    public TheTransfer theTransfer;
    public TheLimelight ll;
    public Limelight3A limelight;
    public Follower follower;
    public TheDoors doors;
    public BeamBreaks beamBreaks;
    public TheLights lights;
    public VoltageSensor battery;
    public NormalizedColorSensor colorSensorR;
    private double power;
    PIDFController aimPIDF;
    private double hoodCorrection;
    private double shooterError = 0;
    private double batteryVoltage = 0;
    private double nominalVoltage = 13.8;
    private static double sigmaTransferPower = .55;
    private static double sigmaSortTransferPower = .75;

    private static double sigmaFarTransferPower = .4;
    private double adjustedTransferPower = 0;
    private double adjustedFarTransferPower = 0;
    private boolean transferOn, shooting, sortOn = false;
    final int cameraWidth = 1280;
    final int cameraHeight = 720;
    Vector velocity = new Vector();
    Pose goon;
    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    float gateWaitTime = 1; // The time, in seconds, that the gate waits before closing
    public boolean teleOp;
    PurpleOrGreen diddy = new PurpleOrGreen();
    OpenCvWebcam webcam;
    private int motif;
    public Bot(HardwareMap hMap, Pose startPose, boolean teleOp){
        tm = PanelsTelemetry.INSTANCE.getTelemetry();

        colorSensorR = hMap.get(NormalizedColorSensor.class, "colorSensorR");
        shootDoorLeft = hMap.get(Servo.class, "shootDoorLeft");
        aimPIDF = new PIDFController(aimKp,aimKI,aimKd,aimKf);
        shootDoorRight = hMap.get(Servo.class, "shootDoorRight");
        //clutchLeft = hardwareMap.get(Servo.class, "clutchLeft");
        //clutchRight = hardwareMap.get(Servo.class, "clutchRight");
        //retractLeft = hardwareMap.get(Servo.class, "retractLeft");
        //retractRight = hardwareMap.get(Servo.class, "retractRight");
        transfer = hMap.get(DcMotorEx.class,"transfer");
        frontLeft = hMap.get(DcMotorEx.class,"frontLeft");
        backLeft = hMap.get(DcMotorEx.class,"backLeft");
        frontRight = hMap.get(DcMotorEx.class,"frontRight");
        backRight = hMap.get(DcMotorEx.class, "backRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterLeft = new MotorEx(hMap, "shooterLeft");
        shooterRight = new MotorEx(hMap, "shooterRight");
        hoodLeft = hMap.get(Servo.class, "hoodLeft");
        hoodRight = hMap.get(Servo.class, "hoodRight");
        sortLeft = hMap.get(Servo.class, "sortLeft");
        sortRight = hMap.get(Servo.class, "sortRight");
        rightLED = hMap.get(Servo.class, "rightLED");
        midLED = hMap.get(Servo.class, "midLED");
        leftLED = hMap.get(Servo.class, "leftLED");
        kickLeft = hMap.get(Servo.class, "kickLeft");
        kickRight = hMap.get(Servo.class, "kickRight");

        rightTopBB = hMap.get(DigitalChannel.class, "rightTopBB");
        rightMidBB = hMap.get(DigitalChannel.class, "rightMidBB");
        intakeBB = hMap.get(DigitalChannel.class, "intakeBB");
        bottomBB = hMap.get(DigitalChannel.class, "bottomBB");
        rightTopBB.setMode(DigitalChannel.Mode.INPUT);
        rightMidBB.setMode(DigitalChannel.Mode.INPUT);
        intakeBB.setMode(DigitalChannel.Mode.INPUT);
        bottomBB.setMode(DigitalChannel.Mode.INPUT);
        //beam break stuff
        shooterLeft.setInverted(true);
        intake = hMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(90);
        follower = Constants.createFollower(hMap);
        follower.setStartingPose(startPose);
        goon = follower.getPose();
        theTransfer = new TheTransfer(transfer);
        shooterDoors = new ShooterDoors(shootDoorLeft, shootDoorRight);
        doors = new TheDoors(sortLeft, sortRight, colorSensorR);
        hood = new TheHood(hoodLeft, hoodRight);
        theIntake = new TheIntake(intake);
        shooter = new TheShooter(shooterLeft, shooterRight);
        ll = new TheLimelight(limelight);
        beamBreaks = new BeamBreaks(rightTopBB, rightMidBB, intakeBB, bottomBB);
        lights = new TheLights(rightLED, midLED, leftLED);
        CommandScheduler.getInstance().registerSubsystem(hood, theIntake, shooter, theTransfer, shooterDoors);

        battery = hMap.voltageSensor.iterator().next();
        batteryVoltage = battery.getVoltage();

        limelight.start();
        if (colorSensorR instanceof SwitchableLight) {
            ((SwitchableLight)colorSensorR).enableLight(true);
        }


        int cameraMonitorViewId = hMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
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
    }

    public void setMotif(int m){
        motif = m;
    }

    public int getMotif(){
        return motif;
    }
    public double getRizz(){
        if (ll.getGoalDistanceM() > 5) {
            return 1120;
        }
        else if (ll.getGoalDistanceM() > 3){
            return 1500;
        }
        else {
            return (193 * ll.getGoalDistanceM()) + 802;
        }
    }
    public double getHoodAngle() {
        if (ll.getGoalDistanceM() > 3 ) {
            return .28;
        }
        else {
            return (0.08 * ll.getGoalDistanceM()) + 0.15;
        }
    }

    public void setPose(Pose pose){ //maybe use for manual relocalize but idk how necessary (just wanted to lol)
        follower.setPose(pose);
    }

    public void sortOff() {
        sortOn = false;

    }
    public void sortOn() {
        sortOn = true;
    }

    public void camOn(){
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(cameraWidth, cameraHeight, OpenCvCameraRotation.UPRIGHT, OpenCvWebcam.StreamFormat.MJPEG);
            }


            @Override
            public void onError(int errorCode) {


            }
        });
    }
    public void camOff(){
        webcam.closeCameraDevice();
    }

    public boolean hasPurpleBall() {
        return diddy.hasPurpleBall();
    }
    public boolean hasGreenBall() {
        return diddy.hasGreenBall();
    }

    public void setTransferGoon(boolean diddy) {
        transferOn = diddy;
    }
    public boolean getTransferGoon() {
        return transferOn;
    }

    public void llRelocalize(){ //gets botPose from ll, converts to pedro coordinate system, and sets pose to the result
        Pose3D pose = ll.getBotPose();
        Position pos = pose.getPosition();
        YawPitchRollAngles angles = pose.getOrientation();
        Pose adjustedPose = new Pose(pos.x, pos.y, angles.getYaw(), FTCCoordinates.INSTANCE)
                .getAsCoordinateSystem(PedroCoordinates.INSTANCE); //converts to pedro coordinate system
        follower.setPose(adjustedPose);
    }

    public boolean isErrorSig() {
        if (shooterError > 35) {
            return true;
        }
        else {
            return false;
        }
    }

    public double getAdjustedTransferPower() {
        return adjustedTransferPower;
    }

    public double getAdjustedFarTransferPower() {
        return adjustedFarTransferPower;
    }

    public void setShootOn(boolean on){
        shooting = on;
    }

    public boolean getShootOn(){
        return shooting;
    }

    public void loop(){
        goon = follower.getPose();
        batteryVoltage = battery.getVoltage();
        adjustedTransferPower = sigmaTransferPower * (nominalVoltage / Math.max(batteryVoltage, 1.0));
        adjustedFarTransferPower = sigmaFarTransferPower * (nominalVoltage / Math.max(batteryVoltage, 1.0));
        shooterError = Math.abs(getRizz() - shooter.getVelocity());
        CommandScheduler.getInstance().run();
//      TelemetryUtil.addData("transfer Power: ", getAdjustedTransferPower());
        TelemetryUtil.addData("Velocity: ", shooterRight.getVelocity());
        TelemetryUtil.addData("target velocity: ", shooter.getTargetVelocity());
        //TelemetryUtil.addData("sort on: ", sortOn);

        if (sortOn) {
            TelemetryUtil.addData("Green Data", diddy.debuggingGreen());
            TelemetryUtil.addData("Purple Data", diddy.debuggingPurple());
        }

        //TelemetryUtil.addData("motif", motif);
        //TelemetryUtil.addData("sort on? ", sortOn);

        TelemetryUtil.update();
        follower.update();

        velocity = follower.getVelocity();
        velocity.rotateVector(-follower.getHeading());

        ll.updateVelocities(((velocity.getXComponent() * 2.54) / 100.0), ((velocity.getYComponent() * 2.54) / 100.0));

        if (!isErrorSig()) {
            if (beamBreaks.getNumBalls() == 3) {
                lights.setIndividualPower(.5, .5, .5);
            } else if (beamBreaks.getNumBalls() == 2) {
                lights.setIndividualPower(.5, .5, 0);
            } else if (beamBreaks.getNumBalls() == 4) {
                lights.setIndividualPower(.1, .1, .1);
            } else if (beamBreaks.getNumBalls() == 1) {
                lights.setIndividualPower(.5, 0, 0);
            } else {
                lights.setIndividualPower(0, 0, 0);
            }
        }
        else {
            if (beamBreaks.getNumBalls() == 3) {
                lights.setIndividualPower(.388, .388, .388);
            } else if (beamBreaks.getNumBalls() == 2) {
                lights.setIndividualPower(.388, .388, 0);
            }
            else if (beamBreaks.getNumBalls() == 4) {
                    lights.setIndividualPower(.1, .1, .1);
            } else if (beamBreaks.getNumBalls() == 1) {
                lights.setIndividualPower(.388, 0, 0);
            } else {
                lights.setIndividualPower(0, 0, 0);
            }
        }

        if (sortOn) {
            if (hasPurpleBall()) {
                new SequentialCommandGroup(
                        new PositionDoors(this, false, true),
                        new WaitCommand(700)
                ).schedule();
            }
            else if (hasGreenBall()) {
                new SequentialCommandGroup(
                        new PositionDoors(this, true, false),
                        new WaitCommand(700)
                ).schedule();
            }
        }

        /*
        tm.debug("right top:", beamBreaks.rightTop);
        tm.debug("right mid:", beamBreaks.rightMid);
        tm.debug("left top:", beamBreaks.intake);
        tm.debug("bottom:", beamBreaks.bottom);


        tm.debug("velocity: ", shooterLeft.getVelocity());
        tm.debug("target velocity: ", shooter.getTargetVelocity());
        tm.update();
         */
    }
}