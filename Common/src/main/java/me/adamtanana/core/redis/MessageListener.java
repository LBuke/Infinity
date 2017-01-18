package me.adamtanana.core.redis;

public interface MessageListener<T extends Message> {

    void onReceive(String sender, T msg);
}
