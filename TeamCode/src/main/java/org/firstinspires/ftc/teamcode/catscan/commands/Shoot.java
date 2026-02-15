package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Bot bot){
        addCommands(
                new PositionSDRight(bot, true),
                new WaitCommand(500),
                new ActivateTransfer(bot, true),//on
                new WaitCommand(1400),
                new ActivateTransfer(bot, false),//off
                new PositionSDRight(bot, false)
       );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}