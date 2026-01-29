package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

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
            new AutoShootGPP(bot).schedule();
        }
    }

    @Override
    public boolean isFinished() {
        //if(bot.shooter.getOn()){
            return false;
        //}
        //return true;
    }
}
