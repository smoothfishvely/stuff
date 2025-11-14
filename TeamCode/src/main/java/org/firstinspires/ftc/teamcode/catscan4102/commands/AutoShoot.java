package org.firstinspires.ftc.teamcode.catscan4102.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan4102.subsystems.Bot;

public class AutoShoot extends SequentialCommandGroup {
    private Bot bot;
    public AutoShoot(Bot bot){
        addCommands(
                new ActivateKickerL(bot),//up
                new WaitCommand(700),
                new ParallelCommandGroup(
                        new ActivateKickerL(bot),//down
                        new ActivateKickerR(bot)//up
                ),
                new WaitCommand(200),
                new ActivateKickerR(bot),//down
                new WaitCommand(700),
                new ParallelCommandGroup(
                        new ActivateKickerL(bot),//up
                        new ActivateKickerR(bot)//up
                ),
                new WaitCommand(400),
                new ParallelCommandGroup(
                        new ActivateKickerL(bot),//down
                        new ActivateKickerR(bot),//down
                        new ActivateShooter(bot)//off
                ),
                new WaitCommand(100)
        );
        addRequirements(bot.shooter, bot.kickerLeft, bot.kickerRight);
    }
}