package me.adamtanana.core.redis.message;

import lombok.Getter;
import lombok.Setter;
import me.adamtanana.core.redis.Message;

import java.util.UUID;

public class PlayerOnlineMessage implements Message {

    private @Getter UUID messageId = UUID.randomUUID();
    private @Getter String playerName;
    private @Getter @Setter boolean online;

    public PlayerOnlineMessage() {}

    public PlayerOnlineMessage(String playerName) {
        this.playerName = playerName;
    }

    public PlayerOnlineMessage(String playerName, boolean b) {
        this.playerName = playerName;
        this.online = b;
    }

    public PlayerOnlineMessage(String playerName, boolean b, UUID id) {
        this.playerName = playerName;
        this.online = b;
        this.messageId = id;
    }
}
