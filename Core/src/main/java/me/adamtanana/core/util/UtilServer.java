package me.adamtanana.core.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.adamtanana.core.Core;
import me.adamtanana.core.event.UpdateEvent;
import me.adamtanana.core.event.UpdateType;
import me.adamtanana.core.redis.MessageListener;
import me.adamtanana.core.redis.message.PlayerMessage;
import me.adamtanana.core.redis.message.PlayerOnlineMessage;
import net.minecraft.server.v1_8_R3.Packet;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class UtilServer {
    @Getter
    private static GetPlayerHandler handler = new GetPlayerHandler();


    public static String getServerName() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void log(Level level, String message) {
        Bukkit.getLogger().log(level, "[" + getServerName() + "] " + message);
    }

    public static BukkitTask runTaskTimer(Runnable run, long time) {
        return Bukkit.getScheduler().runTaskTimer(getPlugin(), run, 0, time);
    }

    public static BukkitTask runTaskLater(Runnable run, long time) {
        return Bukkit.getScheduler().runTaskLater(getPlugin(), run, time);
    }

    public static JavaPlugin getPlugin() {
        return (JavaPlugin) Bukkit.getPluginManager().getPlugins()[0];
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Bukkit.getPluginManager().getPlugins()[0]);
    }

    public static BukkitTask runTaskAsync(Runnable run) {
        return Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), run);
    }

    public static BukkitTask runTask(Runnable run) {
        return Bukkit.getScheduler().runTask(getPlugin(), run);
    }

    public static BukkitTask runTaskAsyncLater(Runnable run, long time) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(getPlugin(), run, time);
    }

    public static void sendPacket(Player player, Packet... packets) {
        for (Packet packet : packets) ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void broadcastPacket(Packet... packets) {
        Bukkit.getOnlinePlayers().forEach(p -> sendPacket(p, packets));
    }

    public static void broadcastPacket(List<Player> players, Packet... packets) {
        players.forEach(p -> sendPacket(p, packets));
    }

    public static void isOnline(String name, Callback<PlayerOnlineMessage> callback) {
        PlayerOnlineMessage msg = new PlayerOnlineMessage(name);
        if(Bukkit.getPlayer(name) != null) {
            msg.setOnline(true);
            callback.call(msg);
        } else getHandler().add(msg, callback);
    }

    public static void message(String player, String message) {
        if(Bukkit.getPlayer(player) != null) {
            Bukkit.getPlayer(player).sendMessage(message);
        } else {
            PlayerMessage msg = new PlayerMessage(player, message);
            Core.getJedis().sendMessage(msg, "all");
        }
    }

    public static class GetPlayerHandler implements MessageListener<PlayerOnlineMessage>, Listener{
        private HashMap<UUID, HashMap<Callback, Long>> callbacks = Maps.newHashMap();

        @Override
        public void onReceive(String sender, PlayerOnlineMessage msg) {
            if(callbacks.get(msg.getMessageId()) != null) {
                callbacks.remove(msg.getMessageId()).keySet().stream().findFirst().get().call(msg);
            } else if(Bukkit.getPlayer(msg.getPlayerName()) != null) {
                msg.setOnline(true);
                Core.getJedis().sendMessage(msg, sender);
            }
        }

        public void add(PlayerOnlineMessage msg, Callback<PlayerOnlineMessage> callback) {
            HashMap<Callback, Long> map = new HashMap<>();
            map.put(callback, System.currentTimeMillis());

            callbacks.put(msg.getMessageId(), map);
            Core.getJedis().sendMessage(msg, "all");
        }

        @EventHandler
        public void update(UpdateEvent event) {
            if(event.getType() != UpdateType.SEC) return;

            List<PlayerOnlineMessage> toRemove = Lists.newArrayList();

            for(UUID id  : callbacks.keySet()) {
                HashMap<Callback, Long> map = callbacks.get(id);
                if(UtilTime.elapsed(map.values().stream().findFirst().get(), 500)) {
                    toRemove.add(new PlayerOnlineMessage("null", false, id));
                }
            }
            toRemove.forEach(to -> onReceive("", to));
        }


    }
}