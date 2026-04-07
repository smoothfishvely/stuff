package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootPGP extends SequentialCommandGroup {
    public AutoShootPGP(Bot bot){
        addCommands(
                new ActivateIntake(bot, false),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(80),
                new SetTransferPower(bot, 0),
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, false),
                new WaitCommand(300),
                new ActivateIntake(bot, true),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(300),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(300),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false),
                new SetTransferPower(bot, .3)
        );
        addRequirements(bot.shooter);
    }
}
