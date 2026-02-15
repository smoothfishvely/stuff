package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class ActivateShooter extends InstantCommand {
    private Bot bot;
    private double velocity;
    private boolean on;
    public ActivateShooter(Bot bot, double velocity){
        this.bot = bot;
        this.velocity = velocity;
    }

    public ActivateShooter(Bot bot){
        this.bot = bot;
        velocity = 0;
    }

    public void initialize(){
        bot.shooter.setVelocity(velocity);
    }
}