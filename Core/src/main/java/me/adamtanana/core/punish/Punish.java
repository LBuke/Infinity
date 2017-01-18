package me.adamtanana.core.punish;

import me.adamtanana.core.util.C;
import me.adamtanana.core.util.UtilText;
import me.adamtanana.core.util.builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class Punish {
    public static void openGui(Player player, List<String> args) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, "Punish Inventory");
        String victim = args.remove(0);

        inv.setItem(4, new ItemBuilder(Material.SKULL_ITEM).setOwner(victim).setName(C.red + victim).setLore(C.blue + UtilText.join(args, ' ')).build());



    }
}
