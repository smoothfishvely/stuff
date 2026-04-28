package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class FarShoot extends SequentialCommandGroup {
    public FarShoot(Bot bot){
        addCommands(
                new SetTransferPower(bot, bot.getAdjustedFarTransferPower()),
                new WaitCommand(100),
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, true),
                new WaitCommand(1200),
                new SetTransferPower(bot, .2),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false),
                new WaitCommand(200),
                new SetBBFalse(bot)
        );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}