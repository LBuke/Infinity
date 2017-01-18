package com.infinity.hub.cosmetic.companions.companion;

import com.google.common.collect.Sets;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.LivingEntity;

import java.lang.reflect.Field;
import java.util.UUID;

public class CompanionFollower
{
	private static Field gsa;
	private static Field goalSelector;
	private static Field targetSelector;
	private static boolean spawn;

	static {
		try {
			gsa = PathfinderGoalSelector.class.getDeclaredField("b");
			gsa.setAccessible(true);
			goalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
			goalSelector.setAccessible(true);
			targetSelector = EntityInsentient.class.getDeclaredField("targetSelector");
			targetSelector.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void follow(LivingEntity e, UUID toFollow, double speed, boolean spawn) {
		try {
			Object nms_entity = ((CraftLivingEntity) e).getHandle();
			if (nms_entity instanceof EntityInsentient) {
				PathfinderGoalSelector goal = (PathfinderGoalSelector) goalSelector.get(nms_entity);
				PathfinderGoalSelector target = (PathfinderGoalSelector) targetSelector.get(nms_entity);
				gsa.set(goal, new UnsafeList<>());
				gsa.set(target, new UnsafeList<>());
				goal.a(0, new PathfinderGoalFloat((EntityInsentient) nms_entity));
				goal.a(1, new PathfinderGoalWalkToTile((EntityInsentient) nms_entity, toFollow, speed, spawn));
			} else {
				throw new IllegalArgumentException(e.getType().getName() + " is not an instance of an EntityInsentient.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static class PathfinderGoalWalkToTile extends PathfinderGoal
	{
		private EntityInsentient entity;
		private PathEntity path;
		private UUID p;
		private double speed;
		private boolean spawn;

		public PathfinderGoalWalkToTile(EntityInsentient entityCreature, UUID p, double speed, boolean spawn) {
			this.entity = entityCreature;
			this.p = p;
			this.speed = speed;
			this.spawn = spawn;
		}

		@Override
		public boolean a()
		{
			if (Bukkit.getPlayer(p) == null) {
				return path != null;
			}
			if(!spawn)
			{
				if (entity.h(((CraftPlayer) Bukkit.getPlayer(p)).getHandle()) < 4d * 4d)
				{
					return false;
				}
			}
			Location targetLocation = Bukkit.getPlayer(p).getLocation();
			boolean flag = this.entity.getNavigation().m();
			this.entity.getNavigation();
			this.path = this.entity.getNavigation().a(targetLocation.getX() + 1, targetLocation.getY() + 3, targetLocation.getZ() + 1);
			this.entity.getNavigation();
			if (this.path != null) {
				this.c();
			}
			return this.path != null;
		}

		@Override
		public void c() {
			this.entity.getNavigation().a(this.path, 1.0D+speed);
		}
	}
}
