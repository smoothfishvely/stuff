package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootGPP extends SequentialCommandGroup {
    public AutoShootGPP(Bot bot){
        addCommands(
                new PositionSDLeft(bot, true),
                new PositionSDRight(bot, false),
                new TransferGoon(bot, true),
                new WaitCommand(50),
                new SetTransferPower(bot, 1),
                new ActivateIntake(bot, true),
                new WaitCommand(800),
                new SetTransferPower(bot, 0),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, true),
                new WaitCommand(400),
                new SetTransferPower(bot, 1),
                new WaitCommand(1000),
                new SetTransferPower(bot, .5),
                new TransferGoon(bot, false),
                new PositionSDLeft(bot, false),
                new PositionSDRight(bot, false)
        );
        addRequirements(bot.shooter, bot.theIntake, bot.theTransfer, bot.doors, bot.shooterDoors);
    }
}