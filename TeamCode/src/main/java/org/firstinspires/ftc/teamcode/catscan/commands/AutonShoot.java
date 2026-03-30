package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutonShoot extends SequentialCommandGroup {
    public AutonShoot(Bot bot){
        addCommands(
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, true),
                new ActivateIntake(bot, true),
                new WaitCommand(75),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                new WaitCommand(500),
                new SetTransferPower(bot, .2),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)

        );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}