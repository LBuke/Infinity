package me.adamtanana.core.scoreboard;


import com.google.common.collect.Lists;
import me.adamtanana.core.util.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Arrays;
import java.util.List;

public class Sidebar {

    private static transient ScoreboardManager bukkitManager = Bukkit.getScoreboardManager();

    private List<String> entries;
    private Scoreboard bukkitScoreboard;
    private Objective bukkitObjective;
    private String title;

    public Sidebar(String title, String... entries) {

        bukkitScoreboard = bukkitManager.getNewScoreboard();

        bukkitObjective = bukkitScoreboard.registerNewObjective("obj", "dummy");

        this.entries = Lists.newArrayList();
        this.title = title;
        addEntry(entries);
        update();


    }

    public Sidebar setTitle(String title) {
        this.title = title;
        return this;
    }

    public Sidebar addEntry(String... scrollers) {
        entries.addAll(Arrays.asList(scrollers));
        return this;
    }

    public Sidebar showTo(Player player) {
        player.setScoreboard(bukkitScoreboard);
        return this;
    }

    public Sidebar hideFrom(Player player) {
        player.setScoreboard(bukkitManager.getMainScoreboard());
        return this;
    }

    public Sidebar update() {

        redo();

        for (int i = entries.size(); i > 0; i--) {
            String txt = entries.get(entries.size() - i);
            if (txt.length() > 40) txt = txt.substring(0, 40);


            bukkitObjective.getScore(txt).setScore(i);
        }
        return this;

    }

    private void redo() {

        bukkitObjective.unregister();
        bukkitObjective = bukkitScoreboard.registerNewObjective("obj", "dummy");

        String msg = title;
        msg = msg.length() == 0 ? " " : msg;
        bukkitObjective.setDisplayName(C.bold + msg);
        bukkitObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void clearEntries() {
        entries.clear();
    }
}
