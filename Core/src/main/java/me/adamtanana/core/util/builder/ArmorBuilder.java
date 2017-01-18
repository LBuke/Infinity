package me.adamtanana.core.util.builder;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ArmorBuilder
{
    private ItemStack itemStack;
    private LeatherArmorMeta itemMeta;

    public ArmorBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
    }

    public ArmorBuilder setColor(Color color)
    {
        itemMeta.setColor(color);
        return this;
    }

    public ItemStack build()
    {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
