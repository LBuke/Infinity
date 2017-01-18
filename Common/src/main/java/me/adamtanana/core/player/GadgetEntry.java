package me.adamtanana.core.player;

import lombok.Getter;
import lombok.Setter;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;

import java.util.UUID;

@Model
public class GadgetEntry {

    @Storable
    @Getter
    private String gadgetName;

    @Storable
    @Getter
    @Setter
    private long data;

    public GadgetEntry() {
    }

    public GadgetEntry(String gadgetName, long data) {
        this.gadgetName = gadgetName;
        this.data = data;
    }

}
