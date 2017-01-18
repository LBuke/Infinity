package me.adamtanana.core;

import com.google.common.collect.Lists;
import lombok.Getter;
import me.adamtanana.core.command.CommandHandler;
import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.event.Updater;
import me.adamtanana.core.inventory.MenuManager;
import me.adamtanana.core.packet.PacketModule;
import me.adamtanana.core.player.GlobalPlayer;
import me.adamtanana.core.player.MsgCommand;
import me.adamtanana.core.player.PlayerHandler;
import me.adamtanana.core.redis.JedisServer;
import me.adamtanana.core.redis.message.CommandMessage;
import me.adamtanana.core.redis.message.PlayerMessage;
import me.adamtanana.core.redis.message.PlayerOnlineMessage;
import me.adamtanana.core.util.CommonUtil;
import me.adamtanana.core.util.UtilServer;
import me.adamtanana.core.util.UtilWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class Core extends JavaPlugin implements Listener {
    @Getter
    private List<Module> modules = Lists.newArrayList();

    @Getter
    private static JedisServer jedis;

    public final void onEnable() {
        new DatabaseEngine("127.0.0.1", 27017);


        jedis = new JedisServer(UtilServer.getServerName());

        JedisServer.registerListener(PlayerMessage.class, (sender, message) -> {
            Player receive = Bukkit.getPlayer(message.getPlayer());
            if(receive != null) receive.sendMessage(message.getMessage());
        });

        JedisServer.registerListener(CommandMessage.class, (sender, message) -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), message.getCommand());
        });

        jedis.registerListener(PlayerOnlineMessage.class, UtilServer.getHandler());

        UtilWorld.init();
        UtilServer.registerListener(UtilServer.getHandler());
        CommandHandler.register(new MsgCommand());
        UtilServer.registerListener(this);

        enableModules(new Updater(), new PlayerHandler(), new CommandHandler(), new MenuManager(), new PacketModule());
        enable();
    }

    protected void enable() {}

    protected void disable(){}

    public void onDisable() {
        disable();
        modules.forEach(Module::disable);

    }

    public void enableModules(Module... mdls) {
        for (Module m : mdls) {
            Bukkit.getPluginManager().registerEvents(m, this);
            m.enable();
        }
        modules.addAll(Arrays.asList(mdls));
    }

    public <T extends Module> T getModule(Class<T> clazz) {
        Optional<Module> found = modules.stream().filter(c -> c.getClass().equals(clazz)).findFirst();
        if (found.isPresent()) return clazz.cast(found.get());
        return null;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GlobalPlayer globalPlayer = CommonUtil.getGlobalPlayer(e.getPlayer().getUniqueId());
        globalPlayer.setLastName(e.getPlayer().getName());
        globalPlayer.setLastServer(Bukkit.getServerName());

    }


}
