package org.firstinspires.ftc.teamcode.catscan.autos;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeHeadingRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeXRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeYBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeYRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootCloseXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootHeadingRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootXCloseRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootYCloseBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootYCloseRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseXRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseYRed;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseYBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startHeadingRed;

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
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.ActivateShooter;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class EighteenRedAuton extends LinearOpMode {
    public static Pose startPose = new Pose(startCloseXRed, startCloseYRed, Math.toRadians(startHeadingRed)); //fix
    public static Pose shootPose = new Pose(shootXCloseRed, shootYCloseRed, Math.toRadians(shootHeadingRed));
    public static Pose gateIntake = new Pose(gateIntakeXRed, gateIntakeYRed, Math.toRadians(gateIntakeHeadingRed));
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
        public PathChain Path9;
        public PathChain Path10;
        public PathChain Path11;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(startHeadingRed), Math.toRadians(shootHeadingRed))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose((144- 51.753), 56.735),
                                    new Pose((144- 60.821), 59.904),
                                    new Pose((144- 11), 59.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingRed), Math.toRadians(0), .6)

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose((144- 11), 59.000),
                                    new Pose((144- 47.687), 56.696),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(shootHeadingRed))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose((144- 47.320), 57.021),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingRed), Math.toRadians(gateIntakeHeadingRed))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose((144- 47.302), 57.117),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeadingRed), Math.toRadians(shootHeadingRed))

                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose((144- 47.318), 56.925),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingRed), Math.toRadians(gateIntakeHeadingRed))

                    .build();

            Path7 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose((144- 47.437), 57.096),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeadingRed), Math.toRadians(shootHeadingRed))

                    .build();
            Path8 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose((144- 47.318), 56.925),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingRed), Math.toRadians(gateIntakeHeadingRed))

                    .build();

            Path9 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose((144- 47.437), 57.096),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeadingRed), Math.toRadians(shootHeadingRed))

                    .build();



            Path10 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose((144- 53.303), 84.261),
                                    new Pose((144- 18), 84.256)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingRed), Math.toRadians(0), .6)

                    .build();

            Path11 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose((144- 18), 84.256),
                                    new Pose((144- 54), 104.755)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(32))

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
                        new PositionHood(bot, .3, (1.01 - .3)),
                        new PositionSDLeft(bot, false),
                        new PositionSDRight(bot, false),
                        new PositionDoors(bot, true, false)
                )
        );
        while(opModeInInit()){
            bot.loop();
        }
        waitForStart();
        scheduleAuto();
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
                                new ActivateShooter(bot, 1120),//on
                                new ActivateIntake(bot, true)
                        ),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path2),
                        new FollowPathCommand(bot.follower, paths.Path3),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path4),
                        new WaitCommand(1000),
                        new FollowPathCommand(bot.follower, paths.Path5),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path6),
                        new WaitCommand(1000),
                        new FollowPathCommand(bot.follower, paths.Path7),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path8),
                        new WaitCommand(1000),
                        new FollowPathCommand(bot.follower, paths.Path9),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path10),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.Path11),
                                new PositionHood(bot, .28, (1.01 - .28)),
                                new ActivateShooter(bot, 1080)
                        ),
                        new Shoot(bot)
                )
        );
    }
}