package com.infinity.hub.cosmetic.companions.command;

import com.infinity.hub.cosmetic.companions.companion.CompanionManager;
import com.infinity.hub.cosmetic.companions.type.CompanionType;
import me.adamtanana.core.player.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestCommand extends me.adamtanana.core.command.Command
{
	private CompanionManager companionManager;

	public TestCommand(CompanionManager companionManager) {
		super("test", new ArrayList<>(), 1, Rank.OWNER);

		this.companionManager = companionManager;
	}

	@Override
	public void execute(Player player, List<String> args) {
		if(args.get(0).equalsIgnoreCase("BB8")) companionManager.spawnCompanion(player, CompanionType.MC_8);
		else if(args.get(0).equalsIgnoreCase("Duck")) companionManager.spawnCompanion(player, CompanionType.DUCK);
		else if(args.get(0).equalsIgnoreCase("Gorilla")) companionManager.spawnCompanion(player, CompanionType.GORILLA);
		else if(args.get(0).equalsIgnoreCase("Dog")) companionManager.spawnCompanion(player, CompanionType.PUG);
		else if(args.get(0).equalsIgnoreCase("Chimp")) companionManager.spawnCompanion(player, CompanionType.CHIMP);
		else if(args.get(0).equalsIgnoreCase("Turtle")) companionManager.spawnCompanion(player, CompanionType.TURTLE);
		else if(args.get(0).equalsIgnoreCase("Minion")) companionManager.spawnCompanion(player, CompanionType.MINION);
		else if(args.get(0).equalsIgnoreCase("statue"))
		{

			Location lookAt = player.getTargetBlock((HashSet<Byte>) null ,100).getLocation();
			companionManager.spawnCompanionStatue(player.getPlayer().getLocation(), lookAt, CompanionType.GORILLA);
			//companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 0, 50, -3), lookAt, CompanionType.DUCK);
			//companionManager.spawnCompanion(new Location(Bukkit.getWorld("world"), 3, 50, -3), lookAt, CompanionType.MC_8);
		}

	}
}
