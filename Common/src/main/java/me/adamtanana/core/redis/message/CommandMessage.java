package me.adamtanana.core.redis.message;

import lombok.Getter;
import me.adamtanana.core.redis.Message;

public class CommandMessage implements Message {

    @Getter
    private String command = "";

    public CommandMessage() {}

    public CommandMessage(String cmd) {
        command = cmd;
    }
}
