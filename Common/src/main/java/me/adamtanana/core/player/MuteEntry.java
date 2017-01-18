package me.adamtanana.core.player;

import lombok.Getter;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;

import java.util.Date;
import java.util.UUID;

@Model
public class MuteEntry {
    @Storable
    @Getter
    private long date;

    @Storable
    @Getter
    private long dateDiff;

    @Storable
    @Getter
    private String reason;

    @Storable
    @Getter
    private UUID muted;

    @Storable
    @Getter
    private String staff;

    public MuteEntry() {
    }

    public MuteEntry(UUID muted, String staff, long date, long dateDiff, String reason) {
        this.date = date;
        this.staff = staff;
        this.dateDiff = dateDiff;
        this.reason = reason;
        this.muted = muted;
    }

    public String getExpiryDate() {
        return new Date(date + (dateDiff * 1000)).toString();
    }

    public boolean expired() {
        if (isPermanent()) return false;
        return System.currentTimeMillis() - date >= dateDiff;
    }

    public boolean isPermanent() {
        return dateDiff < 0;
    }
}
