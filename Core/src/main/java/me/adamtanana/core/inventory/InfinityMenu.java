package me.adamtanana.core.inventory;

import lombok.Getter;
import me.adamtanana.core.inventory.item.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class InfinityMenu implements InventoryHolder {

    @Getter private List<MenuItem> items;

    private Inventory inventory;

    @Getter private int rows = 0;
    @Getter private String title;
    @Getter private final boolean resetCursor, exitNullClick, openParentOnExit;

    public InfinityMenu(int rows, String title, boolean resetCursor, boolean exitNullClick, boolean openParentOnExit) {
        if (rows > 6 || rows < 1) {
            if (rows > 6) this.rows = 6;
            if (rows < 1) this.rows = 1;
        }

        if (this.rows == 0) this.rows = rows;
        this.title = title;
        this.resetCursor = resetCursor;
        this.exitNullClick = exitNullClick;
        this.openParentOnExit = openParentOnExit;
        this.inventory = Bukkit.createInventory(this, rows * 9, title);

        items = new ArrayList<>();
    }

    public InfinityMenu(int rows, String title) {
        this(rows, title, false, false, false);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(MenuItem item) {
        inventory.setItem(item.getIndex(), item.getItemStack());
        items.add(item);
    }

    public boolean containsItem(ItemStack itemStack) {
        boolean result = false;
        for (MenuItem item : items) {
            if (!item.getItemStack().equals(itemStack))
                continue;

            result = true;
            break;
        }

        return result;
    }

    public MenuItem getInventoryItem(ItemStack itemStack) {
        for (MenuItem item : items) {
            if (!item.getItemStack().equals(itemStack))
                continue;

            return item;
        }

        return null;
    }

    public MenuItem getInventoryItem(int index) {
        for (MenuItem item : items) {
            if (item.getIndex() != index)
                continue;

            return item;
        }

        return null;
    }

    public void openInventory(Player player) {
        if (resetCursor) {
            player.closeInventory();
        }

        player.openInventory(inventory);
    }
}
