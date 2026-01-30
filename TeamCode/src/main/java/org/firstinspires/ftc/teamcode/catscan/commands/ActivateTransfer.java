package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class ActivateTransfer extends InstantCommand {
    private Bot bot;
    private double power;
    public ActivateTransfer(Bot bot){
        this.bot = bot;
    }

    public void initialize(){
        bot.theTransfer.setOn();
    }
}
