package org.firstinspires.ftc.teamcode.catscan.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    public static double vel = 0;
    boolean shootOn;
    boolean sdl;
    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryUtil.setup(telemetry);
        Bot bot = new Bot(hardwareMap, startPose, true);
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
                //new ActivateShooter(bot, 0).schedule();
            } else {
                //new ActivateShooter(bot, 1240).schedule();
            }
        });
        //gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ShooterPower(bot, true));
        //gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ShooterPower(bot, false));

        gp1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ActivateIntake(bot));
        gp1.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
            new ActivateTransfer(bot).schedule();
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(()->{
            bot.hood.up();
            bot.hood.setPos();
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(()->{
            bot.hood.down();
            bot.hood.setPos();
        });

        gp1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new PositionSDRight(bot));
        gp1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(()->{
            sdl = !sdl;
            if(!sdl){
                new PositionSDLeft(bot, false).schedule();
            } else {
                new PositionSDLeft(bot, true).schedule();
            }
        });

        waitForStart();
        while(!isStopRequested() && opModeIsActive()){

            bot.shooter.setVelocity(vel);

            double y = -gamepad2.left_stick_y;
            double x = -gamepad2.left_stick_x * 1.1;
            double rx = -gamepad2.right_stick_x * .85;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            bot.frontLeft.setPower(Math.pow((y - x - rx),5) / d);
            bot.backLeft.setPower(Math.pow((y + x - rx),5) / d);
            bot.frontRight.setPower(Math.pow((y + x + rx),5) / d);
            bot.backRight.setPower(Math.pow((y - x + rx),5) / d);
            TelemetryUtil.addData("Velocity: ", bot.shooterRight.getVelocity());
            TelemetryUtil.addData("target velocity: ", bot.shooter.getVelocity());
            TelemetryUtil.addData("Hood Position: ", bot.hood.getPos());
            TelemetryUtil.addData("ty: ", bot.ll.getTy());
            TelemetryUtil.addData("tx: ", bot.ll.getTx());
            TelemetryUtil.addData("goal dist: ", bot.ll.getGoalDistanceCM());
            bot.loop();
        }
        bot.limelight.stop();
    }
}
