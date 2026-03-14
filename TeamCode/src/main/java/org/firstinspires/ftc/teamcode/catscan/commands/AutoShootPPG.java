package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootPPG extends SequentialCommandGroup {
    public AutoShootPPG(Bot bot){
        addCommands(
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, bot.getAdjustedTransferPower() + .1),
                new ActivateIntake(bot, true),
                new WaitCommand(100),
                new SetTransferPower(bot, 0),
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, false),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(300),
                new SetTransferPower(bot, .2),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)
        );
        addRequirements(bot.shooter);
    }
}
