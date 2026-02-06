package org.firstinspires.ftc.teamcode.catscan.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateTransfer;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.ShooterPower;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

@Configurable
@TeleOp(name = "4102 lt drive")
public class LTTeleOp extends LinearOpMode {
    Pose startPose = new Pose(0, 0, 0);
    double rx;
    boolean shootOn;
    boolean ont, diddy;
    Bot bot;
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
        gp1.getGamepadButton(GamepadKeys.Button.A).whenPressed(()->{
            shootOn = !shootOn;
            if(!shootOn) {
                new ActivateShooter(bot, 0).schedule();
            } else {
                new ActivateShooter(bot, bot.getRizz()).schedule();
            }
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ShooterPower(bot, true));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ShooterPower(bot, false));
        gp1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ActivateIntake(bot));
        gp1.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
            ont = !ont;
            if(!ont){
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

        gp2.getGamepadButton(GamepadKeys.Button.A).whileHeld(()->{
            bot.follower.turnTo(Math.toRadians(rx));
        });

        gp2.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{

        });

        waitForStart();
        bot.follower.startTeleopDrive();
        while(!isStopRequested() && opModeIsActive()){

            /*if (gamepad2.a) {
                rx = bot.getAimPower();
            }
            else {
                rx = -gamepad2.right_stick_x * .967;
            }
            double d = Math.max(Math.abs(-gamepad2.left_stick_y) + Math.abs(-gamepad2.left_stick_x * 1.1) + Math.abs(rx), 1);
            bot.frontLeft.setPower(Math.pow(((-gamepad2.left_stick_y) - (-gamepad2.left_stick_x * 1.1) - rx),5) / d);
            bot.backLeft.setPower(Math.pow(((-gamepad2.left_stick_y) + (-gamepad2.left_stick_x * 1.1) - rx),5) / d);
            bot.frontRight.setPower(Math.pow(((-gamepad2.left_stick_y) + (-gamepad2.left_stick_x * 1.1) + rx),5) / d);
            bot.backRight.setPower(Math.pow(((-gamepad2.left_stick_y) - (-gamepad2.left_stick_x * 1.1) + rx),5) / d);*/
            bot.follower.setTeleOpDrive(Math.pow(-gamepad2.left_stick_y, 3), Math.pow(-gamepad2.left_stick_x * 1.1,3), Math.pow(-gamepad2.right_stick_x * .967,3), true);
            TelemetryUtil.addData("Velocity: ", bot.shooterRight.getVelocity());
            TelemetryUtil.addData("target velocity: ", bot.shooter.getVelocity());
            TelemetryUtil.addData("Hood Position: ", bot.hood.getPos());
            TelemetryUtil.addData("ty: ", bot.ll.getTy());
            TelemetryUtil.addData("tx: ", bot.ll.getTx());
            TelemetryUtil.addData("goal dist: ", bot.ll.getGoalDistanceM());
            TelemetryUtil.addData("aim power: ", bot.getAimPower());
            bot.loop();
        }
        bot.limelight.stop();
    }
}