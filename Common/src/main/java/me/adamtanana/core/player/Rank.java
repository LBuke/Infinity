package me.adamtanana.core.player;

import me.adamtanana.core.util.C;

public enum Rank {
    MEMBER(C.reset, ""),
    PRO(C.reset, "Pro"),
    ULTIMATE(C.lightPurple, "Ultimate"),
    GOD(C.darkPurple, "God"),
    YOUTUBUE(C.red, "Youtube"),
    BUILDER(C.reset, "Builder"),
    HELPER(C.yellow, "Helper"),
    MOD(C.gold, "Mod"),
    ADMIN(C.gold, "Admin"),
    DEVELOPER(C.gold, "Dev"),
    OWNER(C.darkRed, "Owner");

    private String color;
    private String name = "";

    Rank(String color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getName(boolean caps, boolean bold, boolean hasColor) {
        return (hasColor ? color : "") + (bold ? C.bold : "") + (caps ? name.toUpperCase() : name) + C.reset;
    }

    public boolean has(Rank rank) {
        return this.compareTo(rank) >= 0;
    }


}
