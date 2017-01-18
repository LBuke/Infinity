package com.infinity.hub.cosmetic.companions.companion;

import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.type.*;
import me.adamtanana.core.event.UpdateEvent;
import me.adamtanana.core.event.UpdateType;
import me.adamtanana.core.util.UtilServer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CompanionManager implements Listener {
    private HashMap<Player, Companion> companions;
    private List<Companion> statues;
    private JavaPlugin plugin;

    public CompanionManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        companions = new HashMap<>();
        statues = new ArrayList<>();
    }

    public Companion getPlayerCompanion(Player player) {
        return companions.get(player);
    }

    public void spawnCompanion(Player player, CompanionType type) {
        //If player already has a companion spawned.
        if (hasActiveCompanion(player)) companions.get(player).remove();

        //Spawn!
        Companion companion = null;
        switch (type) {
            case MC_8:
                companion = new BB8(player);
                break;
            case DUCK:
                companion = new Duck(player);
                break;
            case GORILLA:
                companion = new Gorilla(player);
                break;
            case CHIMP:
                companion = new Chimp(player);
                break;
            case PUG:
                companion = new Dog(player);
                break;
            case MINION:
                companion = new Minion(player);
                break;
            case TURTLE:
                companion = new Turtle(player);
                break;
        }

        companions.put(player, companion);
        companion.spawn(false);
    }

    //Statue, has no parent.
    public void spawnCompanionStatue(Location location, Location lookAt, CompanionType type) {
        Companion companion = null;
        switch (type) {
            case MC_8:
                companion = new BB8(null);
                break;
            case DUCK:
                companion = new Duck(null);
                break;
            case GORILLA:
                companion = new Gorilla(null);
                break;
            case CHIMP:
                companion = new Chimp(null);
                break;
            case PUG:
                companion = new Dog(null);
                break;
            case MINION:
                companion = new Minion(null);
                break;
            case TURTLE:
                companion = new Turtle(null);
                break;
        }

        companion.setSpawnLocation(lookAt(location, lookAt, true, false));
        companion.spawn(true);

        statues.add(companion);
    }

    private Location lookAt(Location loc, Location lookAt, boolean yaw, boolean pitch) {
        loc = loc.clone();
        double x = lookAt.getX() - loc.getX(), y = lookAt.getY() - loc.getY(), z = lookAt.getZ() - loc.getZ();
        if (yaw) {
            if (x != 0) {
                float f = 0;
                if (x < 0) f = (float) (1.5 * Math.PI);
                else f = (float) (0.5 * Math.PI);

                loc.setYaw(f - (float) Math.atan(z / x));
            } else if (z < 0) loc.setYaw((float) Math.PI);

            loc.setYaw(-loc.getYaw() * 180f / (float) Math.PI);
        }

        if (pitch) {
            double xz = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
            loc.setPitch((float) -Math.atan(y / xz));
            loc.setPitch(loc.getPitch() * 180f / (float) Math.PI);
        }

        return loc;
    }

    public boolean hasActiveCompanion(Player player) {
        return companions.containsKey(player);
    }

    public void removePlayerCompanion(Player player) {
        if (!hasActiveCompanion(player)) return;
        companions.get(player).remove();
        companions.remove(player);
    }

    public void removeAllStatues() {
        statues.forEach(statue -> statue.remove());
    }

    public void removeAllCompanions() {
        companions.values().forEach(Companion::remove);
        companions.clear();
    }

    public boolean isEntityCompanion(Entity entity) {
        for (Companion companion : companions.values()) {
            if (!companion.getHolder().equals(entity)) {
                for (CompanionPart part : companion.getCompanionParts()) {
                    if (part.getHolder().equals(entity)) return true;
                }
                continue;
            }

            return true;
        }

        return false;
    }

    @EventHandler
    public void movement(UpdateEvent event) {
        if (event.getType() != UpdateType.FASTEST)
            return;

        companions.keySet().forEach(player -> {
            Companion companion = companions.get(player);
            if (companion.isActive()) {
                Location last = companion.getLastLocation();
                Location loc = companion.getHolder().getLocation();
                if (last == null) {
                    companion.setLastLocation(loc);
                    return;
                }

                if ((loc.getX() == last.getX()) && (loc.getY() == last.getY()) && (loc.getZ() == last.getZ())) {
                    if (companion.isMoving())
                        companion.setMoving(false);
                } else {
                    if (!companion.isMoving())
                        companion.setMoving(true);
                }

                companion.setLastLocation(loc);
            }
        });
    }

    @EventHandler
    public void teleportCompanion(UpdateEvent event) {
        if (event.getType() != UpdateType.SEC)
            return;

        companions.keySet().forEach(player ->
        {
            Companion companion = companions.get(player);
            if (companion.isActive()) {
                if ((companion.getHolder().getLocation().distance(player.getLocation()) > 15)) {
                    companion.getHolder().teleport(player.getLocation());
                    return;
                }
            }
        });
    }

    @EventHandler
    public void onCompanionDamageByPlayer(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (((CraftEntity) entity).getHandle() instanceof CompanionEntityPart) {
                CompanionEntityPart entityPart = (CompanionEntityPart) ((CraftEntity) entity).getHandle();
                event.setCancelled(true);
                return;
            }
            for (Companion companion : companions.values()) {
                if (!companion.getHolder().equals(entity)) continue;
                break;
            }
        }
    }

    @EventHandler
    public void onCompanionDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (((CraftEntity) entity).getHandle() instanceof CompanionEntityPart) {
            event.setCancelled(true);

            return;
        }

        for (Companion companion : companions.values()) {
            if (!companion.getHolder().equals(entity)) continue;
            event.setCancelled(true);
            break;
        }

        for (Companion companion : statues) {
            if (!companion.getHolder().equals(entity)) continue;
            event.setCancelled(true);
            break;
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        for (Companion companion : companions.values()) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(companion.getHolder().getEntityId());
            UtilServer.sendPacket(event.getPlayer(), packet);
            spawnAndTeleportCompanionParts(companion.getCompanionParts(), event.getPlayer());
        }

        for (Companion statue : statues) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(statue.getHolder().getEntityId());
            UtilServer.sendPacket(event.getPlayer(), packet);
            spawnAndTeleportCompanionParts(statue.getCompanionParts(), event.getPlayer());
        }
    }

    private void spawnAndTeleportCompanionParts(Set<CompanionPart> parts, Player toSend) {
        for (CompanionPart part : parts) {
            CraftEntity entity = (CraftEntity) part.getHolder();
            PacketPlayOutSpawnEntity spawn = new PacketPlayOutSpawnEntity(entity.getHandle(), 30);
            PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport(entity.getHandle());
            UtilServer.sendPacket(toSend, spawn, tp);
        }
    }

    @EventHandler
    public void update(UpdateEvent event) {
        if (event.getType() != UpdateType.SEC)
            return;

        for (Player all : Bukkit.getOnlinePlayers()) {
            for (Companion companion : companions.values()) {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(companion.getHolder().getEntityId());
                UtilServer.sendPacket(all, packet);
                spawnAndTeleportCompanionParts(companion.getCompanionParts(), all);
            }

            for (Companion statue : statues) {
                PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(statue.getHolder().getEntityId());
                UtilServer.sendPacket(all, packet);
                spawnAndTeleportCompanionParts(statue.getCompanionParts(), all);
            }
        }
    }

