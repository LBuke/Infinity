package me.adamtanana.core.util;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;

public class UtilTime {
    private static HashMap<Player, HashMap<String, Long>> lastUse = Maps.newHashMap();

    public static boolean elapsed(long last, long time) {
        return System.currentTimeMillis() - last >= time;
    }

    public static boolean useAbility(Player player, String ability, long timeBetweenUse, boolean notify) {
        boolean canUse = false;

        if(lastUse.containsKey(player)) {
            HashMap<String, Long> cooldown = lastUse.get(player);
            if(cooldown.containsKey(ability)) {
                if(elapsed(cooldown.get(ability), timeBetweenUse)) {
                    cooldown.put(ability, System.currentTimeMillis());
                    canUse = true;
                }
            } else {
                cooldown.put(ability, System.currentTimeMillis());
                canUse = true;
            }
        } else{
            HashMap<String, Long> cooldown = Maps.newHashMap();
            cooldown.put(ability, System.currentTimeMillis());
            lastUse.put(player, cooldown);
            canUse = true;
        }

        if(notify && !canUse) {
            long last = lastUse.get(player).get(ability);
            player.sendMessage(C.darkAqua + "You can't use this ability for another " + C.red + timeLeft(timeBetweenUse - (System.currentTimeMillis() - last)));
        }
        return canUse;
    }

    private static String timeLeft(double millis) {
        DecimalFormat df = new DecimalFormat("#.#");
        if(millis > 60 * 60 * 1000) {
            return df.format((millis / 60 * 60 * 1000)) + " hours";
        } else if(millis > 60 * 1000) {
            return df.format((millis / 60 * 1000)) + " minutes";
        } else {
            return df.format((millis / 1000)) + " seconds";
        }

    }
}
