package me.adamtanana.core.engine.team;

import lombok.Getter;
import me.adamtanana.core.engine.player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChangeTeamEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private Player player;
    @Getter private Team from, to;

    public PlayerChangeTeamEvent(Player player, Team from, Team to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
