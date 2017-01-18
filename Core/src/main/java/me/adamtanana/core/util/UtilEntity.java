package me.adamtanana.core.util;

import com.google.common.collect.Sets;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.List;

public class UtilEntity {

    public static void silence(org.bukkit.entity.Entity entity, boolean silence)
    {
        ((CraftEntity)entity).getHandle().b(true);
    }

    public static void setNoAI(org.bukkit.entity.Entity entity)
    {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.setInt("NoAI", 1);
        nmsEntity.f(tag);
    }

    public static void removeItemGravity(Item item)
    {
        net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) item).getHandle();
        NBTTagCompound tag = new NBTTagCompound();
        nmsEntity.c(tag);
        tag.setInt("NoGravity", 1);
        nmsEntity.f(tag);
    }

    public static void lookAtPlayerAI(org.bukkit.entity.Entity entity, float range)
    {
        if (((CraftEntity) entity).getHandle() instanceof EntityInsentient)
        {
            EntityInsentient ent = (EntityInsentient) ((CraftEntity) entity).getHandle();
            PathfinderGoalLookAtPlayer goal1 = new PathfinderGoalLookAtPlayer(ent, EntityHuman.class, range);
            PathfinderGoalRandomLookaround goal2 = new PathfinderGoalRandomLookaround(ent);
            Field goalSelector = null;
            try
            {
                goalSelector = EntityInsentient.class.getDeclaredField("goalSelector");
            } catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }

            if(goalSelector == null) return;
            goalSelector.setAccessible(true);

            try
            {
                ((PathfinderGoalSelector) goalSelector.get(ent)).a(7, goal1);
                ((PathfinderGoalSelector) goalSelector.get(ent)).a(8, goal2);
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static class EntityFollower {
        private static Field gsa;
        private static Field goalSelector;
        private static Field targetSelector;

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

        public static void follow(LivingEntity e, Location location, double speed) {
            try {
                Object nms_entity = ((CraftLivingEntity) e).getHandle();
                if (nms_entity instanceof EntityInsentient) {
                    PathfinderGoalSelector goal = (PathfinderGoalSelector) goalSelector.get(nms_entity);
                    PathfinderGoalSelector target = (PathfinderGoalSelector) targetSelector.get(nms_entity);
                    gsa.set(goal, new UnsafeList<>());
                    gsa.set(target, new UnsafeList<>());
                    goal.a(0, new PathfinderGoalFloat((EntityInsentient) nms_entity));
                    goal.a(1, new PathfinderGoalWalkToTile((EntityInsentient) nms_entity, location, speed));
                } else {
                    throw new IllegalArgumentException(e.getType().getName() + " is not an instance of an EntityInsentient.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static class PathfinderGoalWalkToTile extends PathfinderGoal {
            private EntityInsentient entity;
            private PathEntity path;
            private Location location;
            private double speed;

            public PathfinderGoalWalkToTile(EntityInsentient entityCreature, Location location, double speed) {
                this.entity = entityCreature;
                this.location = location;
                this.speed = speed;
            }

            @Override
            public boolean a() {
                if (location == null) {
                    return path != null;
                }

                boolean flag = this.entity.getNavigation().m();
                this.entity.getNavigation();
                this.path = this.entity.getNavigation().a(location.getX() + 1, location.getY() + 3, location.getZ() + 1);
                this.entity.getNavigation();
                if (this.path != null) {
                    this.c();
                } else {
                    Vector dir = location.toVector().subtract(this.entity.getBukkitEntity().getLocation().toVector()).normalize();
                    this.entity.getBukkitEntity().setVelocity(dir);
                }
                return this.path != null;
            }

            @Override
            public void c() {
                this.entity.getNavigation().a(this.path, 1.0D + speed);
            }
        }
    }
}
