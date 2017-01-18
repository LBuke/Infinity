package me.adamtanana.core.player;

import lombok.Getter;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Model
public class OldViolations {
    @Storable
    @Getter
    private UUID playerUUID;

    @Storable(genericType = BanEntry.class)
    @Getter
    private List<BanEntry> bans = new ArrayList<>();

    @Storable(genericType = MuteEntry.class)
    @Getter
    private List<MuteEntry> mutes = new ArrayList<>();

    public OldViolations(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public OldViolations() {
    }

    public void addBan(BanEntry entry) {
        bans.add(entry);
    }

    public void addMute(MuteEntry entry) {
        mutes.add(entry);
    }

    public void removeBan(BanEntry entry) {
        bans.remove(entry);
    }

    public void removeMute(MuteEntry entry) {
        mutes.remove(entry);
    }

}
