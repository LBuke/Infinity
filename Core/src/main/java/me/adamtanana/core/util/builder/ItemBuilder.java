package me.adamtanana.core.util.builder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, byte data) {
        this.itemStack = new ItemStack(material, 1, data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, (byte) 0);
    }

    public ItemBuilder setAmount(int amount)
    {
        if(amount > 64) amount = 64;
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name)
    {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(List<String> lore)
    {
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(String... lore)
    {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setOwner(String name) {
        if(itemStack.getType().equals(Material.SKULL_ITEM)) {
            SkullMeta meta = (SkullMeta) itemMeta;
            meta.setOwner(name);
        }
        return this;
    }


    public ItemStack build()
    {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemBuilder setData(byte data) {
        itemStack.setDurability(data);
        return this;
    }
}
