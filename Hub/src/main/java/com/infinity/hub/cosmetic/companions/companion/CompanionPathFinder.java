package com.infinity.hub.cosmetic.companions.companion;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CompanionPathFinder extends PathfinderGoalLookAtPlayer
{
	private EntityPlayer player;

	public CompanionPathFinder(EntityInsentient entityInsentient, Player player, Class<? extends Entity> aClass, float v)
	{
		super(entityInsentient, aClass, v);
		this.player = ((CraftPlayer) player).getHandle();
		this.b = this.player;
	}

	@Override
	public boolean a()
	{
		this.b = player;

		if (this.a.bc().nextFloat() >= 0.02F)
		{
			return false;
		}
		else return this.b != null;
	}
}
