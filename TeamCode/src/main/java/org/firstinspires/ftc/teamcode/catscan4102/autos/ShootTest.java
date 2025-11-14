package org.firstinspires.ftc.teamcode.catscan4102;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan4102.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan4102.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan4102.commands.AutoShoot;
import org.firstinspires.ftc.teamcode.catscan4102.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan4102.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan4102.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan4102.subsystems.TelemetryUtil;

@Autonomous(name="shooter")
public class ShootTest extends LinearOpMode {
    private ElapsedTime timer;
    private final Pose startPose = new Pose(25.605, 128.496, Math.toRadians(135));//change start pose
    Bot bot;
    private PathChain[] path = new PathChain[8];

    public void buildPaths(){
        path[0] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(25.605, 128.496), new Pose(55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(140))
                .build();

        path[1] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(55.909, 95.139),
                                new Pose(57.083, 81.514),
                                new Pose(53.090, 83.628),
                                new Pose(7.679, 79.863)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[2] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(7.679, 79.863), new Pose(55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();

        path[3] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(55.909, 95.139),
                                new Pose(58.023, 56.848),
                                new Pose(59.667, 60.137),
                                new Pose(9.148, 59.667)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[4] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(9.148, 59.667), new Pose(55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();

        path[5] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(55.909, 95.139),
                                new Pose(56.613, 36.646),
                                new Pose(59.902, 34.532),
                                new Pose(9.148, 35.706)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[6] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(9.148, 35.706), new Pose(55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(40))
                .build();

        path[7] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.909, 95.139), new Pose(33.122, 76.346))
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(135))
                .build();
    }

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        new ActivateShooter(bot, 1.067),
                        new WaitCommand(2000),
                        new ActivateShooter(bot)
                )
        );
    }

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryUtil.setup(telemetry);
        timer = new ElapsedTime();
        timer.reset();
        CommandScheduler.getInstance().reset();
        bot = new Bot(hardwareMap, startPose, false);
        buildPaths();
        while(opModeInInit()){
            bot.loop();
        }
        scheduleAuto();
        waitForStart();
        while(!isStopRequested() && opModeIsActive()){
            bot.loop();
        }
        CommandScheduler.getInstance().reset();
    }
}