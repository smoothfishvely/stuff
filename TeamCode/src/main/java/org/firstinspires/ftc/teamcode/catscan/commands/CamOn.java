package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class CamOn extends InstantCommand {
    private Bot bot;
    public CamOn(Bot bot){
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.camOn();
    }
}
