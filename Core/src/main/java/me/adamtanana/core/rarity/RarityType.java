package me.adamtanana.core.rarity;

public enum RarityType {

    COMMON("Common"),
    RARE("Rare"),
    EPIC("Epic"),
    LEGENDARY("Legendary"),
    CLASSIFIED("Classified");

    private String name;

    RarityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getOrdinal() {
        return ordinal();
    }
}
