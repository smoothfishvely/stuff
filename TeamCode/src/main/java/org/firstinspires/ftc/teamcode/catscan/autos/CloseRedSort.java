package org.firstinspires.ftc.teamcode.catscan.autos;

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

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.AutoSort;
import org.firstinspires.ftc.teamcode.catscan.commands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan.commands.GetMotif;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

@Autonomous(name = "red sorting")
public class CloseRedSort extends LinearOpMode {
    private ElapsedTime timer;

    double a = 144, b = 180;
    private final Pose startPose = new Pose(a-25.605, 128.496, Math.toRadians(b-135));//change start pose
    Bot bot;
    private PathChain[] path = new PathChain[12];

    public void buildPaths(){
        path[0] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-25.605, 128.496), new Pose(a-59.909, 107.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(120))
                .build();
        path[11] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine (new Pose(a-59.909, 107.139), new Pose(a-55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-67), Math.toRadians(40))
                .build();
        path[1] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 95.139),
                                new Pose(a-57.083, 71.514),
                                new Pose(a-53.090, 73.628),
                                new Pose(a-27.679, 72.963)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(b-180), .55)
                .build();
        path[2] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(a-27.679, 72.963),
                                new Pose(a-20.679, 72.963)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(b-180))
                .build();
        path[3] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-20.679, 72.863), new Pose(a-55.909, 95.139))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(40))
                .build();
        path[6] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 95.139),
                                new Pose(a-56.613, 26.646),
                                new Pose(a-59.902, 24.532),
                                new Pose(a-37.5, 26.996)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(b-180), .55)
                .build();
        path[10] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-37.5, 26.996), new Pose(a-9.67, 26.996))
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(b-180))
                .build();
        path[7] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-9.67, 26.996),
                                new Pose(a-56.613, 36.646),
                                new Pose(a-59.902, 34.532),
                                new Pose(a-55.909, 95.139)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(40), .67)
                .build();
        path[4] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-55.909, 95.139),
                                new Pose(a-58.023, 46.848),
                                new Pose(a-59.667, 50.137),
                                new Pose(a-9.67, 47.667)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(b-180), .54)
                .build();
        path[5] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(a-9.67, 47.667),
                                new Pose(a-38.37,35.08),
                                new Pose(a-57.78, 71.647),
                                new Pose(a-55.909, 95.139)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(b-180), Math.toRadians(40))
                .build();
        path[8] = bot.follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(a-55.909, 95.139), new Pose(a-33.122, 76.346))
                )
                .setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(b-135))
                .build();
    }

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.067),
                                new FollowPathCommand(bot.follower, path[0]),//go to shoot
                                //new PositionHood(bot, .565),
                                new PositionDoors(bot, false, false)
                        ),
                        new GetMotif(bot),
                        new FollowPathCommand(bot.follower, path[11]),
                        new WaitCommand(350),
                        new AutoSort(bot), //shoot first 3
                        new ParallelCommandGroup(
                                new PositionDoors(bot, false, true),
                                new ActivateIntake(bot),
                                new FollowPathCommand(bot.follower, path[1])//go to pick up
                        ),
                        new WaitCommand(290),
                        new PositionDoors(bot,true, false),
                        new FollowPathCommand(bot.follower, path[2]), //2 balls in bot
                        new WaitCommand(90), //3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.067),
                                new FollowPathCommand(bot.follower, path[3])//go to shoot
                        ),
                        new WaitCommand(250),
                        new AutoSort(bot), //3 balls scored (6 total)
                        new ParallelCommandGroup(
                                new PositionDoors(bot,true, false),
                                new FollowPathCommand(bot.follower, path[6])//go to pick up
                        ),
                        new WaitCommand(290),
                        new PositionDoors(bot,false, true),
                        new FollowPathCommand(bot.follower, path[10]),
                        new WaitCommand(280),//(2nd) 3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.067),
                                new FollowPathCommand(bot.follower, path[7])//go to shoot
                        ),
                        new WaitCommand(250),
                        new AutoSort(bot),//, 3 balls scored (9 total)
                        new ParallelCommandGroup(
                                new PositionDoors(bot, true, true),
                                new FollowPathCommand(bot.follower, path[4])//go to shoot
                        ),
                        new WaitCommand(350), //(3rd) 3 balls in bot
                        new ParallelCommandGroup(
                                new ActivateShooter(bot, 1.067),
                                new FollowPathCommand(bot.follower, path[5])//go to shoot
                        ),
                        new WaitCommand(250),
                        new AutoSort(bot), //3 balls scored (12 total)
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
