package me.adamtanana.core.player;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import lombok.Getter;
import lombok.Setter;
import me.adamtanana.core.database.Database;
import me.adamtanana.core.database.DatabaseEngine;
import me.adamtanana.core.database.Model;
import me.adamtanana.core.database.Storable;
import me.adamtanana.core.util.CommonUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Model(name = "players")
public class GlobalPlayer {
    @Storable
    @Getter
    private UUID uniqueUserId = UUID.randomUUID();

    @Storable
    @Getter
    private String lastName = "";

    @Storable(genericType = String.class)
    @Getter
    private List<String> knownIps = Lists.newArrayList();

    @Storable
    @Getter
    private String lastIp = "";

    @Storable
    @Getter
    private Rank rank = Rank.OWNER;

    @Storable(genericType = BanEntry.class)
    @Getter
    private List<BanEntry> banEntries = Lists.newArrayList();

    @Storable(genericType = MuteEntry.class)
    @Getter
    private List<MuteEntry> muteEntries = Lists.newArrayList();

    @Storable(genericType = StatEntry.class)
    @Getter
    private List<StatEntry> allStats = Lists.newArrayList();

    @Storable(genericType = GadgetEntry.class)
    @Getter
    private List<GadgetEntry> allGadgets = Lists.newArrayList();

    @Storable(genericType = String.class)
    @Getter
    private List<String> allPets = Lists.newArrayList();

    @Storable(genericType = UUID.class)
    @Getter
    private List<UUID> friends = Lists.newArrayList();

    @Storable(genericType = UUID.class)
    @Getter
    private List<UUID> pendingFriends = Lists.newArrayList();

    @Storable
    @Getter
    private OldViolations oldViolations;

    @Storable
    @Getter
    private String lastServer = "";

    @Storable
    @Getter
    private long lastOnline = 0;

    @Storable
    @Getter
    private long coins = 0;

    @Storable
    @Getter
    private long points = 0;

    @Storable
    @Getter
    private boolean isVanished = false;

    @Getter @Setter
    private String locale = "en_US";

    public GlobalPlayer(UUID uniqueUserId) {
        oldViolations = new OldViolations(uniqueUserId);
        this.uniqueUserId = uniqueUserId;
        Database database = DatabaseEngine.getInstance().getDatabase("global");
        database.loadModel(this, new BasicDBObject("UniqueUserId", uniqueUserId.toString()));
    }

    public GlobalPlayer(String lastName) {
        Database database = DatabaseEngine.getInstance().getDatabase("global");
        database.loadModel(this, new BasicDBObject("lastName", lastName));
    }

    public void save() {
//        CommonUtil.invalidateGlobalPlayer(getUniqueUserId());
//        CommonUtil.invalidateGlobalPlayer(getLastName());
        Database database = DatabaseEngine.getInstance().getDatabase("global");
        database.saveModel(this, new BasicDBObject("UniqueUserId", uniqueUserId.toString()));
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
        save();
    }


    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
        if (!knownIps.contains(lastIp)) {
            knownIps.add(lastIp);
        }
        save();
    }

    public void addBanEntry(BanEntry entry) {
        banEntries.add(entry);
        save();
    }

    public void addMuteEntry(MuteEntry entry) {
        muteEntries.add(entry);
        save();
    }

    public void setLastServer(String lastServer) {
        this.lastServer = lastServer;
        save();
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
        save();
    }

    public void setCoins(long coins) {
        this.coins = coins;
        save();
    }

    public void addFriend(UUID player) {
        this.friends.add(player);
        save();

    }

    public void addPendingFriend(UUID toAdd) {
        this.pendingFriends.add(toAdd);
        save();
    }

    public void setVanished(boolean b) {
        isVanished = b;
        save();
    }

    public void addStat(StatEntry entry) {
        for (StatEntry stat : getAllStats()) {
            if (stat.getGameType().equalsIgnoreCase(entry.getGameType()) && stat.getStatName().equalsIgnoreCase(entry.getStatName())) {
                System.out.printf("Stat: " + entry.toString() + " Updated from %i to %i", stat.getData(), entry.getData());
                stat.setData(entry.getData());
                save();
                return;
            }
        }
        this.allStats.add(entry);
        System.out.printf("Stat: " + entry.toString() + " Updated from %i to %i", 0, entry.getData());
        save();

    }

    public List<StatEntry> getStats(String gameType) {
        List<StatEntry> stats = Lists.newArrayList();

        getAllStats().stream().filter(statEntry -> statEntry.getGameType().equalsIgnoreCase(gameType)).forEach(s -> stats.add(s));

        return stats;
    }

    public void setGadgetData(String gadgetName, long data) {
        for (GadgetEntry gadget : getAllGadgets()) {
            if (gadget.getGadgetName().equalsIgnoreCase(gadgetName)) {
                gadget.setData(data);
                save();
                return;
            }
        }
        getAllGadgets().add(new GadgetEntry(gadgetName, data));
        save();

    }

    public boolean hasGadget(String gadgetName) {
        return getGadgetData(gadgetName) > 0;
    }

    public long getGadgetData(String gadget) {
        List<GadgetEntry> gadgets = Lists.newArrayList();

        getAllGadgets().stream().filter(g -> g.getGadgetName().equalsIgnoreCase(gadget)).forEach(g -> gadgets.add(g));

        if (gadgets.size() > 0) return gadgets.get(0).getData();
        return 0;
    }

    public boolean hasPet(String petName) {
        return getAllPets().contains(petName.toLowerCase());
    }

    public void addPet(String petName) {
        getAllPets().add(petName);
        save();
    }


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(gson.fromJson(DatabaseEngine.getInstance().getDatabase("global")
                        .getCollection("players").find(new BasicDBObject("UniqueUserId", uniqueUserId.toString())).next().toString(),
                Map.class));
    }
}