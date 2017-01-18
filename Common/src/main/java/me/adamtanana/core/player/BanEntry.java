package me.adamtanana.core.player;

import lombok.Getter;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;
import me.adamtanana.core.util.C;

import java.util.Date;
import java.util.UUID;

@Model
public class BanEntry {
    @Storable
    @Getter
    private UUID uuid;

    @Storable
    private String banReason;

    @Storable
    @Getter
    private long bannedOn;

    @Storable
    @Getter
    private String ip = "";

    @Storable
    private long dateDiff;

    @Storable
    @Getter
    private String staff;

    public BanEntry() {
    }

    public BanEntry(UUID uuid, String banner, String banReason, long bannedOn, long dateDiff) {
        this.uuid = uuid;
        this.bannedOn = bannedOn;
        this.staff = banner;
        this.dateDiff = dateDiff;
        if (dateDiff > 0) {
            this.banReason = "&4Banned: &c" + banReason + "\n&4Lasts until: " + getExpiryDate();
        } else {
            this.banReason = "&4Kicked: &c" + banReason;
        }
    }

    public BanEntry(String ip, String banner, String banReason, long bannedOn, long dateDiff) {
        this.ip = ip;
        this.staff = banner;
        this.bannedOn = bannedOn;
        this.dateDiff = dateDiff;
        if (isPermanent()) {
            this.banReason = "&4Banned: &c" + banReason + "\n&eYou are &4Permanently&e banned!";
        } else if (isKick()) {
            this.banReason = "&4Kicked: &c" + banReason;
        } else {
            this.banReason = "&4Banned: &c" + banReason + "\n&eYou are banned until &4" + getExpiryDate() + "&e!";
        }
    }

    public String getBanReason() {
        return C.replaceColors(banReason);
    }

    public String getExpiryDate() {
        return new Date(bannedOn + (dateDiff * 1000)).toString();
    }

    public boolean expired() {
        if (isPermanent()) return false;
        return System.currentTimeMillis() - bannedOn >= dateDiff;
    }

    public boolean isKick() {
        return dateDiff == 0;
    }

    public boolean isPermanent() {
        return dateDiff < 0;
    }

}
