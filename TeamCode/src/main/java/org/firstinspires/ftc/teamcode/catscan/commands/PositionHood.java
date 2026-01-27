package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionHood extends InstantCommand {
    public PositionHood(Bot bot, double posL, double posR){
       bot.hood.setPos(posL, posR);
    }
}
