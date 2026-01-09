package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootPGP extends SequentialCommandGroup {
    public AutoShootPGP(Bot bot){
        addCommands(
                //new ActivateKickerR(bot),//up
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerR(bot),//down
                        //new ActivateKickerL(bot)//up
                ),
                new WaitCommand(600),
                //new ActivateKickerL(bot),//down
                new WaitCommand(500),
                new ParallelCommandGroup(
                        //new ActivateKickerR(bot),//up
                        //new ActivateKickerL(bot)//up (safety)
                ),
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerR(bot),//down
                        //new ActivateKickerL(bot),//down (safety)
                        new ActivateShooter(bot)//off
                )
        );
        addRequirements(bot.shooter);
    }
}
