package me.adamtanana.core.engine.team;

import lombok.Getter;
import me.adamtanana.core.engine.player.GamePlayer;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Team {

    private String name;
    private ChatColor color;
    private Set<GamePlayer> gamePlayers;

    public Team(String name, ChatColor color) {
        this.name = name;
        this.color = color;

        gamePlayers = new HashSet<>();
    }

    public Team() {
        this("default", ChatColor.WHITE);
    }

    public boolean hasPlayer(UUID uuid) {
        for(GamePlayer gamePlayer : gamePlayers) {
            if(gamePlayer.getUuid().equals(uuid)) return true;
        }
        return false;
    }

    public void removePlayer(UUID uuid) {
        GamePlayer found = null;
        for(GamePlayer gamePlayer : gamePlayers) {
            if(!gamePlayer.getUuid().equals(uuid)) continue;
            found = gamePlayer;
        }

        if(found == null) return;
        gamePlayers.remove(found);
    }

    public void addPlayer(GamePlayer gamePlayer) {
        gamePlayers.add(gamePlayer);
    }
}
