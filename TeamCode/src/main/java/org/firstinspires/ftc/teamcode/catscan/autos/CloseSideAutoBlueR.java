package org.firstinspires.ftc.teamcode.catscan.autos;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startHeading;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startX;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startY;

import com.pedropathing.follower.Follower;
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
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateTransfer;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class CloseSideAutoBlueR extends LinearOpMode {
    public static Pose startPose = new Pose(startX, startY, Math.toRadians(startHeading)); //fix
    Bot bot;
    public Paths paths;
    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;
        public PathChain Path8;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,
                                    new Pose(53.941, 95.687)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(135))
                    .setBrakingStrength(0.7)
                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(53.941, 95.687),
                                    new Pose(53.238, 83.023),
                                    new Pose(63.792, 84.899),
                                    new Pose(14.072, 84.195)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(14.072, 84.195),
                                    new Pose(71.765, 80.678),
                                    new Pose(17.355, 71.062)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(17.355, 71.062),

                                    new Pose(53.707, 95.687)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(53.707, 95.687),
                                    new Pose(52.534, 54.176),
                                    new Pose(61.681, 59.805),
                                    new Pose(10.319, 59.336)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(10.319, 59.336),
                                    new Pose(54.879, 60.508),
                                    new Pose(53.941, 95.922)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(134))

                    .build();

            Path7 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(53.941, 95.922),
                                    new Pose(53.003, 37.759),
                                    new Pose(59.805, 34.007),
                                    new Pose(9.147, 35.648)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(134), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path8 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(9.147, 35.648),
                                    new Pose(53.707, 54.176),
                                    new Pose(57.459, 104.365)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(142))
                    .build();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryUtil.setup(telemetry);
        CommandScheduler.getInstance().reset();
        bot = new Bot(hardwareMap, startPose, false);
        paths = new Paths(bot.follower);
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

    public void scheduleAuto(){
        CommandScheduler.getInstance().schedule(
                new RunCommand(() -> bot.follower.update()),
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.Path1),
                                new ActivateShooter(bot),//on
                                new ActivateTransfer(bot)//on
                        ),
                        new WaitCommand(200),
                        new PositionSDLeft(bot),
                        new WaitCommand(200),
                        new ActivateShooter(bot),//off
                        new ActivateTransfer(bot),//off
                        new FollowPathCommand(bot.follower, paths.Path2),
                        new FollowPathCommand(bot.follower, paths.Path3),
                        new FollowPathCommand(bot.follower, paths.Path4),
                        new FollowPathCommand(bot.follower, paths.Path5),
                        new FollowPathCommand(bot.follower, paths.Path6),
                        new FollowPathCommand(bot.follower, paths.Path7),
                        new FollowPathCommand(bot.follower, paths.Path8)

                )
        );
    }


}
