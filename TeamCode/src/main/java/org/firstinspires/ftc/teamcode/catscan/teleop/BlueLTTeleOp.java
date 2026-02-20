package org.firstinspires.ftc.teamcode.catscan.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateTransfer;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.ShooterPower;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

@Configurable
@TeleOp(name = "4102 lt drive blue")
public class BlueLTTeleOp extends LinearOpMode {
    Pose startPose = new Pose(72, 72, 90);
    double rx;
    boolean shootOn;
    boolean transferOn;
    DigitalChannel rightTopBB;
    Bot bot;
    @Override
    public void runOpMode() throws InterruptedException {

        TelemetryUtil.setup(telemetry);
        bot = new Bot(hardwareMap, startPose, true);
        //rightTopBB = hardwareMap.get(DigitalChannel.class, "rightTopBB");
        //rightTopBB.setMode(DigitalChannel.Mode.INPUT);
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
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ShooterPower(bot, true));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ShooterPower(bot, false));

        gp1.getGamepadButton(GamepadKeys.Button.A).whenPressed(()->{
            new ActivateShooter(bot, bot.getRizz()).schedule();
            new PositionHood(bot, bot.getHoodAngle(), (1.01- bot.getHoodAngle())).schedule();
        });

        gp1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ActivateIntake(bot));

        gp1.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
            transferOn = !transferOn;
            if(!transferOn){
                new SequentialCommandGroup(
                        new PositionSDLeft(bot, false),
                        new PositionSDRight(bot, false),
                        new WaitCommand(200),
                        new ActivateTransfer(bot, false)
                ).schedule();
            } else {
                new SequentialCommandGroup(
                        new PositionSDLeft(bot, true),
                        new PositionSDRight(bot, true),
                        new WaitCommand(200),
                        new ActivateTransfer(bot, true)
                ).schedule();
            }
        });

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(()->{
            bot.hood.up();
            bot.hood.setPos();
        });

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(()->{
            bot.hood.down();
            bot.hood.setPos();
        });

        /*
        gp2.getGamepadButton(GamepadKeys.Button.A).whileHeld(()->{
            bot.follower.turnTo(bot.getTargetAngle()); //changed to target angle, as it was set to rx before
        });
        gp2.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
        });
        gp2.getGamepadButton(GamepadKeys.Button.X).whileHeld(()->{
            new ActivateShooter(bot, bot.getRizz()).schedule();
            new PositionHood(bot, bot.getHoodAngle(), (1.01- bot.getHoodAngle())).schedule();
        });*/

        waitForStart();
        bot.follower.startTeleopDrive();
        while(!isStopRequested() && opModeIsActive()){
            double d = Math.max(Math.abs(-gamepad2.left_stick_y) + Math.abs(-gamepad2.left_stick_x * 1.1) + Math.abs(rx), 1);
            double y = (-gamepad2.left_stick_y);
            double x = (-gamepad2.left_stick_x * 1.1);
            if (gamepad2.x) {
                rx = -bot.ll.AimPID(); // tests it with ll , could be switched to the pedro coordinate based pid in bot class
                x = (-gamepad2.left_stick_x * .8);
                y = (-gamepad2.left_stick_y * .7);
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
                rx = -gamepad2.right_stick_x * .967;
                bot.frontLeft.setPower(Math.pow((y - x - rx),5) / d);
                bot.backLeft.setPower(Math.pow((y + x - rx),5) / d);
                bot.frontRight.setPower(Math.pow((y + x + rx),5) / d);
                bot.backRight.setPower(Math.pow((y - x + rx),5) / d);
                bot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                bot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
            /*
            if (bot.beamBreaks.isAllFalse()) {
                new SequentialCommandGroup(
                        new PositionSDLeft(bot, false),
                        new PositionSDRight(bot, false),
                        new WaitCommand(200),
                        new ActivateTransfer(bot, false)
                ).schedule();
                if (transferOn) {
                    transferOn = false;
                }
            }
            */
            TelemetryUtil.addData("Velocity: ", bot.shooterRight.getVelocity());
            TelemetryUtil.addData("target velocity: ", bot.shooter.getVelocity());
            TelemetryUtil.addData("Hood Position: ", bot.hood.getPos());
            TelemetryUtil.addData("ty: ", bot.ll.getTy());
            TelemetryUtil.addData("tx: ", bot.ll.getTx());
            TelemetryUtil.addData("goal dist: ", bot.ll.getGoalDistanceM());
            TelemetryUtil.addData("ll aim power: ", -bot.ll.AimPID());
            TelemetryUtil.addData("num balls: ", bot.beamBreaks.getNumBalls());
            //TelemetryUtil.addData("right top: " + rightTopBB.getState());
            bot.loop();
        }
        bot.limelight.stop();
    }
}