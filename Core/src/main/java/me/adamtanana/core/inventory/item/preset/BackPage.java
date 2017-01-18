package me.adamtanana.core.inventory.item.preset;

import me.adamtanana.core.inventory.item.ClickableItem;
import me.adamtanana.core.util.builder.ItemBuilder;
import org.bukkit.Material;

public class BackPage extends ClickableItem {

    public BackPage(int index) {
        super(index, new ItemBuilder(Material.ARROW).setName("Back Page").build(), true);
    }
}
