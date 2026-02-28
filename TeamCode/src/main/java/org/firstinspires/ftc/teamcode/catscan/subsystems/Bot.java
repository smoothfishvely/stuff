package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Configurable
public class Bot {
    public DcMotorEx frontRight, frontLeft, backRight, backLeft, intake, transfer;
    TelemetryManager tm;

    public Servo sortLeft, sortRight, kickLeft, kickRight, hoodLeft, hoodRight, shootDoorLeft, shootDoorRight, rightLED, midLED, leftLED;
    public MotorEx shooterLeft, shooterRight;
    public TheHood hood;
    public DigitalChannel rightTopBB, rightMidBB, leftTopBB, bottomBB;
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
    public NormalizedColorSensor colorSensor;
    private double power;
    PIDFController aimPIDF;
    private double hoodCorrection;
    private double shooterError = 0;
    Pose goon;
    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    float gateWaitTime = 1; // The time, in seconds, that the gate waits before closing
    public boolean teleOp;
    public boolean isBlue; //added this to use to determine target goal position, see getTargetAngle()
    private int motif;
    public Bot(HardwareMap hMap, Pose startPose, boolean teleOp){
        tm = PanelsTelemetry.INSTANCE.getTelemetry();

//        colorSensor = hMap.get(NormalizedColorSensor.class, "colorSensor");
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

        rightTopBB = hMap.get(DigitalChannel.class, "rightTopBB");
        rightMidBB = hMap.get(DigitalChannel.class, "rightMidBB");
        leftTopBB = hMap.get(DigitalChannel.class, "leftTopBB");
        bottomBB = hMap.get(DigitalChannel.class, "bottomBB");
        rightTopBB.setMode(DigitalChannel.Mode.INPUT);
        rightMidBB.setMode(DigitalChannel.Mode.INPUT);
        leftTopBB.setMode(DigitalChannel.Mode.INPUT);
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
        doors = new TheDoors(sortLeft, sortRight);
        hood = new TheHood(hoodLeft, hoodRight);
        theIntake = new TheIntake(intake);
        shooter = new TheShooter(shooterLeft, shooterRight);
        ll = new TheLimelight(limelight);
        beamBreaks = new BeamBreaks(rightTopBB, rightMidBB, leftTopBB, bottomBB);
        lights = new TheLights(rightLED, midLED, leftLED);
        CommandScheduler.getInstance().registerSubsystem(hood, theIntake, shooter, theTransfer, shooterDoors);
        limelight.start();
//        if (colorSensor instanceof SwitchableLight) {
//            ((SwitchableLight)colorSensor).enableLight(true);
//        }
    }

    public void setMotif(int m){
        motif = m;
    }

    public int getMotif(){
        return motif;
    }
    public double getRizz(){
        if (ll.getGoalDistanceM() > 5) {
            return 1250;
        }
        else if (ll.getGoalDistanceM() > 3){
            return 1520;
        }
        else {
            return (163 * ll.getGoalDistanceM()) + 890;
        }
    }
    public double getHoodAngle() {
        if (ll.getGoalDistanceM() > 3 && ll.getGoalDistanceM() > 5) {
            return .37;
        }
        else {
            return (.0669 * ll.getGoalDistanceM()) + 0.195;
        }
    }
    public double getTargetAngle(){//made this get target angle so it can be used for the .turnTo version in teleOp
        if (isBlue) {
            double x = goon.getX();
            double y = 144 - goon.getY(); //blue goal position is at x = 0, y = 144 in pedro coordinates
            return Math.atan(x / y);
        }
        else {
            double x = 144 - goon.getX(); //red goal position is at x = 144, y = 144 in pedro coordinates
            double y = 144 - goon.getY();
            return Math.atan(x / y);
        }
    }

    public void setPose(Pose pose){ //maybe use for manual relocalize but idk how necessary (just wanted to lol)
        follower.setPose(pose);
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

    public void loop(){
        goon = follower.getPose();
        shooterError = Math.abs(getRizz() - shooter.getVelocity());
        CommandScheduler.getInstance().run();
        TelemetryUtil.addData("Current Position", follower.getPose());
        TelemetryUtil.addData("motif", motif);
        TelemetryUtil.update();
        follower.update();

        if (!isErrorSig()) {
            if (beamBreaks.getNumBalls() == 3) {
                lights.setIndividualPower(.5, .5, .5);
            } else if (beamBreaks.getNumBalls() == 2) {
                lights.setIndividualPower(.5, .5, 0);
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
            } else if (beamBreaks.getNumBalls() == 1) {
                lights.setIndividualPower(.388, 0, 0);
            } else {
                lights.setIndividualPower(0, 0, 0);
            }
        }
        tm.debug("right top:", beamBreaks.rightTop);
        tm.debug("right mid:", beamBreaks.rightMid);
        tm.debug("left top:", beamBreaks.leftTop);
        tm.debug("bottom:", beamBreaks.bottom);


        tm.debug("velocity: ", shooterLeft.getVelocity());
        tm.debug("target velocity: ", shooter.getTargetVelocity());
        tm.update();
    }
}