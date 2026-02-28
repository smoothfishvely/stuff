package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SetIntakePower extends InstantCommand {

    private Bot bot;
    private double power;

    public SetIntakePower(Bot bot, double power) {
        this.bot = bot;
        this.power = power;
    }

    public void initialize() {bot.theIntake.setPower(power);}

}
