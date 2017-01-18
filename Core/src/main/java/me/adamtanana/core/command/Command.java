package me.adamtanana.core.command;

import lombok.Getter;
import me.adamtanana.core.player.Rank;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public abstract class Command {

    private @Getter String name;
    private @Getter List<String> aliases;
    private @Getter Rank requiredRank;
    private @Getter List<Rank> whitelist, blacklist;
    private @Getter int minArgs;

    public Command(String name, List<String> aliases, int minArgs, Rank requiredRank) {
        this(name, aliases, minArgs, requiredRank, new Rank[]{}, new Rank[]{});
    }

    public Command(String name, List<String> aliases, int minArgs, Rank requiredRank, Rank[] whitelist, Rank[] blacklist) {
        this.name = name;
        this.aliases = aliases;
        this.minArgs = minArgs;
        this.requiredRank = requiredRank;
        this.whitelist = Arrays.asList(whitelist);
        this.blacklist = Arrays.asList(blacklist);
    }

    public abstract void execute(Player player, List<String> args);

}
