package com.infinity.hub.cosmetic.companions;

import com.infinity.hub.cosmetic.companions.command.TestCommand;
import com.infinity.hub.cosmetic.companions.companion.CompanionManager;
import com.infinity.hub.cosmetic.companions.type.CompanionType;
import me.adamtanana.core.Module;
import me.adamtanana.core.command.CommandHandler;
import me.adamtanana.core.util.UtilServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created: 02/05/2016
 *
 * @author Teddeh
 * @version 1.0
 */
public class CompanionModule extends Module {

    private static CompanionManager companionManager;

    @Override
    public void enable() {
        companionManager = new CompanionManager(UtilServer.getPlugin());

        CommandHandler.register(new TestCommand(companionManager));

        for(World world : Bukkit.getWorlds())
        {
            for(Entity entity : world.getEntities())
            {
                if(entity.getCustomName() == null) continue;
                if(!entity.getCustomName().equals("companion")) continue;
                for(Entity entity2 : entity.getNearbyEntities(2, 2, 2))
                {
                    if(!(entity2 instanceof ArmorStand)) continue;
                    if(((ArmorStand) entity2).isVisible()) continue;
                    if(entity2.getCustomName() != null) continue;
                    entity2.remove();
                }
                entity.remove();
            }
        }

//        Location lookAt = new Location(Bukkit.getWorld("world"), 0, 50, 0);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), -3, 50, -3), lookAt, CompanionType.GORILLA);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 0, 50, -3), lookAt, CompanionType.DUCK);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 3, 50, -3), lookAt, CompanionType.MC_8);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), -6, 50, -6), lookAt, CompanionType.CHIMP);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 0, 50, -6), lookAt, CompanionType.PUG);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 6, 50, -6), lookAt, CompanionType.MINION);
//        companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 6, 50, 6), lookAt, CompanionType.TURTLE);
    }

    @Override
    public void disable() {
        companionManager.removeAllCompanions();
	    companionManager.removeAllStatues();
    }

    public static CompanionManager getCompanionManager() {
        return companionManager;
    }
}
