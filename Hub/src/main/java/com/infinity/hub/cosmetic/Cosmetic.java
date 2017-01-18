package com.infinity.hub.cosmetic;

import me.adamtanana.core.rarity.RarityType;
import org.bukkit.Material;

public abstract class Cosmetic {

    private String displayName;
    private Material displayMaterial;
    private CosmeticType cosmeticType;
    private RarityType rarityType;
    
    public Cosmetic(String displayName, Material displayMaterial, RarityType rarityType, CosmeticType cosmeticType) {
        this.displayName = displayName;
        this.displayMaterial = displayMaterial;
        this.rarityType = rarityType;
        this.cosmeticType = cosmeticType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getDisplayMaterial() {
        return displayMaterial;
    }

    public RarityType getRarityType() {
        return rarityType;
    }

    public CosmeticType getCosmeticType() {
        return cosmeticType;
    }
}
