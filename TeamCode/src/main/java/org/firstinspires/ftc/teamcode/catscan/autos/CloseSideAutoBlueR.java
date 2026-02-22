package org.firstinspires.ftc.teamcode.catscan.autos;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateTransfer;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class CloseSideAutoBlueR extends LinearOpMode {
    public static Pose startPose = new Pose(startX, startY, Math.toRadians(startHeading)); //fix
    public static Pose shootPose = new Pose(shootX, shootY, Math.toRadians(shootHeading));
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
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(shootHeading))
                    .setBrakingStrength(0.7)
                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(53.23, 83.02),
                                    new Pose(63.73, 84.89),
                                    new Pose(15, 84)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(15, 84),
                                    new Pose(71.76, 80.66),
                                    new Pose(17.35, 71.06)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(15, 84),

                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootHeading))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(52.53, 54.17),
                                    new Pose(61.68, 59.85),
                                    new Pose(10.31, 59.3)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(10.3, 59.3),
                                    new Pose(54.8, 60.5),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootHeading))

                    .build();

            Path7 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(53, 37.75),
                                    new Pose(59.8, 34),
                                    new Pose(9.14, 35.64)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(180))
                    .setBrakingStrength(0.7)
                    .build();

            Path8 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(9.14, 35.64),
                                    new Pose(53.70, 54.17),
                                    new Pose(57.45, 104.365)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(146))
                    .build();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {
        TelemetryUtil.setup(telemetry);
        CommandScheduler.getInstance().reset();
        bot = new Bot(hardwareMap, startPose, false);
        paths = new Paths(bot.follower);
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
                        new ActivateIntake(bot, true),
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
