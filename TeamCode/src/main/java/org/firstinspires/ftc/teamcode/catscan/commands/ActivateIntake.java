package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class ActivateIntake extends InstantCommand {
    private Bot bot;
    public ActivateIntake(Bot bot, boolean on){
        bot.theIntake.setOn(on);
    }


}
