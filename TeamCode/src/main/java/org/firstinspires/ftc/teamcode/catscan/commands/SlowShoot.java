package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SlowShoot extends SequentialCommandGroup {
    public SlowShoot(Bot bot){
        addCommands(
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, bot.getAdjustedTransferPower() + .15),
                new ActivateIntake(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, .2),
                new WaitCommand(150),
                new SetTransferPower(bot, bot.getAdjustedTransferPower() + .15),
                new WaitCommand(80),
                new SetTransferPower(bot, -.01),
                new WaitCommand(120),
                new SetTransferPower(bot, bot.getAdjustedTransferPower()+ .15),
                new WaitCommand(300),
                new SetTransferPower(bot, .2),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)

        );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}