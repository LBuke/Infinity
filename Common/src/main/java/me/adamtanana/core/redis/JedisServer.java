package me.adamtanana.core.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JedisServer {
    private final Gson gson = new Gson();
    private JedisPool jedisPool;
    private MessageReader reader;
    private MessageWriter writer;

    @Getter
    private static HashMap<Class<? extends Message>, List<MessageListener>> listeners = Maps.newHashMap();

    public JedisServer(String serverName) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379, 0, null); //TODO PRODUCTION REDIS

        try (Jedis jedis = jedisPool.getResource()){
            jedis.ping();
        } catch (Exception e) {
            e.printStackTrace();
        }

        reader = new MessageReader(serverName);
        writer = new MessageWriter(serverName, jedisPool);

        new Thread(() -> {
            try (Jedis j = getJedisPool().getResource()) {
                j.subscribe(reader, "infinity");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public void sendMessage(Object message, String server) {
        writer.publishPacket(message, server);
    }

    public void disable() {
        if (reader.isSubscribed()) reader.unsubscribe();
        jedisPool.destroy();
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public static  <T extends Message> void registerListener(Class<T> msg, MessageListener <T>listener) {
        if(listeners.containsKey(msg)) listeners.get(msg).add(listener);
        else {
            List<MessageListener> list  = Lists.newArrayList();
            list.add(listener);
            listeners.put(msg, list);
        }
    }

}
