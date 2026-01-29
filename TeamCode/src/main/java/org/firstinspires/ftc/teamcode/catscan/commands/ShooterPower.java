package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class ShooterPower extends InstantCommand {
    private Bot bot;
    private boolean increase;
    private double power;
    public ShooterPower(Bot bot, boolean increase){
        this.bot = bot;
        this.increase = increase;
        power = 0;
    }

    public ShooterPower(Bot bot, double power){
        this.bot = bot;
        increase = false;
        this.power = power;
    }

    public void initialize() {
        if (power == 0) {
            if (increase) {
                bot.shooter.add();
            } else {
                bot.shooter.subtract();
            }
        } else {
            bot.shooter.setVelocity(power);
        }
    }
}
