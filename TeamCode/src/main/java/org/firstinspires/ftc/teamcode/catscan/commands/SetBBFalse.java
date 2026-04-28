package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SetBBFalse extends InstantCommand {
    private Bot bot;

    public SetBBFalse(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void initialize() {
        bot.beamBreaks.setFalse();
    }
}
