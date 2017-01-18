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

public class Chimp extends Companion
{
	public Chimp(Player player)
	{
		super(player, EntityType.PIG, CompanionType.CHIMP);

		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQzOTI4ODFhMTNhNDJiMDhhZWI4ODhmMzk5ZTFkZWJkYWNlMDRiYzVhMGUwMWMyMjFjMGI1ZDExZDQwIn19fQ==").build();
		ItemStack bodyTop = new ArmorBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setColor(Color.fromRGB(153, 86, 50)).build();
		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.68D, 0F, 0F, 0F, new CompanionEquipment().setHelmet(head).setChestplate(bodyTop), new CompanionOptions(false, false, false, true)){
				int i = 0, x = 0;
				boolean iB = true, xB = false;
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
			new CompanionModuleAnimated(this, "tail", 4, -1.05, 135F, 1.3F, 180F, new CompanionEquipment().setHand(new ItemStack(Material.LEASH)), new CompanionOptions(false, false, false, true)){

				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(70), Math.toRadians(90))));
				}
			},
		};

		addCompanionParts(parts);
	}
}
