package org.firstinspires.ftc.teamcode.catscan.autos;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.catscan.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

//@Autonomous(name="gate goon")
public class CloseGateAutoBlue extends LinearOpMode {
    private ElapsedTime timer;
    private final Pose startPose = new Pose(25.605, 128.496, Math.toRadians(135));//change start pose
    Bot bot;
    private PathChain[] path = new PathChain[9];

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
                                new Pose(53.794, 78.460),
                                new Pose(53.090, 83.628),
                                new Pose(7.670, 79.860)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[2] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(7.670, 79.860),
                                new Pose(41.344, 72.822),
                                new Pose(12.517, 72.762)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))
                .build();

        path[3] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(12.517, 75.762),
                                new Pose(38.055, 72.352),
                                new Pose(65.540, 87.152),
                                new Pose(54.499, 92.085),
                                new Pose(55.910, 94.904)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(140))
                .build();

        path[4] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(55.910, 94.904),
                                new Pose(53.325, 50.976),
                                new Pose(64.835, 57.788),
                                new Pose(0.600, 54.667)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[5] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(0.600, 54.667),
                                new Pose(51.915, 47.217),
                                new Pose(55.909, 76.581),
                                new Pose(54.909, 94.669)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();

        path[6] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(54.909, 94.669),
                                new Pose(52.385, 21.847),
                                new Pose(57.318, 37.351),
                                new Pose(0.670, 35.471)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(180))
                .build();

        path[7] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(0.670, 35.471),
                                new Pose(42.989, 44.163),
                                new Pose(55.909, 94.669)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(140))
                .build();

        path[8] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(55.909, 94.669), new Pose(39.700, 75.876))
                )
                .setLinearHeadingInterpolation(Math.toRadians(140), Math.toRadians(140))
                .build();
    }

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        /*new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.1),
                                new FollowPathCommand(bot.follower, path[0]),//go to shoot
                                new PositionHood(bot, .5),
                                new PositionDoors(bot, true, false)
                        ),
                        new WaitCommand(600),
                        new AutoShootGPP(bot), //shoot first 3
                        new ParallelCommandGroup(
                                new PositionDoors(bot, false, true),
                                new ActivateIntake(bot),
                                new FollowPathCommand(bot.follower, path[1])//go to pick up
                        )*/
                        new FollowPathCommand(bot.follower, path[0]),
                        new FollowPathCommand(bot.follower, path[1]),
                        new FollowPathCommand(bot.follower, path[2]),
                        new FollowPathCommand(bot.follower, path[3]),
                        new FollowPathCommand(bot.follower, path[4]),
                        new FollowPathCommand(bot.follower, path[5]),
                        new FollowPathCommand(bot.follower, path[6]),
                        new FollowPathCommand(bot.follower, path[7]),
                        new FollowPathCommand(bot.follower, path[8])
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