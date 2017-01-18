package me.adamtanana.core.database;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import me.adamtanana.core.database.codecs.*;
import me.adamtanana.core.reflection.SafeClass;
import me.adamtanana.core.reflection.SafeField;
import me.adamtanana.core.util.CaseInsensitiveMap;
import me.adamtanana.core.util.CommonUtil;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DatabaseEngine {
    private static DatabaseEngine instance;

    public static DatabaseEngine getInstance() {
        return instance;
    }

    private final LoadingCache<Class<?>, Map<String, SafeField>> fieldCache;
    private final Set<DBCodec<?>> codecs = Sets.newConcurrentHashSet();
    private final MongoClient client;

    private static ExecutorService pool;

    public DatabaseEngine(String address, int port) {
        pool = Executors.newCachedThreadPool();
        instance = this;
        try {
            this.client = new MongoClient(address, port);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Unknown database host: " + address + ":" + port);
        }

        // Register default codecs
        registerCodec(EnumCodec.class);
        registerCodec(ListCodec.class);
        registerCodec(ModelCodec.class);
        registerCodec(UUIDCodec.class);
        registerCodec(MapCodec.class);

        // Create field cache
        this.fieldCache = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).maximumSize(32L).build(new CacheLoader<Class<?>, Map<String, SafeField>>() {
            @Override
            public Map<String, SafeField> load(Class<?> aClass) throws Exception {
                Map<String, SafeField> map = new CaseInsensitiveMap();
                SafeClass safeClass = new SafeClass(aClass);
                for (SafeField field : safeClass.getFields()) {
                    map.put(field.getName(), field);
                }
                return map;
            }
        });


    }

    public Database getDatabase(String name) {
        DB db = client.getDB(name);
        return new Database(this, db);
    }

    /**
     * Register a custom codec
     *
     * @param codecClass Class of the codec
     */
    public void registerCodec(Class<? extends DBCodec<?>> codecClass) {
        DBCodec<?> codec = (DBCodec<?>) CommonUtil.instance(codecClass, this);
        codecs.add(codec);
    }

    public Object encode(Class<?> type, SafeField field, Object value) {
        for (DBCodec<?> codec : codecs) {
            if (codec.canEncode(type)) {
                return codec.encodeFrom(type, field, value);
            }
        }

        return value;
    }

    public Object decode(Class<?> type, SafeField field, Object dbObject) {
        for (DBCodec<?> codec : codecs) {
            if (codec.canDecode(type)) {
                return codec.decode(type, field, dbObject);
            }
        }

        return dbObject;
    }

    public Map<String, SafeField> getFields(Class<?> clazz) {
        try {
            return fieldCache.get(clazz);
        } catch (ExecutionException e) {
            return null;
        }
    }

    public static ExecutorService getPool() {
        return pool;
    }
}