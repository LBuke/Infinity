package me.adamtanana.core.util;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.entity.Player;

import java.util.List;

public class UtilText {

    public static String join(List<String> list, char seperator) {
        StringBuilder msg = new StringBuilder();
        list.forEach(s -> msg.append(s + seperator));

        if(msg.length()>0) msg.setLength(msg.length() - 1);
        return msg.toString();
    }

    public static void sendActionBar(Player player, String message) {
        PacketPlayOutChat chat = new PacketPlayOutChat(new ChatMessage(message), (byte) 2);
        UtilServer.sendPacket(player, chat);
    }

}
