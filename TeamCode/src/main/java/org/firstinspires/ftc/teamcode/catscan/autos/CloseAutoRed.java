package org.firstinspires.ftc.teamcode.catscan.autos;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.AutoShootGPP;
import org.firstinspires.ftc.teamcode.catscan.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

//@Autonomous(name="close red")
public class CloseAutoRed extends LinearOpMode {
    int a = 144;
    int b = 180;
    private ElapsedTime timer;
    private final Pose startPose = new Pose(a-25.605, 128.496, Math.toRadians(b-135));//change start pose
    Bot bot;
    private PathChain[] path = new PathChain[8];

    public void buildPaths(){
        path[0] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-25.605, 128.496), new Pose(a-55.909, 91.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-135), Math.toRadians(b-140))
                .build();

        path[1] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 91.139),
                                new Pose(a-58.083, 72.514),
                                new Pose(a-54.090, 74.628),
                                new Pose(a-21, 71.863)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-140), Math.toRadians(b-180), .6)
                .build();

        path[2] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-21, 71.863), new Pose(a-55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(b-140))
                .build();

        path[3] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 95.139),
                                new Pose(a-58.023, 55.848),
                                new Pose(a-59.667, 52.137),
                                new Pose(a-12.648, 44.667)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-140), Math.toRadians(b-180), .56)
                .build();

        path[4] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-12.648, 44.667),
                                new Pose(a-38.37,35.08),
                                new Pose(a-57.78, 71.647),
                                new Pose(a-55.909, 95.139)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(b-140))
                .build();

        path[5] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 95.139),
                                new Pose(a-56.613, 31.646),
                                new Pose(a-59.902, 29.532),
                                new Pose(a-12.648, 25.706)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-140), Math.toRadians(b-180), .6)
                .build();

        path[6] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-12.648, 23.706), new Pose(a-55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(b-140))
                .build();

        path[7] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-55.909, 95.139), new Pose(a-33.122, 76.346))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-140), Math.toRadians(b-135))
                .build();
    }

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.07),
                                new FollowPathCommand(bot.follower, path[0]),//go to shoot
                                //new PositionHood(bot, .55),
                                new PositionDoors(bot, true, false)
                        ),
                        new WaitCommand(750),
                        new AutoShootGPP(bot), //shoot first 3
                        new ParallelCommandGroup(
                                new PositionDoors(bot, false, true),
                                new ActivateIntake(bot),
                                new FollowPathCommand(bot.follower, path[1])//go to pick up
                        ),
                        new WaitCommand(400), //3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.07),
                                new FollowPathCommand(bot.follower, path[2])//go to shoot
                        ),
                        new WaitCommand(750),
                        new AutoShootGPP(bot), //3 balls scored (6 total)
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, path[3])//go to pick up
                        ),
                        new WaitCommand(600), //(2nd) 3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.07),
                                new FollowPathCommand(bot.follower, path[4])//go to shoot
                        ),
                        new WaitCommand(750),
                        new AutoShootGPP(bot)//, 3 balls scored (9 total)
                        /*
                        new FollowPathCommand(bot.follower, path[5]), //go to pick up
                        new WaitCommand(600), //(3rd) 3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.07),
                                new FollowPathCommand(bot.follower, path[6])//go to shoot
                        ),
                        new WaitCommand(750),
                        new AutoShootGPP(bot), //3 balls scored (12 total)
                        new WaitCommand(500),
                        new FollowPathCommand(bot.follower, path[7])*/
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