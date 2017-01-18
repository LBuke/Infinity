package me.adamtanana.core.inventory.example;

import me.adamtanana.core.inventory.InfinityMenu;
import me.adamtanana.core.inventory.item.DummyItem;
import me.adamtanana.core.inventory.item.preset.BackPage;
import me.adamtanana.core.inventory.item.preset.NextPage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopInventory extends InfinityMenu {

    public ShopInventory() {
        this(1);
    }

    public ShopInventory(int page) {
        super(6, "Test Shop (Page " + page + ")", true, true, false);

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 137; i++) {
            items.add(new ItemStack(Material.STONE));
        }

        int perPage = 25, pages = 1, x = items.size(), z = 0;
        if (x / perPage > 0) pages = x / perPage;
        if (x % perPage > 0) pages += 1;
        if (page > pages) return;
        System.out.println((page * perPage) - perPage + " - " + page * perPage);
        for (int i = ((page * perPage) - perPage); i < (page * perPage); i++) {
            if (i >= x) break;
            addItem(new DummyItem(z, items.get(i), true));
            z += 1;
        }

        if (page < pages) {
            int slot = ((getRows() - 1) * 9) + 9 - 1;
            addItem(new NextPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new ShopInventory(page + 1).openInventory(player);
                }
            });
        }

        if (page > 1) {
            int slot = ((getRows() - 1) * 9);
            addItem(new BackPage(slot) {
                @Override
                public void click(Player player, ClickType clickType) {
                    super.click(player, clickType);
                    new ShopInventory(page - 1).openInventory(player);
                }
            });
        }
    }
}
