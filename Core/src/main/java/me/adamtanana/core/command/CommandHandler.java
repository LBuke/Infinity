package me.adamtanana.core.command;

import com.google.common.collect.Sets;
import me.adamtanana.core.Module;
import me.adamtanana.core.player.Rank;
import me.adamtanana.core.util.C;
import me.adamtanana.core.util.CommonUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CommandHandler extends Module{
    private static Set<Command> cmds = Sets.newHashSet();

    public static void register(Command command) {
        cmds.add(command);
    }

    public static void unregister(Command command) {
        cmds.remove(command);
    }

    @EventHandler (priority = EventPriority.HIGH)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        List<String> args = new ArrayList<>();
        for(String s : event.getMessage().split(Pattern.quote(" ")))
            args.add(s);

        String commandName = args.remove(0).replaceAll(Pattern.quote("/"), "");

        cmds.stream().filter(cmd -> {
            if(cmd.getName().equalsIgnoreCase(commandName)) return true;

            for(String al : cmd.getAliases()) {
                if(al.equalsIgnoreCase(commandName)) return true;
            }

            return false;
        }).findFirst().ifPresent(cmd -> {
            event.setCancelled(true); //Remove "Command not found msg"
            Rank rank = CommonUtil.getGlobalPlayer(event.getPlayer().getUniqueId()).getRank();
            if((rank.has(cmd.getRequiredRank()) || cmd.getWhitelist().contains(rank)) && !cmd.getBlacklist().contains(rank)) {
                if(args.size() >= cmd.getMinArgs()) {
                    cmd.execute(event.getPlayer(), args);
                }else {
                    event.getPlayer().sendMessage(C.red + "This command requires a minimum of " + C.yellow + cmd.getMinArgs() +
                    C.red + " arguments!");
                }
            } else {
               event.getPlayer().sendMessage(C.yellow + "You required rank " + cmd.getRequiredRank().getName(false, false, true)
                       + C.yellow + " to use this command!");
            }
        });





    }

}
