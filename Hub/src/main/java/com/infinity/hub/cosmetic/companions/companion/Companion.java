package com.infinity.hub.cosmetic.companions.companion;

import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import com.infinity.hub.cosmetic.companions.type.CompanionType;
import me.adamtanana.core.util.UtilEntity;
import me.adamtanana.core.util.UtilServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Companion implements Cloneable {
    private Location spawnLocation, lastLocation;
    private HashSet<CompanionPart> companionParts;
    private boolean active = false, moving = false;
    private Entity holder;
    private final EntityType entityType;
    private Player player;
    private final CompanionType companionType;
    private boolean statue;

    public Companion(Player player, EntityType entityType, CompanionType companionType) {
        this.statue = false;
        this.companionType = companionType;
        this.entityType = entityType;
        if(player != null) {
            this.player = player;
            this.spawnLocation = player.getLocation();
        }
        this.companionParts = new HashSet<>();
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public HashSet<CompanionPart> getCompanionParts() {
        return companionParts;
    }

    public void addCompanionParts(CompanionPart... parts) {
        this.companionParts.addAll(Arrays.asList(parts));
    }

    public CompanionPart getPartByName(String name) {
        for (CompanionPart part : this.companionParts) {
            if (!part.getName().equals(name)) continue;
            return part;
        }

        return null;
    }

    public CompanionPart getPartById(int id) {
        for (CompanionPart part : this.companionParts) {
            if (part.getId() != id) continue;
            return part;
        }

        return null;
    }

    public Entity getHolder() {
        return holder;
    }

    public boolean isActive() {
        return active;
    }

    public void spawn(boolean statue) {
        this.statue = statue;
        holder = this.spawnLocation.getWorld().spawnEntity(this.spawnLocation, entityType);
        holder.setCustomName("companion");
        double speed = 0.4d;
        if (holder instanceof LivingEntity && !statue) {
            for(PotionEffect potty : player.getActivePotionEffects())
            {
                if(potty.getType().equals(PotionEffectType.SPEED) && potty.getAmplifier() == 2)
                    speed += 0.4f;
            }
            CompanionFollower.follow((LivingEntity) holder, player.getUniqueId(), speed, statue);
        }

        if(statue)
        {
            UtilEntity.setNoAI(holder);
            UtilEntity.lookAtPlayerAI(holder, 6f);
        }

        UtilEntity.silence(holder, true);
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(holder.getEntityId());
        UtilServer.broadcastPacket(destroy);
        companionParts.forEach(CompanionPart::spawn);
        active = true;
    }

    public void remove() {
        active = false;
        companionParts.forEach(CompanionPart::remove);
        if (holder != null) holder.remove();
        companionParts.clear();
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isMoving() {
        return moving;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public CompanionType getCompanionType() {
        return companionType;
    }

    public void setSpawnLocation(Location spawnLocation)
    {
        this.spawnLocation = spawnLocation;
    }

    public boolean isStatue()
    {
        return statue;
    }
}
