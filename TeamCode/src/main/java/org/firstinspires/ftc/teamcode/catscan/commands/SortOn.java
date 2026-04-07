package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SortOn extends InstantCommand {
    private Bot bot;

    public SortOn(Bot bot) {
        bot.sortOn();
    }
}
