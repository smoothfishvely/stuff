package org.firstinspires.ftc.teamcode.catscan.autos;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeYBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootCloseXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.shootYCloseBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startHeadingBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseXBlue;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.startCloseYBlue;

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
import org.firstinspires.ftc.teamcode.catscan.commands.AutoSortShoot;
import org.firstinspires.ftc.teamcode.catscan.commands.CamOn;
import org.firstinspires.ftc.teamcode.catscan.commands.GetMotif;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionDoors;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionHood;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDLeft;
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.ReverseIntake;
import org.firstinspires.ftc.teamcode.catscan.commands.SetIntakePower;
import org.firstinspires.ftc.teamcode.catscan.commands.SetTransferPower;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.commands.SortOff;
import org.firstinspires.ftc.teamcode.catscan.commands.SortOn;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class FifteenFullSortedBludAuton extends LinearOpMode {
    public static Pose startPose = new Pose(startCloseXBlue, startCloseYBlue, Math.toRadians(startHeadingBlue)); //fix
    public static Pose shootPose = new Pose(shootCloseXBlue, shootYCloseBlue, Math.toRadians(shootHeadingBlue));
    public static Pose gateIntake = new Pose(gateIntakeXBlue, gateIntakeYBlue, Math.toRadians(gateIntakeHeadingBlue));
    Bot bot;
    public Paths paths;

    public static class Paths {
        public PathChain StartToMotif;
        public PathChain MotifToShoot;
        public PathChain ShootToSpike2;
        public PathChain Spike2ToShoot;
        public PathChain ShootToGate;
        public PathChain GateToShoot;
        public PathChain ShootToFirstSpike;
        public PathChain MidFirstSpike;
        public PathChain FirstSpikeToLastShoot;
        public PathChain ShootToThirdSpike;
        public PathChain ThirdSpikeToShoot;
        public PathChain MidThirdSpike;

        public Paths(Follower follower) {
            StartToMotif = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(startHeadingBlue), Math.toRadians(80))

                    .build();

            MotifToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(80), Math.toRadians(shootHeadingBlue))

                    .build();

            ShootToSpike2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(51.753, 56.735),
                                    new Pose(60.821, 59.904),
                                    new Pose(9.500, 59.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingBlue), Math.toRadians(180), .6)

                    .build();

            Spike2ToShoot = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(9.500, 59.000),
                                    new Pose(47.687, 56.696),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootHeadingBlue))

                    .build();

            ShootToGate = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(47.320, 57.021),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingBlue), Math.toRadians(gateIntakeHeadingBlue))

                    .build();

            GateToShoot = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose(47.302, 57.117),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeadingBlue), Math.toRadians(shootHeadingBlue))

                    .build();

            ShootToFirstSpike = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(53.303, 84.261),
                                    new Pose(23, 84.256)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingBlue), Math.toRadians(180), .6)

                    .build();

            MidFirstSpike = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(23, 84.256),
                                    new Pose(16, 84.256)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();


            FirstSpikeToLastShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(16, 84.256),
                                    new Pose(54, 104.755)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(152))

                    .build();
            ShootToThirdSpike = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(54.303, 44),
                                    new Pose(52, 31),
                                    new Pose(33, 35)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeadingBlue), Math.toRadians(180), .6)

                    .build();

            MidThirdSpike = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(33, 35),
                                    new Pose(10, 35)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180))

                    .build();

            ThirdSpikeToShoot = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(10, 35),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootHeadingBlue))

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
                        new PositionHood(bot, .28, (1.01 - .28)),
                        new PositionSDLeft(bot, false),
                        new PositionSDRight(bot, false),
                        new CamOn(bot),
                        new PositionDoors(bot, false, true)
                )
        );
        while(opModeInInit()){
            bot.loop();
        }
        bot.ll.setPipeline(2);
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
                                new FollowPathCommand(bot.follower, paths.StartToMotif),
                                new ActivateShooter(bot, 1100),//on
                                new ActivateIntake(bot, true)
                        ),
                        new GetMotif(bot),
                        new FollowPathCommand(bot.follower, paths.MotifToShoot),
                        new WaitCommand(300),
                        new Shoot(bot),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.ShootToSpike2),
                                new ReverseIntake(bot)
                        ),
                        new FollowPathCommand(bot.follower, paths.Spike2ToShoot),
                        new Shoot(bot),
                        new SortOn(bot),
                        new SetIntakePower(bot, .9),
                        new FollowPathCommand(bot.follower, paths.ShootToGate),
                        new WaitCommand(1100),
                        new SetIntakePower(bot, 1),
                        new SetTransferPower(bot, .5),
                        new FollowPathCommand(bot.follower, paths.GateToShoot),
                        new ParallelCommandGroup(
                                new AutoSortShoot(bot),
                                new WaitCommand(1700)
                        ),
                        new SortOff(bot),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.ShootToThirdSpike),
                                new ReverseIntake(bot),
                                new PositionDoors(bot, true, false)
                        ),
                        new WaitCommand(300),
                        new PositionDoors(bot, false, true),
                        new FollowPathCommand(bot.follower, paths.MidThirdSpike),
                        new FollowPathCommand(bot.follower, paths.ThirdSpikeToShoot),
                        new ParallelCommandGroup(
                                new AutoSortShoot(bot),
                                new WaitCommand(1700)
                        ),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.ShootToFirstSpike),
                                new ReverseIntake(bot),
                                new PositionDoors(bot, false, true)
                        ),
                        new WaitCommand(300),
                        new PositionDoors(bot, true, false),
                        new FollowPathCommand(bot.follower, paths.MidFirstSpike),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.FirstSpikeToLastShoot),
                                new PositionHood(bot, .28, (1.01 - .28)),
                                new ActivateShooter(bot, 1050)
                        ),
                        new ParallelCommandGroup(
                                new AutoSortShoot(bot),
                                new WaitCommand(1200)
                        )
                )
        );
    }
}