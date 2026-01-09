package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class AutoShootPPG extends SequentialCommandGroup {
    public AutoShootPPG(Bot bot){
        addCommands(
                //new ActivateKickerR(bot),//up
                new WaitCommand(600),
                //new ActivateKickerR(bot),//down
                new WaitCommand(500),
                //new ActivateKickerR(bot),//up
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerL(bot),//up
                        //new ActivateKickerR(bot)//down
                ),
                new WaitCommand(600),
                new ParallelCommandGroup(
                        //new ActivateKickerL(bot),//down
                        new ActivateShooter(bot)//off
                ),
                new WaitCommand(100)
        );
        addRequirements(bot.shooter);
    }
}
