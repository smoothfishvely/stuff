package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootGPP extends SequentialCommandGroup {
    public AutoShootGPP(Bot bot){
        addCommands(
                //new ActivateKickerL(bot),//up
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerL(bot),//down
                        //new ActivateKickerR(bot)//up
                ),
                new WaitCommand(600),
                //new ActivateKickerR(bot),//down
                new WaitCommand(500),
                new ParallelCommandGroup(
                        //new ActivateKickerL(bot),//up (safety)
                        //new ActivateKickerR(bot)//up
                ),
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerL(bot),//down (safety)
                        //new ActivateKickerR(bot),//down
                        new ActivateShooter(bot)//off
                )
        );
        addRequirements(bot.shooter);
    }
}