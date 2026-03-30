package org.firstinspires.ftc.teamcode.catscan.autos;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeYBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootFarHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootFarXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootFarYBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startFarXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startFarYBlue;

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
import org.firstinspires.ftc.teamcode.catscan.commands.FarShoot;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.ReverseIntake;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class FarBlueAuton extends LinearOpMode {
    public static Pose startPose = new Pose(startFarXBlue, startFarYBlue, Math.toRadians(90)); //fix
    public static Pose shootPose = new Pose(shootFarXBlue, shootFarYBlue, Math.toRadians(shootFarHeadingBlue));
    public static Pose gateIntake = new Pose(gateIntakeXBlue, gateIntakeYBlue, Math.toRadians(gateIntakeHeadingBlue));
    Bot bot;
    public Paths paths;


    public static class Paths {
        public PathChain startToShoot;
        public PathChain pickUpThirdSpike;
        public PathChain ThirdSpikeToShoot;
        public PathChain FirstCornerPickupPath;
        public PathChain retryCorner;
        public PathChain TurnToCornerPickup;
        public PathChain CornerToShootPath;
        public PathChain SecondCornerPickupPath;

        public Paths(Follower follower) {
            startToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,

                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(shootFarHeadingBlue))

                    .build();

            pickUpThirdSpike = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(57.042, 48.645),
                                    new Pose(64.758, 32.277),
                                    new Pose(11.239, 35.587)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootFarHeadingBlue), Math.toRadians(180), .4)

                    .build();



            ThirdSpikeToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(11.239, 35.587),

                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootFarHeadingBlue))

                    .build();
            TurnToCornerPickup = follower.pathBuilder().addPath(
                            new BezierLine(
                                    shootPose,

                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootFarHeadingBlue), Math.toRadians(200), .4)

                    .build();
            FirstCornerPickupPath = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(59.7, 10),
                                    new Pose(14, 11.5)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();
            retryCorner = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose (15, 11.5),
                                    new Pose (30, 25),
                                    new Pose(14, 11.5)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();

            CornerToShootPath = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(14, 11.5),
                                    new Pose(38, 21),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootFarHeadingBlue))

                    .build();
            SecondCornerPickupPath = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(19, 14),

                                    new Pose(16, 13)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(200), Math.toRadians(180))

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
                        new PositionDoors(bot, false, true)
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
                                new FollowPathCommand(bot.follower, paths.startToShoot),
                                new ActivateShooter(bot, 1590),//on
                                new ActivateIntake(bot, true)
                        ),
                        new WaitCommand(1300),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.pickUpThirdSpike),
                        new FollowPathCommand(bot.follower, paths.ThirdSpikeToShoot),
                        new WaitCommand(50),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.FirstCornerPickupPath),
                        new FollowPathCommand(bot.follower, paths.CornerToShootPath),
                        new WaitCommand(70),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.FirstCornerPickupPath),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.CornerToShootPath),
                                new SequentialCommandGroup(
                                        new WaitCommand(700),
                                        new ReverseIntake(bot)
                                )
                        ),
                        new WaitCommand(70),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.FirstCornerPickupPath),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.CornerToShootPath),
                                new SequentialCommandGroup(
                                        new WaitCommand(700),
                                        new ReverseIntake(bot)
                                )
                        ),
                        new WaitCommand(70),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.FirstCornerPickupPath),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.CornerToShootPath),
                                new SequentialCommandGroup(
                                        new WaitCommand(700),
                                        new ReverseIntake(bot)
                                )
                        ),
                        new WaitCommand(70),
                        new FarShoot(bot),
                        new FollowPathCommand(bot.follower, paths.pickUpThirdSpike)
                )
        );
    }
}