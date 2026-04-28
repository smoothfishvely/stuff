package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class PositionDoors extends InstantCommand {
    private Bot bot;
    private boolean leftUp, rightUp;
    public PositionDoors(Bot bot, boolean leftUp, boolean rightUp){
        this.bot = bot;
        this.leftUp = leftUp;
        this.rightUp = rightUp;
    }

    public void initialize(){
        bot.doors.open(leftUp, rightUp);
    }
}
