package com.infinity.hub.cosmetic.companions.type;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModuleAnimated;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;
import me.adamtanana.core.util.builder.ArmorBuilder;
import me.adamtanana.core.util.builder.SkullBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Gorilla extends Companion
{
	public Gorilla(Player player)
	{
		super(player, EntityType.PIG, CompanionType.GORILLA);

		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU5N2E5N2M1NmQzN2EzZjQzNGE1MjE2MmFjM2FkZDQ2MzA2YzYzNTcxMDIyNDg5MGU2OGJjN2FiZDg2YTY2In19fQ==").build();
		ItemStack bodyTop = new ArmorBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setColor(Color.fromRGB(90, 90, 90)).build();
		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.68D, 0F, 0F, 0F, new CompanionEquipment().setHelmet(head).setChestplate(bodyTop), new CompanionOptions(false, false, false, true)){
				int i = 0, x = 0;
				boolean iB = false, xB = false;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(55), 0, 0)));

					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(x), 0, 0)));
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-x), 0, 0)));
					if(x == 7 || x == -7) xB = !xB; x = x + (xB ? 1 : -1);

					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(0, 0, Math.toRadians(i))));
					if(i == 6 || i == -6) iB = !iB;
					i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "body_mid", 2, -1.1D, 0F, 0F, 0F, new CompanionEquipment().setChestplate(bodyTop), new CompanionOptions(false, false, false, true)){
				int x = 0;
				boolean xB = false;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, 0)));

					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(x), 0, 0)));
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-x), 0, 0)));
					if(x == 7 || x == -7) xB = !xB; x = x + (xB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "body_bottom", 3, -1.25D, 180F, 1F, 180F, new CompanionEquipment().setChestplate(bodyTop), new CompanionOptions(false, false, false, true)){
				int x = 0;
				boolean xB = false;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(245), Math.toRadians(180), 0)));

					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-15 + x), 0, 0)));
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-15 + -x), 0, 0)));
					if(x == 4 || x == -4) xB = !xB; x = x + (xB ? 1 : -1);
				}
			},
		};

		addCompanionParts(parts);
	}
}
