package org.firstinspires.ftc.teamcode.catscan4102.autos;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.catscan4102.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan4102.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan4102.subsystems.TelemetryUtil;

@Autonomous(name="e goon")
public class FarAuto extends LinearOpMode {
    private ElapsedTime timer;
    private final Pose startPose = new Pose(55.909, 8.222, Math.toRadians(90));//change start pose
    Bot bot;
    private PathChain[] path = new PathChain[8];

    public void buildPaths(){
        path[0] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.909, 8.222), new Pose(61.312, 20.202))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(115))
                .build();

        path[1] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(61.312, 20.202),
                                new Pose(58.258, 35.471),
                                new Pose(74.467, 36.646),
                                new Pose(17.148, 35.706)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                .build();

        path[2] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.148, 35.706), new Pose(61.312, 20.437))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))
                .build();

        path[3] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(61.312, 20.437),
                                new Pose(60.842, 65.540),
                                new Pose(70.708, 57.318),
                                new Pose(17.148, 59.902)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                .build();

        path[4] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(17.148, 59.902), new Pose(61.312, 20.437))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))
                .build();

        path[5] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(61.312, 20.437),
                                new Pose(58.493, 85.977),
                                new Pose(71.648, 84.803),
                                new Pose(18.323, 84.098)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(180))
                .build();

        path[6] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(18.323, 84.098), new Pose(61.312, 20.202))
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(115))
                .build();

        path[7] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(61.312, 20.202), new Pose(53.560, 29.129))
                )
                .setLinearHeadingInterpolation(Math.toRadians(115), Math.toRadians(115))
                .build();
    }

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        new FollowPathCommand(bot.follower, path[0]),
                        new FollowPathCommand(bot.follower, path[1]),
                        new FollowPathCommand(bot.follower, path[2]),
                        new FollowPathCommand(bot.follower, path[3]),
                        new FollowPathCommand(bot.follower, path[4]),
                        new FollowPathCommand(bot.follower, path[5]),
                        new FollowPathCommand(bot.follower, path[6]),
                        new FollowPathCommand(bot.follower, path[7])
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
