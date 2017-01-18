package com.infinity.hub.cosmetic.gadget;

import com.infinity.hub.cosmetic.gadget.command.GiveGadgetCommand;
import com.infinity.hub.cosmetic.gadget.event.GadgetEvent;
import me.adamtanana.core.Module;
import me.adamtanana.core.command.CommandHandler;
import me.adamtanana.core.packet.PacketEvent;
import me.adamtanana.core.util.*;
import me.adamtanana.core.util.builder.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.UUID;

public class GadgetManager extends Module {

    private HashMap<UUID, Gadget> activeGadgets;
    private final int slot = 4;

    private boolean enabled = false;

    @Override
    public void enable() {
        CommandHandler.register(new GiveGadgetCommand());
        activeGadgets = new HashMap<>();
    }

    @Override
    public void disable() {
        if (activeGadgets == null) return;
        activeGadgets.clear();
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * This value will determine weather the gadgets will trigger
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Removes a player from the active gadgets list
     * When removed, this player will no longer be listened upon.
     * <p>
     * Removes the gadget item from slot 4
     *
     * @param uuid
     */
    public void removeGadget(UUID uuid) {
        if (!activeGadgets.containsKey(uuid)) return;
        Player player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline()) activeGadgets.remove(uuid);

        player.getInventory().setItem(4, new ItemBuilder(Material.AIR).build());
        player.updateInventory();

        activeGadgets.remove(uuid);
    }

    public void setGadget(UUID uuid, GadgetType gadgetType) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null || !player.isOnline()) return;
        Gadget gadget = null;
        try {
            gadget = gadgetType.getGadget().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (gadget == null) return;

        activeGadgets.put(uuid, gadget);

        player.getInventory().setItem(slot, new ItemBuilder(gadget.getDisplayMaterial()).setName(gadget.getDisplayName()).build());
        player.updateInventory();
    }

    @EventHandler
    public void onGadgetUse(GadgetEvent event) {
        if (!enabled) {
            event.setCancelled(true);
            return;

        }

        Player player = event.getPlayer();
        Gadget gadget = event.getGadget();

        String name = "";
        for (GadgetType gadgetType : GadgetType.values()) {
            if(gadgetType.getGadget().isInstance(gadget)) {
                name = gadgetType.name();
                break;
            }
        }
        if(name.isEmpty()) return;


        //If has no ammo, STOP and send message.
        if (gadget.getAmmo(player) <= 0) {
            event.getPlayer().sendMessage(C.red + "You do not have any " + C.yellow + gadget.getDisplayName() + C.red + "'s left!");
//            UtilText.sendActionBar(player, C.black + "[" + C.yellow + "0" + C.black + "] " + C.gray + "ammo");
            return;
        }

        if (!UtilTime.useAbility(player, gadget.getDisplayName(), gadget.getCooldown(), true)) return;

        UtilText.sendActionBar(player, C.black + "[" + C.yellow + (gadget.getAmmo(player)-1) + C.black + "] " + C.gray + "ammo");
        gadget.reduceAmmo(player, 1);

        gadget.run(player);
    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getHeldItemSlot() != 4) return;
//        if (event.getHand() != EquipmentSlot.HAND) return; 1.9+
        if (!activeGadgets.containsKey(event.getPlayer().getUniqueId())) return;
        Player player = event.getPlayer();
        Gadget gadget = activeGadgets.get(player.getUniqueId());

        //If Trigger type is not LEFT or RIGHT click
        if (gadget.getTriggerType() != GadgetTriggerType.LEFT_CLICK && gadget.getTriggerType() != GadgetTriggerType.RIGHT_CLICK)
            return;

        //If Action is not LEFT or RIGHT click
        if (event.getAction() == Action.PHYSICAL) return;

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (gadget.getTriggerType() == GadgetTriggerType.RIGHT_CLICK) {
                gadget.call(player);
            }
        } else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (gadget.getTriggerType() == GadgetTriggerType.LEFT_CLICK) {
                gadget.call(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(!activeGadgets.containsKey(event.getPlayer().getUniqueId())) return;
        removeGadget(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void testCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equalsIgnoreCase("/gadget")) {
            setGadget(event.getPlayer().getUniqueId(), GadgetType.ICEGUN);
            event.setCancelled(true);
        }
    }
}
