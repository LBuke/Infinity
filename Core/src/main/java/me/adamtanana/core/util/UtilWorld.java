package me.adamtanana.core.util;

import net.minecraft.server.v1_8_R3.RegionFileCache;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class UtilWorld {
    private static File mapFolder;// = new File(Bukkit.getWorldContainer() + "../maps");
    private static File worldFolder;// = Bukkit.getWorldContainer();

    public static void init() {
        try {
            mapFolder = new File(Bukkit.getWorldContainer().getCanonicalPath() + "/../maps");
            System.out.println("MAAP FOLDER NAME IS " + mapFolder.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        worldFolder = Bukkit.getWorldContainer();
        mapFolder.mkdirs();
        worldFolder.mkdirs();

    }

    public static void loadWorld(String gameType, String mapName, Callback<World> callback) {
        if (Bukkit.getWorld(mapName) != null) {
            if (callback != null) callback.call(Bukkit.getWorld(mapName));
            return;
        }
        UtilServer.runTaskAsync(() -> {
            File map = new File(mapFolder, gameType + "/" + mapName);
            System.out.println(mapFolder);
            System.out.println(map);

            try {
                FileUtils.copyDirectory(map, new File(worldFolder, mapName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            UtilServer.runTask(() -> {
                WorldCreator creator = new WorldCreator(mapName);
                creator.generateStructures(false);
                creator.type(WorldType.FLAT);
                World world = creator.createWorld();
                world.setAutoSave(false);
                world.setKeepSpawnInMemory(false);
                world.setThundering(false);
                world.setStorm(false);
                world.setDifficulty(Difficulty.EASY);
                if (callback != null) callback.call(world);
            });
        });


    }

    public static void unloadWorld(String mapName, Callback callback) {
        World world = Bukkit.getWorld(mapName);
        if (world == null) return;
        File folder = world.getWorldFolder();
        for (Player player : world.getPlayers()) player.kickPlayer(C.red + "You have been disconnected");
        for (Chunk chunk : world.getLoadedChunks()) {
            chunk.unload(false, false);
        }

        Bukkit.unloadWorld(world, false);
        RegionFileCache.a();
        UtilServer.runTaskAsyncLater(() -> {
            try {
                FileUtils.deleteDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (callback != null) callback.call(null);

        }, 5L);
    }

}
