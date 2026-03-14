package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

public class ReverseIntake extends SequentialCommandGroup {
    public ReverseIntake(Bot bot){
        addCommands(
                new SetIntakePower(bot, -1),
                new WaitCommand(100),
                new SetIntakePower(bot, 1)
        );
        addRequirements(bot.theIntake);
    }
}