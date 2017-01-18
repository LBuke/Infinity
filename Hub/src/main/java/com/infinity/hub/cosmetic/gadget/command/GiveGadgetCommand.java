package com.infinity.hub.cosmetic.gadget.command;

import com.infinity.hub.cosmetic.gadget.GadgetType;
import com.mojang.authlib.GameProfile;
import me.adamtanana.core.command.Command;
import me.adamtanana.core.player.Rank;
import me.adamtanana.core.util.C;
import me.adamtanana.core.util.CommonUtil;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GiveGadgetCommand extends Command {

    public GiveGadgetCommand() {
        super("giveGadget", Arrays.asList(), 3, Rank.ADMIN);
    }

    @Override
    public void execute(Player player, List<String> args) {
        String receivere = args.remove(0);
        int amount = 1;
        String amt = args.remove(0);
        try {
            amount = Integer.valueOf(amt);
        }catch (Exception e) {
            player.sendMessage(C.red + "Unknown number -> " + C.yellow + amt);
        }
        String value = args.remove(0);

        if(Bukkit.getPlayer(receivere) == null) {
            player.sendMessage(C.red + "Player not online!");
            return;
        }
        try {
            GadgetType.valueOf(value.toUpperCase());
        } catch (Exception e) {
            player.sendMessage(C.yellow + value + C.red + " is not a valid gadget!");
            return;
        }

        player.sendMessage(C.yellow + receivere + C.darkAqua + " has received " + amount +  "x " + C.yellow + value);
        Bukkit.getPlayer(receivere).sendMessage(C.yellow +"You received " + amount +  "x " + C.yellow + value);

        CommonUtil.getGlobalPlayer(Bukkit.getPlayer(receivere).getUniqueId()).setGadgetData(value.toUpperCase(),
                CommonUtil.getGlobalPlayer(Bukkit.getPlayer(receivere).getUniqueId()).getGadgetData(value.toUpperCase()) + amount);
    }
}
