package me.adamtanana.core.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.gson.*;
import me.adamtanana.core.player.BanEntry;
import me.adamtanana.core.player.GlobalPlayer;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CommonUtil {
    private static final String MOJANG_NAMES_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static final JsonParser JSON_PARSER = new JsonParser();
    private static final LoadingCache<UUID, List<String>> uuidNameCache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).maximumSize(100L).build(new CacheLoader<UUID, List<String>>() {
        @Override
        public List<String> load(UUID uuid) throws Exception {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format(MOJANG_NAMES_URL, uuid.toString().replace("-", ""))).openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            JsonArray json = (JsonArray) JSON_PARSER.parse(new InputStreamReader(connection.getInputStream()));
            List<String> names = Lists.newArrayList();
            for (JsonElement e : json) {
                JsonObject entry = (JsonObject) e;
                names.add(entry.get("name").getAsString());
            }
            return names;
        }
    });
    private static final LoadingCache<String, UUID> nameUuidCache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).maximumSize(100L).build(new CacheLoader<String, UUID>() {
        @Override
        public UUID load(String name) throws Exception {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.mojang.com/profiles/minecraft").openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            JsonArray arr = new JsonArray();
            arr.add(new JsonPrimitive(name));
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(arr.toString());
            out.flush();
            JsonArray json = (JsonArray) JSON_PARSER.parse(new InputStreamReader(connection.getInputStream()));
            String uuid = json.get(0).getAsJsonObject().get("id").getAsString();
            out.close();
            return parseUUID(uuid);
        }
    });
    private static final LoadingCache<UUID, GlobalPlayer> uuidPlayerCache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).maximumSize(100L).build(new CacheLoader<UUID, GlobalPlayer>() {
        @Override
        public GlobalPlayer load(UUID uuid) throws Exception {
            GlobalPlayer player = new GlobalPlayer(uuid);
            return player;
        }
    });
    private static final LoadingCache<String, GlobalPlayer> namePlayerCache = CacheBuilder.newBuilder().expireAfterAccess(30L, TimeUnit.MINUTES).maximumSize(100L).build(new CacheLoader<String, GlobalPlayer>() {
        @Override
        public GlobalPlayer load(String name) throws Exception {
            GlobalPlayer player = new GlobalPlayer(name);
            return player;
        }
    });

    /**
     * Get UUID of the player by their name.
     * Cached for 30 min. (max 100)
     *
     * @param name Name of the player
     * @return UUID of the player, null if non existent
     */
    public static UUID getUUIDByName(String name) {
        try {
            GlobalPlayer player = namePlayerCache.get(name);
            return player != null ? player.getUniqueUserId() : forceFetchUUID(name);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * Parse uuid from string
     *
     * @param uuidStr
     * @return
     */
    public static UUID parseUUID(String uuidStr) {
        //Split uuid in to 5 components
        String[] uuidComponents = new String[]{
                uuidStr.substring(0, 8),
                uuidStr.substring(8, 12),
                uuidStr.substring(12, 16),
                uuidStr.substring(16, 20),
                uuidStr.substring(20, uuidStr.length())
        };

        //Combine components with a dash
        StringBuilder builder = new StringBuilder();
        for (String component : uuidComponents) {
            builder.append(component).append('-');
        }

        //Correct uuid length, remove last dash
        builder.setLength(builder.length() - 1);
        return UUID.fromString(builder.toString());
    }

    /**
     * Force fetch the player's UUID from mojang(cached)
     *
     * @param name Name of the player
     * @return UUID of the player
     */
    public static UUID forceFetchUUID(String name) {
        try {
            return nameUuidCache.get(name);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get name of player by UUID.
     * Cached for 30 min.
     *
     * @param uuid Unique user id of player
     * @return All previous and current names of player starting by oldest, null if failed to retrieve
     */
    public static List<String> getNamesByUUID(UUID uuid) {
        try {
            return uuidNameCache.get(uuid);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * Returns the player's current name.
     * Cached for 30 minutes.
     *
     * @param uuid Unique user id of player
     * @return Current name of player, null if failed to retrieve.
     */
    public static String getNameByUUID(UUID uuid) {
        try {
            GlobalPlayer player = uuidPlayerCache.get(uuid);
            return player.getLastName();
        } catch (ExecutionException e) {
            return null;
        }
    }

    public static List<GlobalPlayer> getAlts(String ip) {
        List<GlobalPlayer> alts = Lists.newArrayList();
        namePlayerCache.asMap().values().stream().filter(globalPlayer -> globalPlayer.getKnownIps().contains(ip)).forEach(globalPlayer -> {
            alts.add(globalPlayer);
        });
        return alts;
    }

    public static List<BanEntry> getIpBans(String ip) {
        List<BanEntry> banEntries = Lists.newArrayList();
        getAlts(ip).forEach((gp) ->
                gp.getBanEntries().stream().filter((ban) -> !ban.getIp().isEmpty() && !banEntries.contains(ban)).forEach(
                        (entry) -> banEntries.add(entry)
                ));
        return banEntries;
    }

    /**
     * Get global player info by player name.
     *
     * @param name Name of player
     * @return Global player info
     */
    public static GlobalPlayer getGlobalPlayer(String name) {
        try {
            return namePlayerCache.get(name);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * Delete the cached values for a player
     *
     * @param name Player's name
     */
    public static void invalidateGlobalPlayer(String name) {
        namePlayerCache.invalidate(name);
    }

    /**
     * Delete the cached values for a player
     *
     * @param uuid Player's UUID
     */
    public static void invalidateGlobalPlayer(UUID uuid) {
        uuidPlayerCache.invalidate(uuid);
    }

    /**
     * Get global player info by player uuid.
     *
     * @param uuid Unique user id of player.
     * @return Global player info
     */
    public static GlobalPlayer getGlobalPlayer(UUID uuid) {
        try {
            return uuidPlayerCache.get(uuid);
        } catch (ExecutionException e) {
            return null;
        }
    }

    /**
     * My maths stuff
     **/
    public static byte degreeToByte(float f) {
        return (byte) (int) (f * 256f / 360f);
    }

    public static float degreeToRadian(float f) {
        return (float) (((f + 90) * Math.PI) / 180);
    }

    public static float radianToDegree(float f) {
        return f * 57.2957795f;
    }

    public static Object instance(Class<?> clazz, Object... args) {
        try {
            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
                if (constructor.getParameterTypes().length == args.length) {
                    boolean typeMismatch = false;
                    for (int i = 0; i < args.length; i++) {
                        if (!constructor.getParameterTypes()[i].isInstance(args[i])) {
                            typeMismatch = false;
                        }
                    }

                    if (!typeMismatch) {
                        if (!constructor.isAccessible()) {
                            constructor.setAccessible(true);
                        }
                        try {
                            return constructor.newInstance(args);
                        } catch (Exception e) {
                            try {
                                return clazz.newInstance();
                            } catch (Exception e1) {
                                throw new IllegalArgumentException("Invalid amount of parameters when constructing class!", e1);
                            }
                        }
                    }
                }
            }

            //Attempt to use 0 args
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid amount of parameters when constructing class!", e);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Class constructing failed", e);
        }
    }

    public static List<GlobalPlayer> getAllCachedPlayers() {
        return new ArrayList<>(namePlayerCache.asMap().values());
    }
}
