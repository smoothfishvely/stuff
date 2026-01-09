package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionHood extends InstantCommand {
    private Bot bot;
    private double posL, posR;
    public PositionHood(Bot bot, double posL, double posR){
        this.bot = bot;
        this.posL = posL;
    }

    public void initialize(){
        bot.hood.setPos(posL, posR);
    }

}
