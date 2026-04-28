package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class TransferGoon extends InstantCommand {
    private Bot bot;
    private boolean diddy;
    public TransferGoon(Bot bot, boolean diddy){
        this.bot = bot;
        this.diddy = diddy;
    }

    public void initialize(){

    }
}