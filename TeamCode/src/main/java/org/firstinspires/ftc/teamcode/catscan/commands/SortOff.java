package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SortOff extends InstantCommand {
    private Bot bot;
    public SortOff(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.sortOff();
    }
}
