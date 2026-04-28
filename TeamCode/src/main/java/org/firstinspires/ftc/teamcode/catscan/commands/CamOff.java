package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class CamOff extends InstantCommand {
    private Bot bot;
    public CamOff(Bot bot){
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.camOff();
    }
}