//    @EventHandler
//    public void fixSkins(UpdateEvent event) {
//        if(event.getType() != UpdateType.SEC) return;
//
//        for (Companion companion : companions.values()) {
//            for (CompanionPart companionPart : companion.getCompanionParts()) {
//                companionPart.updateSkin();
//            }
//        }
//    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        if (!companions.containsKey(event.getPlayer()))
            return;

        Player player = event.getPlayer();
        Companion companion = companions.get(player);
        if (companion == null) return;
        companion.remove();

        companions.remove(player);
    }

    @EventHandler
    public void armorstandInteraction(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked() == null || event.getRightClicked().getType() != EntityType.ARMOR_STAND) return;
        ArmorStand armorStand = (ArmorStand) event.getRightClicked();
        if (((CraftEntity) armorStand).getHandle() instanceof CompanionEntityPart)
            event.setCancelled(true);

        //Client sometimes has a bugged/invisible item when interacting with Armorstands,
        //This should always update the Client when interacting.
        event.getPlayer().updateInventory();
    }

    @EventHandler
    public void crashPrevention(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity.getCustomName() == null || entity.getCustomName().equals("")) continue;
            if (!entity.getCustomName().contains("companion")) continue;

            boolean found = false;
            for (Companion companion : companions.values()) {
                if (!companion.getHolder().equals(entity)) continue;
                companion.remove();
                found = true;
                break;
            }

            if (!found) entity.remove();
        }
    }

    @EventHandler
    public void worldChange(EntityPortalEvent event) {
        if (event.getEntity() == null) return;
        if (!isEntityCompanion(event.getEntity())) return;

        if (!event.isCancelled()) event.setCancelled(true);
    }

    @EventHandler
    public void worldChange(PlayerTeleportEvent event) {
        if (!hasActiveCompanion(event.getPlayer())) return;
        CompanionType type = companions.get(event.getPlayer()).getCompanionType();
        removePlayerCompanion(event.getPlayer());
        new BukkitRunnable() {
            public void run() {
                //If players disconnect in the middle of a teleport.
                if (event.getPlayer().isOnline())
                    spawnCompanion(event.getPlayer(), type);
            }
        }.runTaskLater(plugin, 20L);
    }
}
