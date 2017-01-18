package me.adamtanana.core.inventory;

import me.adamtanana.core.Module;
import me.adamtanana.core.inventory.example.ShopInventory;
import me.adamtanana.core.inventory.item.ClickableItem;
import me.adamtanana.core.inventory.item.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

public class MenuManager extends Module {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getInventory().getHolder() == null) return;
        if (!(event.getInventory().getHolder() instanceof InfinityMenu)) return;

        InfinityMenu goInv = (InfinityMenu) event.getInventory().getHolder();
        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();

        //Is the clicked location outside of the Inventory bounding
        if (clicked == null) {
            if (!goInv.isExitNullClick()) {
                player.closeInventory();
            }

            //TODO: Open parent inventory if not null
            return;
        }

        //If the ItemStack cannot be recognized as 'MenuItem'
        if (!goInv.containsItem(clicked)) {
            event.setCancelled(true);
            return;
        }

        MenuItem goItem = goInv.getInventoryItem(event.getRawSlot());
        if (goItem == null) return;

        if (goItem.isCancelOnClick()) {
            event.setCancelled(true);
        }

        if (goItem instanceof ClickableItem) {
            ((ClickableItem) goItem).click(player, event.getClick());
        }
    }

    /**
     * TODO
     * REMOVE ME ONCE TESTED & WORKING.
     */
    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        if (!event.isSneaking()) return;

        new ShopInventory().openInventory(event.getPlayer());
    }
}
