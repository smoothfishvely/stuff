package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Bot bot){
        addCommands(
                new ParallelCommandGroup(
                        new ActivateShooter(bot),
                        new ActivateTransfer(bot)
                ),
                new WaitCommand(200),
                new PositionSDLeft(bot)
        );
        addRequirements(bot.shooter, bot.shooterDoors);
    }
}
