package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Bot bot){
        addCommands(
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, true),
                new ActivateIntake(bot, true),
                new WaitCommand(40),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(800),
                new SetTransferPower(bot, .3),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)

       );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}