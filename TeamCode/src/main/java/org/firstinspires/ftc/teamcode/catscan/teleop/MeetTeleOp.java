package org.firstinspires.ftc.teamcode.catscan.teleop;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.ShooterPower;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

//@TeleOp(name = "v2 robot")
public class MeetTeleOp extends LinearOpMode {
    Pose startPose = new Pose(0, 0, 0);

    @Override
    public void runOpMode(){
        TelemetryUtil.setup(telemetry);
        Bot bot = new Bot(hardwareMap, startPose, true);
        GamepadEx gp1 = new GamepadEx(gamepad1);
        GamepadEx gp2 = new GamepadEx(gamepad2);
        bot.frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        bot.backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        bot.frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        bot.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        gp2.getGamepadButton(GamepadKeys.Button.B).whenPressed(()->{
            bot.limelight.pipelineSwitch(1); //blue
        });

        gp2.getGamepadButton(GamepadKeys.Button.A).whenPressed(()->{
            bot.limelight.pipelineSwitch(0); //red
        });

        gp1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ActivateShooter(bot));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ShooterPower(bot, true));
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ShooterPower(bot, false));
        gp1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new PositionDoors(bot, true, true));

        //gp1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ActivateIntake(bot));

        gp1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(()->{
            bot.hood.up();
            bot.hood.setPos();
        });
        gp1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(()->{
            bot.hood.down();
            bot.hood.setPos();
        });
        gp1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(()->{
            //bot.hood.setPos(.55);
            new ShooterPower(bot, 1.07).schedule();
        });
        gp1.getGamepadButton(GamepadKeys.Button.X).whenPressed(()->{
            //bot.hood.setPos(.6);
            new ShooterPower(bot, 1.3).schedule();
        });

        waitForStart();

        while(!isStopRequested() && opModeIsActive()){
            double y = -gamepad2.left_stick_y;
            double x = -gamepad2.left_stick_x * 1.1;
            double rx = -gamepad2.right_stick_x * .85;
            double d = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            bot.frontLeft.setPower(Math.pow((y - x - rx),5) / d);
            bot.backLeft.setPower(Math.pow((y + x - rx),5) / d);
            bot.frontRight.setPower(Math.pow((y + x + rx),5) / d);
            bot.backRight.setPower(Math.pow((y - x + rx),5) / d);
            TelemetryUtil.addData("Velocity: ", bot.shooterLeft.getVelocity());
            TelemetryUtil.addData("Motor Power: ", bot.shooter.getVelocity());
            TelemetryUtil.addData("Hood Position: ", bot.hood.getPos());
            bot.loop();
        }
        bot.limelight.stop();
    }
}