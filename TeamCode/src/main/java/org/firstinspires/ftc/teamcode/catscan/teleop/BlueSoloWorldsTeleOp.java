package org.firstinspires.ftc.teamcode.catscan.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.AutoShootGPP;
import org.firstinspires.ftc.teamcode.catscan.commands.AutoShootPGP;
import org.firstinspires.ftc.teamcode.catscan.commands.AutoShootPPG;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.ReverseIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.SetIntakePower;
import org.firstinspires.ftc.teamcode.catscan.commands.SetTransferPower;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

@Configurable
@TeleOp(name = "4102 WORLDS blue solo")
public class BlueSoloWorldsTeleOp extends LinearOpMode {
    Pose startPose = new Pose(72, 72, 90);
    double rx;
    boolean shootOn;
    boolean transferOn, intOn, sortOn;
    private static double hood = 0;
    private static double testTransferPower = 0;
    private static boolean panelsHoodAdjustment = false;
    public static double targetTransferVelocity = 0;
    public double fwdIntPow = 1;

    private static int waitms =0;
    private static double degreeOffsetClose = 1;
    private static double degreeOffsetFar = 3;


    private final ElapsedTime loop = new ElapsedTime();
    double timeDiff;


    public Bot bot;
    @Override
    public void runOpMode() throws InterruptedException {

        TelemetryUtil.setup(telemetry);
        bot = new Bot(hardwareMap, startPose, true);
        GamepadEx gp1 = new GamepadEx(gamepad1);
        GamepadEx gp2 = new GamepadEx(gamepad2);
        bot.frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        bot.backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        bot.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bot.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //buttons and stuff
        gp1.getGamepadButton(GamepadKeys.Button.START).whenPressed(()->{
            shootOn = !shootOn;
            if(!shootOn) {
                new ActivateShooter(bot, 0).schedule();
            } else {
                new ActivateShooter(bot, bot.getRizz()).schedule();
            }
        });
        /*
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ShooterPower(bot, true));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ShooterPower(bot, false));
        */

        gp1.getGamepadButton(GamepadKeys.Button.X).whenPressed(()->{
            new ActivateShooter(bot, bot.getRizz()).schedule();
            new PositionHood(bot, bot.getHoodAngle(), (1.01- bot.getHoodAngle())).schedule();
        });

        gp1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(()->{
            intOn = !intOn;
            if(!intOn){
                new SetIntakePower(bot, 0).schedule();
            } else {
                new SetIntakePower(bot, fwdIntPow).schedule();
            }
        });
        gp2.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
            new ReverseIntake(bot).schedule();
        });


        gp1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(()->{
            if (bot.ll.getGoalDistanceM() > 3 ){
                new SequentialCommandGroup(
                        new SetTransferPower(bot, bot.getAdjustedFarTransferPower()),
                        new WaitCommand(100),
                        new PositionSDLeft(bot, true),
                        new PositionSDRight(bot, true),
                        new WaitCommand(1200),
                        new SetTransferPower(bot, .2),
                        new PositionSDLeft(bot, false),
                        new PositionSDRight(bot, false)
                ).schedule();
            } else {
                new SequentialCommandGroup(
                        new Shoot(bot)
                ).schedule();
            }
        });


        /*
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(()->{
            bot.hood.up();
            bot.hood.setPos();
        });

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(()->{
            bot.hood.down();
            bot.hood.setPos();
        });

        */

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(()->{
            new SequentialCommandGroup(
                    new AutoShootPPG(bot)
            ).schedule();
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(()->{
            new SequentialCommandGroup(
                    new AutoShootGPP(bot)
            ).schedule();
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(()->{
            new SequentialCommandGroup(
                    new AutoShootPGP(bot)
            ).schedule();
        });

        gp2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(()->{
            bot.sortOff();
            bot.camOff();
            sortOn = false;
            fwdIntPow = 1;
            new SetIntakePower(bot, fwdIntPow).schedule();
            new PositionDoors(bot, false , true).schedule();
        });

        gp2.getGamepadButton(GamepadKeys.Button.A).whenPressed(()->{
            bot.sortOn();
            bot.camOn();
            sortOn = true;
            fwdIntPow = .8;
            new SetIntakePower(bot, fwdIntPow).schedule();
        });

        waitForStart();
        new PositionDoors(bot, false , true).schedule();
        new PositionSDLeft(bot, false).schedule();
        new PositionSDRight(bot, false).schedule();
        new SetTransferPower(bot, .3).schedule();
        bot.limelight.pipelineSwitch(1);
        bot.follower.startTeleopDrive();
        bot.sortOff();
        while(!isStopRequested() && opModeIsActive()){
            double d = Math.max(Math.abs(-gamepad1.left_stick_y) + Math.abs(-gamepad1.left_stick_x * 1.1) + Math.abs(rx), 1);
            double y = (-gamepad1.left_stick_y);
            double x = (-gamepad1.left_stick_x * 1.1);
            if (gamepad1.x) {
                rx = -bot.ll.AimPID(); // tests it with ll , could be switched to the pedro coordinate based pid in bot class
                x = (-gamepad1.left_stick_x * .8);
                y = (-gamepad1.left_stick_y * .7);
                bot.frontLeft.setPower(y - x - rx);
                bot.backLeft.setPower(y + x - rx);
                bot.frontRight.setPower(y + x + rx);
                bot.backRight.setPower(y - x + rx);
                bot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                bot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                bot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                bot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            else {
                rx = -gamepad1.right_stick_x * .967;
                bot.frontLeft.setPower(Math.pow((y - x - rx),1) / d);
                bot.backLeft.setPower(Math.pow((y + x - rx),1) / d);
                bot.frontRight.setPower(Math.pow((y + x + rx),1) / d);
                bot.backRight.setPower(Math.pow((y - x + rx),1) / d);
                bot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }

            if (gamepad1.dpadDownWasPressed()) {
                bot.kickLeft.setPosition(.1);
                bot.kickRight.setPosition(.9);
            }
            if (gamepad1.dpadUpWasPressed()) {
                bot.kickLeft.setPosition(.75);
                bot.kickRight.setPosition(.25);
            }
/*
            if (bot.beamBreaks.getNumBalls() == 4 && !bot.ll.isResultValid()) {
                new ReverseIntake(bot).schedule();
            }
   */
            TelemetryUtil.addData("Hood Position: ", bot.hood.getPos());
            //TelemetryUtil.addData("ty: ", bot.ll.getTy());
            TelemetryUtil.addData("tx: ", bot.ll.getTx());
            TelemetryUtil.addData("goal dist: ", bot.ll.getGoalDistanceM());
            //TelemetryUtil.addData("ll aim power: ", -bot.ll.AimPID());
            TelemetryUtil.addData("num balls: ", bot.beamBreaks.getNumBalls());

            if (panelsHoodAdjustment) {
                new PositionHood(bot, hood, (1.01- hood)).schedule();
            }

            if (bot.ll.getGoalDistanceM() > 3 && bot.ll.getGoalDistanceM() < 5) {
                bot.ll.setDegreeOffset(degreeOffsetFar);
            }
            else {
                bot.ll.setDegreeOffset(degreeOffsetClose);
            }

            /*
            if (bot.ll.isResultValid()) {
                new ActivateShooter(bot, bot.getRizz()).schedule();
                new PositionHood(bot, bot.getHoodAngle(), (1.01- bot.getHoodAngle())).schedule();
            }
            */
            /*
            if (sortOn) {
                if (bot.hasPurpleBall()) {
                    new SequentialCommandGroup(
                            new PositionDoors(bot, false, true),
                            new WaitCommand(400)
                    ).schedule();
                }
                else if (bot.hasGreenBall()) {
                    new SequentialCommandGroup(
                            new PositionDoors(bot, true, false),
                            new WaitCommand(400)
                    ).schedule();
                }
            }
            */
            timeDiff = loop.seconds();
            loop.reset();

            TelemetryUtil.addData("loop: ", timeDiff);
            //TelemetryUtil.addData("Sort on? ", sortOn);
            //TelemetryUtil.addData("Intake Current: ", bot.theIntake.getCurrent());
            //TelemetryUtil.addData("Transfer Current: ", bot.theTransfer.getCurrent());


            //TelemetryUtil.addData("transfer on: ", transferOn);

            bot.loop();
        }
        bot.limelight.stop();
    }
}