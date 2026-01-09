package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionSDLeft extends InstantCommand {
    private Bot bot;
    public PositionSDLeft(Bot bot){
        this.bot = bot;
    }

    public void initialize(){
        bot.shooterDoors.shootLeft();
    }

}
