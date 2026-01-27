package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionSDLeft extends InstantCommand {
    public PositionSDLeft(Bot bot, boolean open){
        bot.shooterDoors.shootLeft(open);
    }
}
