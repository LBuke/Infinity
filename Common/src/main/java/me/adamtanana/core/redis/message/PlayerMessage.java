package me.adamtanana.core.redis.message;

import lombok.Getter;
import me.adamtanana.core.redis.Message;

public class PlayerMessage implements Message {
    @Getter
    private String player, message;

    public PlayerMessage() {}

    public PlayerMessage(String playerReceiver, String message) {
        this.player = playerReceiver;
        this.message = message;
    }
}
