package org.firstinspires.ftc.teamcode.catscan.autos;

import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootHeading;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootX;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootY;
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
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;

@Autonomous
public class AuraRedAuto extends LinearOpMode {
    public static Pose startPose = new Pose(118.440, 127.580, Math.toRadians(45)); //fix
    public static Pose shootPose = new Pose(shootX, shootY, Math.toRadians(shootHeading));
    Bot bot;
    public AuraRedAuto.Paths paths;
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
                                    new Pose(118.440, 127.580),

                                    new Pose(90.100, 95.680)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(40))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(90.100, 95.680),
                                    new Pose(90.770, 83.020),
                                    new Pose(80.210, 84.890),
                                    new Pose(129.000, 84.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0), .6)
                    .setBrakingStrength(0.7)

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(129.000, 84.000),
                                    new Pose(72.240, 80.670),
                                    new Pose(126.650, 71.060)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(126.650, 71.060),

                                    new Pose(90.100, 95.680)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(90.100, 95.680),
                                    new Pose(91.470, 54.170),
                                    new Pose(82.320, 59.800),
                                    new Pose(133.690, 59.300)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0), .6)
                    .setBrakingStrength(0.7)

                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(133.690, 59.300),
                                    new Pose(89.200, 60.500),
                                    new Pose(90.100, 95.680)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(40))


                    .build();

            Path7 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(90.100, 95.680),
                                    new Pose(91.000, 37.750),
                                    new Pose(84.200, 34.000),
                                    new Pose(134.860, 35.640)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(40), Math.toRadians(0), .6)
                    .setBrakingStrength(0.7)

                    .build();

            Path8 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(134.860, 35.640),
                                    new Pose(90.300, 54.170),
                                    new Pose(86.550, 104.360)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(35))
                    .build();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryUtil.setup(telemetry);
        CommandScheduler.getInstance().reset();
        bot = new Bot(hardwareMap, startPose, false);
        paths = new AuraRedAuto.Paths(bot.follower);
        CommandScheduler.getInstance().schedule(
                new ParallelCommandGroup(
                        new PositionHood(bot, .41, .6 )
                )
        );
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
                                new ActivateShooter(bot, 1)//on
                        ),
                        new ActivateIntake(bot),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path2),
                        new FollowPathCommand(bot.follower, paths.Path4),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path5),
                        new FollowPathCommand(bot.follower, paths.Path6),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path7),
                        new FollowPathCommand(bot.follower, paths.Path8),
                        new Shoot(bot)
                )
        );
    }
}

