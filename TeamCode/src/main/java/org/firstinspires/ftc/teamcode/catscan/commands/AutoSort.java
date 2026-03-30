package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoSort extends CommandBase {
    private Bot bot;
    public AutoSort(Bot bot){
        this.bot = bot;
    }

    @Override
    public void initialize() {
        if(bot.getMotif() == 23){
            new AutoShootPPG(bot).schedule();
        } else if(bot.getMotif() == 22){
            new AutoShootPGP(bot).schedule();
        } else {
            new SequentialCommandGroup(
                    new PositionSDLeft(bot, true),
                    new PositionSDRight(bot, false),
                    new WaitCommand(50),
                    new SetTransferPower(bot, bot.getAdjustedTransferPower() + .25),
                    new ActivateIntake(bot, true),
                    new WaitCommand(500),
                    new SetTransferPower(bot, 0),
                    new WaitCommand(300),
                    new PositionSDLeft(bot, false),
                    new PositionSDRight(bot, true),
                    new SetTransferPower(bot, bot.getAdjustedTransferPower()),
                    new WaitCommand(600),
                    new SetTransferPower(bot, .2),
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