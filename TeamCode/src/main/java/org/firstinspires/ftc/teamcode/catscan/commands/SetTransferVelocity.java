package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SetTransferVelocity extends InstantCommand {
    private Bot bot;
    private double velocity;
    private boolean on;
    public SetTransferVelocity(Bot bot, double velocity){
        this.bot = bot;
        this.velocity = velocity;
    }

    public void initialize(){
        //bot.theTransfer.setVelocity(velocity);
    }
}