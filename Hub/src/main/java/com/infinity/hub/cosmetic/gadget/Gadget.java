package com.infinity.hub.cosmetic.gadget;

import com.infinity.hub.cosmetic.Cosmetic;
import com.infinity.hub.cosmetic.CosmeticType;
import com.infinity.hub.cosmetic.attribute.CosmeticAmmo;
import com.infinity.hub.cosmetic.gadget.event.GadgetEvent;
import me.adamtanana.core.rarity.RarityType;
import me.adamtanana.core.util.CommonUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Gadget extends Cosmetic implements CosmeticAmmo {

    private GadgetTriggerType triggerType;
    private long cooldown;
    protected long ammo = -1;

    public Gadget( String displayName, Material displayMaterial, RarityType rarityType, GadgetTriggerType triggerType, long cooldown) {
        super(displayName, displayMaterial, rarityType, CosmeticType.GADGET);

        this.triggerType = triggerType;
        this.cooldown = cooldown;
    }

    protected abstract void run(Player player);

    protected void call(Player player) {
        GadgetEvent event = new GadgetEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public GadgetTriggerType getTriggerType() {
        return triggerType;
    }

    public long getCooldown() {
        return cooldown;
    }

    @Override
    public long getAmmo(Player player) {
        return ammo = CommonUtil.getGlobalPlayer(player.getUniqueId()).getGadgetData(GadgetType.getName(this));
    }

    @Override
    public void setAmmo(Player player, long amount) {
        if(amount < 0) amount = 0;
        getAmmo(player);
        ammo = amount;

        CommonUtil.getGlobalPlayer(player.getUniqueId()).setGadgetData(GadgetType.getName(this), amount);
    }

    @Override
    public void reduceAmmo(Player player, int amount) {
        getAmmo(player);
        if((ammo - amount) < 0) ammo = 0;
        else ammo -= amount;

        CommonUtil.getGlobalPlayer(player.getUniqueId()).setGadgetData(GadgetType.getName(this), ammo);
    }
}
