package com.infinity.hub.cosmetic.companions.companion.part.armorstand;

import com.infinity.hub.cosmetic.companions.CompanionModule;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModuleAnimated;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import me.adamtanana.core.util.UtilServer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.util.EulerAngle;

public class CompanionEntityPart extends EntityArmorStand
{
	public CompanionPart companionPart;

	public CompanionEntityPart(World world, CompanionPart companionPart)
	{
		super(world);

		this.companionPart = companionPart;
	}

	@Override
	public void m()
	{
        if(companionPart.getParent() != null && companionPart.getParent().getHolder() != null && companionPart.getParent().getPlayer() != null&& !companionPart.getParent().getPlayer().isOnline())
        {
            CompanionModule.getCompanionManager().removePlayerCompanion(companionPart.getParent().getPlayer());
            return;
        }
		if(companionPart.getParent() == null) return;

		Location loc = companionPart.update();
		setPosition(loc.getX(), loc.getY(), loc.getZ());
		yaw = loc.getYaw();
		pitch = loc.getPitch();

		if(companionPart instanceof CompanionModuleAnimated)
		{
			((CompanionModuleAnimated) companionPart).animate(this);
			if (companionPart.getParent().isMoving()) ((CompanionModuleAnimated) companionPart).moveAnimate(this);
			else ((CompanionModuleAnimated) companionPart).stillAnimate(this);
		}

		PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(this);
		Bukkit.getOnlinePlayers().forEach(player -> UtilServer.sendPacket(player, teleport));
//		PlayerUtil.sendPacket(companionPart.getParent().getPlayer(), teleport); Had to remove for statue support
//		super.m();
	}

	public static CompanionEntityPart spawn(CompanionPart companionPart)
	{
		Location loc = companionPart.update();
		//System.out.println(loc.getX() + " , " + loc.getY() + " , " + loc.getZ());
		World w = ((CraftWorld) loc.getWorld()).getHandle();
		CompanionEntityPart armorStand = new CompanionEntityPart(w, companionPart);

		//add entity to the world
		armorStand.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		w.addEntity(armorStand, CreatureSpawnEvent.SpawnReason.CUSTOM);

		//send packet
		PacketPlayOutSpawnEntity spawn = new PacketPlayOutSpawnEntity(armorStand, 30);
		PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport(armorStand);
		Bukkit.getOnlinePlayers().forEach(all -> UtilServer.sendPacket(all, spawn, tp));
		return armorStand;
	}

	public EulerAngle fromVector3f(Vector3f old) {
		return new EulerAngle(Math.toRadians((double)old.getX()), Math.toRadians((double)old.getY()), Math.toRadians((double)old.getZ()));
	}

	public Vector3f fromEulerAngle(EulerAngle old) {
		return new Vector3f((float)Math.toDegrees(old.getX()), (float)Math.toDegrees(old.getY()), (float)Math.toDegrees(old.getZ()));
	}
}