package me.adamtanana.core.engine.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerStateChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private Player player;
    @Getter private PlayerState from, to;

    public PlayerStateChangeEvent(Player player, PlayerState from, PlayerState to) {
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
