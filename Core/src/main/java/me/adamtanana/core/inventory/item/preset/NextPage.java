package me.adamtanana.core.inventory.item.preset;

import me.adamtanana.core.inventory.item.ClickableItem;
import me.adamtanana.core.util.builder.ItemBuilder;
import org.bukkit.Material;

public class NextPage extends ClickableItem {

    public NextPage(int index) {
        super(index, new ItemBuilder(Material.ARROW).setName("Next Page").build(), true);
    }
}
