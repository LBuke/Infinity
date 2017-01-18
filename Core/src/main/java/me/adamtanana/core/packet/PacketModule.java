package me.adamtanana.core.packet;

import me.adamtanana.core.Module;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketModule extends Module {
    @Override
    public void enable() {
        Bukkit.getOnlinePlayers().forEach(PacketHandler::hook);
    }

    @Override
    public void disable(){
        Bukkit.getOnlinePlayers().forEach(PacketHandler::unHook);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        PacketHandler.hook(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        PacketHandler.unHook(event.getPlayer());
    }

}
