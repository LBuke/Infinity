package me.adamtanana.core.punish;

import me.adamtanana.core.command.Command;
import me.adamtanana.core.player.Rank;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PunishCommand extends Command{
    public PunishCommand() {
        super("p", Arrays.asList("punish", "ban"), 2, Rank.HELPER);
    }

    @Override
    public void execute(Player player, List<String> args) {
        Punish.openGui(player, args);
    }
}
