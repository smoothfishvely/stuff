package org.firstinspires.ftc.teamcode.catscan.commands;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class GetMotif extends CommandBase {
    private Bot bot;
    private ElapsedTime time;
    public GetMotif(Bot bot){
        this.bot = bot;
        time = new ElapsedTime();
    }

    public void initialize(){
        time.reset();
    }

    public void execute(){
        bot.setMotif(bot.ll.getMotif());
    }

    @Override
    public boolean isFinished() {
        if(bot.getMotif() != 0){
            return true;
        }
        return false;
    }
}
