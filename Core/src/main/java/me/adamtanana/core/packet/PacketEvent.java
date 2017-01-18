package me.adamtanana.core.packet;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PacketEvent extends Event{

    private static final HandlerList handlers = new HandlerList();

    @Getter
    private PacketType packetType;

    @Getter
    @Setter
    private boolean cancelled = false;

    @Getter
    private Packet packet;

    @Getter
    private Player player;

    public PacketEvent(Player player, PacketType packetType, Packet packet) {
        this.packetType = packetType;
        this.packet = packet;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }



    public static enum PacketType{
        INWARDS, OUTWARDS
    }
}
