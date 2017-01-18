package me.adamtanana.core.player;

import me.adamtanana.core.Module;
import me.adamtanana.core.packet.PacketEvent;
import me.adamtanana.core.util.CommonUtil;
import me.adamtanana.core.util.UtilServer;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerHandler extends Module {
    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        UUID id = event.getUniqueId();
        GlobalPlayer globalPlayer = CommonUtil.getGlobalPlayer(id);
        globalPlayer.getBanEntries().forEach(ban -> {
            if (ban.isKick() || ban.expired()) {
                globalPlayer.getBanEntries().remove(ban);
                globalPlayer.getOldViolations().addBan(ban);
            } else {
                event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                event.setKickMessage(ban.getBanReason());
            }
        });
        globalPlayer.save();
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CommonUtil.invalidateGlobalPlayer(event.getPlayer().getName());
        CommonUtil.invalidateGlobalPlayer(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void playerLocale(PacketEvent event) {
        if(event.getPacket() instanceof PacketPlayInSettings) {
            PacketPlayInSettings settings = (PacketPlayInSettings) event.getPacket();
            CommonUtil.getGlobalPlayer(event.getPlayer().getUniqueId()).setLocale(settings.a());
        }
    }
}
