package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionSDRight extends InstantCommand {
    private Bot bot;
    private boolean open;
    public PositionSDRight(Bot bot, boolean open){
        this.bot = bot;
        this.open = open;
    }

    public void initialize(){
        bot.shooterDoors.shootRight(open);
    }
}
