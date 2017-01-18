package com.infinity.hub.cosmetic.attribute;

import org.bukkit.entity.Player;

public interface CosmeticAmmo {
    long getAmmo(Player player);
    void setAmmo(Player player, long amount);
    void reduceAmmo(Player player, int amount);
}
