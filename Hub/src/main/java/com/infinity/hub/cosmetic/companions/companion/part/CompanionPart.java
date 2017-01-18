package com.infinity.hub.cosmetic.companions.companion.part;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;
import me.adamtanana.core.util.UtilServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public abstract class CompanionPart
{
	private final String name;
	private final int id;

	private float offset, radius, directionOffset;
	private double yMod;
	private final CompanionEquipment equipment;
	private final CompanionOptions options;
	private ArmorStand holder;
	private final Companion parent;


	public CompanionPart(Companion parent, String name, int id, double yMod, float offset, float radius, float directionOffset, CompanionEquipment equipment, CompanionOptions options)
	{
		this.name = name;
		this.id = id;
		this.yMod = yMod;
		this.parent = parent;
		this.offset = offset;
		this.radius = radius;
		this.directionOffset = directionOffset;
		this.equipment = equipment;
		this.options = options;
	}

	public void spawn()
	{
		CompanionEntityPart entityPart = new CompanionEntityPart(((CraftWorld) parent.getHolder().getWorld()).getHandle(), this).spawn(this);
		holder = (ArmorStand) entityPart.getBukkitEntity();
		holder.setMarker(false);
		options.apply(holder);
		equipment.apply(holder);

		if(parent.isStatue() && name.equals("head"))
		{
			holder.setCustomName(parent.getCompanionType().getName());
			holder.setCustomNameVisible(true);
		}

		teleport();
		updateSkin();
	}

	public void updateSkin() {
//		List<PacketPlayOutEntityEquipment> equipmentList = new ArrayList<>();
//		if(equipment.head != null) {
//			equipmentList.add(new PacketPlayOutEntityEquipment(holder.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(equipment.head)));
//		}
//
//		if(equipment.chest != null) {
//			equipmentList.add(new PacketPlayOutEntityEquipment(holder.getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(equipment.chest)));
//		}
//
//		if(equipment.legs != null) {
//			equipmentList.add(new PacketPlayOutEntityEquipment(holder.getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(equipment.legs)));
//		}
//
//		if(equipment.boots != null) {
//			equipmentList.add(new PacketPlayOutEntityEquipment(holder.getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(equipment.boots)));
//		}
//
//		if(equipment.hand != null) {
//			equipmentList.add(new PacketPlayOutEntityEquipment(holder.getEntityId(), EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(equipment.hand)));
//		}
//
//		UtilServer.broadcastPacket(holder.getWorld().getPlayers(), equipmentList.toArray(new Packet[equipmentList.size()]));
	}

	public Location update()
	{
		if (parent.getHolder() == null) return null;

		double yaw = Math.toRadians(offset + parent.getHolder().getLocation().getYaw()) + (Math.PI / 2);
		double x = parent.getHolder().getLocation().getX() + radius * Math.cos(yaw);
		double z = parent.getHolder().getLocation().getZ() + radius * Math.sin(yaw);
		double y = parent.getHolder().getLocation().getY() + yMod;
		Location loc = new Location(parent.getHolder().getLocation().getWorld(), x, y, z);
		loc.setYaw(parent.getHolder().getLocation().getYaw()+directionOffset);
		return loc;
	}

	public void teleport()
	{
		holder.teleport(update());

		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(((CraftEntity) holder).getHandle());
		UtilServer.broadcastPacket(packet);
	}

	public final void remove()
	{
		if(holder == null || holder.isDead()) return;
		holder.remove();
	}

	public CompanionOptions getOptions()
	{
		return options;
	}

	public CompanionEquipment getEquipment()
	{
		return equipment;
	}

	public float getRadius()
	{
		return radius;
	}

	public float getOffset()
	{
		return offset;
	}

	public ArmorStand getHolder()
	{
		return holder;
	}

	public Companion getParent()
	{
		return parent;
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}
}
