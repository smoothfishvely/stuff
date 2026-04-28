package org.firstinspires.ftc.teamcode.catscan.commands;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SelectCommand;

import org.firstinspires.ftc.teamcode.catscan.subsystems.Bot;

import java.util.HashMap;
public class otherAutonSortShoot extends CommandBase {
    private Bot bot;

    public otherAutonSortShoot(Bot bot) {
        this.bot = bot;
    }
    @Override
    public void initialize() {
        Command selected = new SelectCommand(
                new HashMap<Object, Command>() {{
                    put(21, new AutoShootGPP(bot));
                    put(22, new AutoShootPGP(bot));
                    put(23, new AutoShootPPG(bot));
                }},
                bot::getMotif
        );

        selected.schedule();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
