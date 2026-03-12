package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class FarShoot extends SequentialCommandGroup {
    public FarShoot(Bot bot){
        addCommands(
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, bot.getAdjustedFarTransferPower() + .1),
                new ActivateIntake(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, .2),
                new WaitCommand(200),
                new SetTransferPower(bot, bot.getAdjustedFarTransferPower()),
                new WaitCommand(80),
                new SetTransferPower(bot, -.01),
                new WaitCommand(120),
                new SetTransferPower(bot, bot.getAdjustedFarTransferPower()+ .15),
                new WaitCommand(200),
                new SetTransferPower(bot, .2),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)

        );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}