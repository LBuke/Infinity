package me.adamtanana.core.inventory.item;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class MenuItem {

    private final ItemStack itemStack;
    private final int index;
    private final boolean cancelOnClick;

    public MenuItem(int index, ItemStack itemStack, boolean cancelOnClick) {
        this.index = index;
        this.itemStack = itemStack;
        this.cancelOnClick = cancelOnClick;
    }

    public MenuItem(int index, ItemStack itemStack) {
        this(index, itemStack, true);
    }
}
