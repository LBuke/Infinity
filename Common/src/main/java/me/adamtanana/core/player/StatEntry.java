package me.adamtanana.core.player;

import lombok.Getter;
import lombok.Setter;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;

import java.util.UUID;

@Model
public class StatEntry {
    /**
     * SG
     * - Kills stat
     * - Deaths stat
     * - Wins
     * - Losses
     * <p/>
     * Climber
     * - Blocks dropped
     * - Deaths
     * - Wins
     * - Losses
     */

    @Storable
    @Getter
    private UUID uuid;

    @Storable
    @Getter
    private String statName;

    @Storable
    @Getter
    private String gameType;

    @Storable
    @Getter
    @Setter
    private long data;

    public StatEntry() {
    }

    public StatEntry(UUID uuid, String gameType, String statName, long data) {
        this.uuid = uuid;
        this.statName = statName;
        this.gameType = gameType;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{UUID: " + uuid.toString() + ", Stat: " + statName + ", game: " + gameType + ", data: " + data + "}";
    }


}

