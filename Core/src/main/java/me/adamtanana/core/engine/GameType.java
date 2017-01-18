package me.adamtanana.core.engine;

import lombok.Getter;

public enum GameType {
    TEST("Test", 10);

    @Getter private String name;
    @Getter private int slots;

    GameType(String name, int slots) {
        this.name = name;
        this.slots = slots;
    }
}
