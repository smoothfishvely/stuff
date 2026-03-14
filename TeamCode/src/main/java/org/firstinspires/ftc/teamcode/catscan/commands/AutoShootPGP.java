package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootPGP extends SequentialCommandGroup {
    public AutoShootPGP(Bot bot){
        addCommands(
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, bot.getAdjustedTransferPower() + .25),
                new ActivateIntake(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, 0),
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, false),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(100),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new SetTransferPower(bot, bot.getAdjustedTransferPower() + .1),
                new WaitCommand(200),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new SetTransferPower(bot, .2)
        );
        addRequirements(bot.shooter);
    }
}
