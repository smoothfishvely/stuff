package org.firstinspires.ftc.teamcode.catscan.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Configurable
public class Bot {
    public DcMotorEx frontRight, frontLeft, backRight, backLeft, intake, transfer;
    public Servo sortLeft, sortRight, kickLeft, kickRight, hoodLeft, hoodRight, shootDoorLeft, shootDoorRight ;
    public MotorEx shooterLeft, shooterRight;
    public TheHood hood;
    public TheIntake theIntake;
    public TheShooter shooter;
    public ShooterDoors shooterDoors;
    public TheTransfer theTransfer;
    public TheLimelight ll;
    public Limelight3A limelight;
    public Follower follower;
    public TheDoors doors;
    public NormalizedColorSensor colorSensor;
    ElapsedTime gateTimer = new ElapsedTime(); // The timer that tracks how long it has been since a gate was opened
    float gateWaitTime = 1; // The time, in seconds, that the gate waits before closing
    public boolean teleOp;
    private int motif;
    public static double kp = 1.3;
    public static double ki = 0;
    public static double kd = 1.25;
    public static double ks = 232;
    public static double kv = 1.2;
    public static double ka = 0;
    public Bot(HardwareMap hMap, Pose startPose, boolean teleOp){
//        colorSensor = hMap.get(NormalizedColorSensor.class, "colorSensor");
        shootDoorLeft = hMap.get(Servo.class, "shootDoorLeft");
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
        //sortLeft = hardwareMap.get(Servo.class, "sortLeft");
        //sortRight = hardwareMap.get(Servo.class, "sortRight");
        shooterLeft.setInverted(true);
        intake = hMap.get(DcMotorEx.class,"intake");
        intake.setDirection(DcMotor.Direction.REVERSE);
        shooterLeft.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setRunMode(Motor.RunMode.VelocityControl);
        shooterRight.setVeloCoefficients(kp, ki, kd);
        shooterLeft.setVeloCoefficients(kp, ki, kd);
        shooterRight.setFeedforwardCoefficients(ks, kv, ka);
        shooterLeft.setFeedforwardCoefficients(ks, kv, ka);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        limelight = hMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        follower = Constants.createFollower(hMap);
        follower.setStartingPose(startPose);

        theTransfer = new TheTransfer(transfer);
        shooterDoors = new ShooterDoors(shootDoorLeft, shootDoorRight);
//        doors = new TheDoors(sortLeft, sortRight, colorSensor);
        hood = new TheHood(hoodLeft, hoodRight);
        theIntake = new TheIntake(intake);
        shooter = new TheShooter(shooterLeft, shooterRight);
        ll = new TheLimelight(limelight);
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

    public void loop(){
        CommandScheduler.getInstance().run();
        TelemetryUtil.addData("Current Position", follower.getPose());
        TelemetryUtil.addData("motif", motif);
        TelemetryUtil.update();
        follower.update();
    }
}