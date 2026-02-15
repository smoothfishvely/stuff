package org.firstinspires.ftc.teamcode.catscan.autos;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeHeading;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeX;
import static org.firstinspires.ftc.teamcode.catscan.autos.AutoConstants.gateIntakeY;
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
import org.firstinspires.ftc.teamcode.catscan.commands.PositionSDRight;
import org.firstinspires.ftc.teamcode.catscan.commands.Shoot;
import org.firstinspires.ftc.teamcode.catscan.commands.ShooterPower;
import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;
import org.firstinspires.ftc.teamcode.catscan.subsystems.TelemetryUtil;
@Autonomous
public class MKAuto extends LinearOpMode {
    public static Pose startPose = new Pose(startX, startY, Math.toRadians(startHeading)); //fix
    public static Pose shootPose = new Pose(shootX, shootY, Math.toRadians(shootHeading));
    public static Pose gateIntake = new Pose(gateIntakeX, gateIntakeY, Math.toRadians(gateIntakeHeading));
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

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    startPose,
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(startHeading), Math.toRadians(shootHeading))

                    .build();

            Path2 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(51.753, 56.735),
                                    new Pose(60.821, 59.904),
                                    new Pose(9.500, 59.000)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(180), .6)

                    .build();

            Path3 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    new Pose(9.500, 59.000),
                                    new Pose(47.687, 56.696),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(shootHeading))

                    .build();

            Path4 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(47.320, 57.021),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(gateIntakeHeading))

                    .build();

            Path5 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose(47.302, 57.117),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeading), Math.toRadians(shootHeading))

                    .build();

            Path6 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(47.318, 56.925),
                                    gateIntake
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(gateIntakeHeading))

                    .build();

            Path7 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    gateIntake,
                                    new Pose(47.437, 57.096),
                                    shootPose
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(gateIntakeHeading), Math.toRadians(shootHeading))

                    .build();

            Path8 = follower.pathBuilder().addPath(
                            new BezierCurve(
                                    shootPose,
                                    new Pose(53.303, 84.261),
                                    new Pose(16.532, 84.256)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(shootHeading), Math.toRadians(180), .6)

                    .build();

            Path9 = follower.pathBuilder().addPath(
                            new BezierLine(
                                    new Pose(16.532, 84.256),

                                    new Pose(57.825, 104.755)
                            )
                    ).setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(150))

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
                        new PositionHood(bot, .45, .56 )
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
                                new ActivateShooter(bot, 1220),//on
                                new ActivateIntake(bot)
                        ),
                        new WaitCommand(1000),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path2),
                        new FollowPathCommand(bot.follower, paths.Path3),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path4),
                        new WaitCommand(1400),
                        new FollowPathCommand(bot.follower, paths.Path5),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path6),
                        new WaitCommand(1400),
                        new FollowPathCommand(bot.follower, paths.Path7),
                        new Shoot(bot),
                        new FollowPathCommand(bot.follower, paths.Path8),
                        new ParallelCommandGroup(
                                new FollowPathCommand(bot.follower, paths.Path9),
                                new PositionHood(bot, .43, .58),
                                new ActivateShooter(bot, 1200)
                        ),
                        new Shoot(bot),
                        new PositionSDRight(bot, true),
                        new ActivateShooter(bot, 0)
                )
        );
    }
}