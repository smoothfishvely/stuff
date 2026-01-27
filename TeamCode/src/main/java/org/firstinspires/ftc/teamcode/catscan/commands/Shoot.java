package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class Shoot extends SequentialCommandGroup {
    public Shoot(Bot bot){
        addCommands(
                new WaitCommand(500),
                new ActivateTransfer(bot),//on
                new WaitCommand(2250),
                new ActivateTransfer(bot)//off
       );
        addRequirements(bot.shooter, bot.shooterDoors, bot.theTransfer);
    }
}
