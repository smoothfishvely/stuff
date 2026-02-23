package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class SetTransferPower extends InstantCommand {

    private Bot bot;
    private double power;

    public SetTransferPower(Bot bot, double power) {
        this.bot = bot;
        this.power = power;
    }

    public void initialize() {bot.theTransfer.setPower(power);}

}
