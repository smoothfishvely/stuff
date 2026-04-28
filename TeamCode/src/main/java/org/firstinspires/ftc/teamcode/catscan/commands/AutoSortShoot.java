package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoSortShoot extends CommandBase {
    private Bot bot;
    public AutoSortShoot(Bot bot){
        this.bot = bot;
    }

    @Override
    public void initialize() {
        if(bot.getMotif() == 23){
            new SequentialCommandGroup(
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, true),
                    new TransferGoon(bot, true),
                    new WaitCommand(50),
                    new SetTransferPower(bot, 1),
                    new ActivateIntake(bot, true),
                    new WaitCommand(600),
                    new SetTransferPower(bot, 0),
                    new PositionSDLeft(bot, true),
                    new PositionSDRight(bot, false),
                    new WaitCommand(200),
                    new SetTransferPower(bot, 1),
                    new WaitCommand(300),
                    new SetTransferPower(bot, .3),
                    new TransferGoon(bot, false),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, false)
            ).schedule();
        } else if(bot.getMotif() == 22){
            new SequentialCommandGroup(
                    new ActivateIntake(bot, false),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, true),
                    new TransferGoon(bot, true),
                    new WaitCommand(50),
                    new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                    new WaitCommand(50),
                    new SetTransferPower(bot, 0),
                    new PositionSDLeft(bot, true),
                    new PositionSDRight(bot, false),
                    new WaitCommand(300),
                    new ActivateIntake(bot, true),
                    new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                    new WaitCommand(200),
                    new SetTransferPower(bot, 0),
                    new PositionSDLeft(bot, true),
                    new PositionSDRight(bot, true),
                    new WaitCommand(200),
                    new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                    new WaitCommand(200),
                    new TransferGoon(bot, false),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, false),
                    new SetTransferPower(bot, .3)
            ).schedule();
        } else {
            new SequentialCommandGroup(
                    new PositionSDLeft(bot, true),
                    new PositionSDRight(bot, false),
                    new TransferGoon(bot, true),
                    new WaitCommand(50),
                    new SetTransferPower(bot, 1),
                    new ActivateIntake(bot, true),
                    new WaitCommand(500),
                    new SetTransferPower(bot, 0),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, true),
                    new WaitCommand(300),
                    new SetTransferPower(bot, 1),
                    new WaitCommand(600),
                    new SetTransferPower(bot, .3),
                    new TransferGoon(bot, false),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, false)
            ).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        //if(bot.theTransfer.getPower() != .2){
            //return false;
        //}
        return true;
    }
}