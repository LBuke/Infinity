package me.adamtanana.core.player;

import me.adamtanana.core.command.Command;
import me.adamtanana.core.util.C;
import me.adamtanana.core.util.UtilServer;
import me.adamtanana.core.util.UtilText;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MsgCommand extends Command{

    public MsgCommand() {
        super("msg", Arrays.asList("m", "tell"), 2, Rank.MEMBER);
    }

    @Override
    public void execute(Player player, List<String> args) {
        UtilServer.isOnline(args.get(0), (msg) -> {
            String ply = args.remove(0);
            if(msg.isOnline()) {
                UtilServer.message(player.getName(), C.gold + player.getName() + C.bold + "> " + ply + ": " + C.gray + C.bold + UtilText.join(args, ' '));
                UtilServer.message(ply, C.gold + player.getName() + C.bold + "> You: " + C.gray + C.bold  + UtilText.join(args, ' '));
            }
            else player.sendMessage(C.yellow + ply + C.red + " is not online!");
        });
    }
}
