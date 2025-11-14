package org.firstinspires.ftc.teamcode.catscan4102.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan4102.subsystems.Bot;

public class ActivateShooter extends InstantCommand {
    private Bot bot;
    private double power;
    public ActivateShooter(Bot bot, double power){
        this.bot = bot;
        this.power = power;
    }

    public ActivateShooter(Bot bot){
        this.bot = bot;
        power = 0;
    }

    public void initialize(){
        if(power != 0) {
            bot.shooter.setPower(power);
        }
        bot.shooter.setOn();
    }
}