package com.infinity.hub.cosmetic.companions.type;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModule;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModuleAnimated;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;
import me.adamtanana.core.util.builder.ItemBuilder;
import me.adamtanana.core.util.builder.SkullBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Duck extends Companion
{
	public Duck(Player player)
	{
		super(player, EntityType.PIG, CompanionType.DUCK);

		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTMzMmUxYmM3ZmNkODZiOGVmNjI0ZDhlMmU5MGY4ZWE2Yzk4ZDZiZWZmMWY3MjMxZThjMTRmZGM4ZDdmMCJ9fX0=").build();
		ItemStack wing = new ItemBuilder(Material.INK_SACK, (byte)11).build();
		ItemStack feet = new ItemBuilder(Material.STONE_SLAB2).build();

		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.3D, -3F, 0.24F, 0F, new CompanionEquipment().setHelmet(head), new CompanionOptions(true, false, false, false))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(350), 0, 0)));
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(350), 0, Math.toRadians(i))));
					if(i == 6 || i == -6) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModule(this, "body", 2, -0.6D, 0F, 0F, 0F, new CompanionEquipment().setHelmet(new ItemStack(Material.GOLD_BLOCK)), new CompanionOptions(true, false, false, false)),
			new CompanionModuleAnimated(this, "wing_left", 3, -1.35D, -90F, 1.13F, 0F, new CompanionEquipment().setHand(wing), new CompanionOptions(false, false, false, false))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(90), Math.toRadians(90))));
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(90+i), Math.toRadians(90))));
					if(i == 4 || i == -4) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "wing_right", 4, -1.35D, -90F, 0.63F, 0F, new CompanionEquipment().setHand(wing), new CompanionOptions(false, false, false, false))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(90), Math.toRadians(90))));
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(90+i), Math.toRadians(90))));
					if(i == 4 || i == -4) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "leg_right", 5, -0.4f, -85F, 0.39F, 0F, new CompanionEquipment().setHand(feet), new CompanionOptions(true, false, false, false)){

				int i2 = 0;
				boolean iB2 = false;@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
					if(i2 == 4 || i2 == -4) iB2 = !iB2;
					i2 = i2 + (iB2 ? 12 : -1);
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
					if(i == 4 || i == -4) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "leg_left", 6, -0.4F, 0F, 0.06F, 0F, new CompanionEquipment().setHand(feet), new CompanionOptions(true, false, false, false))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
					if(i == 4|| i == -4) iB = !iB;
					i = i + (iB ? 1 : -1);
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
					if(i == 4 || i == -4) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
		};

		addCompanionParts(parts);
	}
}
