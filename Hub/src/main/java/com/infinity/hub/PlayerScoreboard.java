package com.infinity.hub;

import com.google.common.collect.Maps;
import me.adamtanana.core.Module;
import me.adamtanana.core.event.UpdateEvent;
import me.adamtanana.core.event.UpdateType;
import me.adamtanana.core.player.Rank;
import me.adamtanana.core.scoreboard.Scroller;
import me.adamtanana.core.scoreboard.Sidebar;
import me.adamtanana.core.util.C;
import me.adamtanana.core.util.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.UUID;

public class PlayerScoreboard extends Module {
    private Map<UUID, Sidebar> players = Maps.newHashMap();
    private Scroller title;

    @Override
    public void enable() {
        title = new Scroller("Welcome to Infinity", 20, 5);
    }

    @Override
    public void disable() {
        players.clear();
    }

    @EventHandler
    public void addPlayer(PlayerJoinEvent event) {
        players.put(event.getPlayer().getUniqueId(), new Sidebar(" "));
        players.get(event.getPlayer().getUniqueId()).showTo(event.getPlayer());
    }

    @EventHandler
    public void leavePlayer(PlayerQuitEvent event) {
        players.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void update(UpdateEvent event) {
        if (event.getType() != UpdateType.FAST) return;
        final String next = title.next();

        players.keySet().forEach(p -> {
            players.get(p).clearEntries();
            players.get(p).setTitle(next);
            String rank = CommonUtil.getGlobalPlayer(p).getRank().getName(true, true, true);
            players.get(p).addEntry(
                    C.gold + C.bold + "Players",
                    Bukkit.getOnlinePlayers().size() + "",
                    " ",
                    C.red + C.bold + "Rank",
                    rank.equals(Rank.MEMBER.getName(true, true, true)) ? "Default" : rank);
            players.get(p).update();
        });
    }
}
