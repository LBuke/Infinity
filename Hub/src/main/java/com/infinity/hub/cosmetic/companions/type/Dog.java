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

public class Dog extends Companion
{
	public Dog(Player player)
	{
		super(player, EntityType.PIG, CompanionType.PUG);

		ItemStack chest = new ArmorBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setColor(Color.fromRGB(233, 224, 221)).build();
		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiNGY4NGUxOWI1MmYzMTIxNzcxMmU3YmE5ZjUxZDU2ZGE1OWQyNDQ1YjRkN2YzOWVmNmMzMjNiODE2NiJ9fX0=").build();
		ItemStack tail = new SkullBuilder().setCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFjNDVhNTk1NTAxNDNhNDRlZDRlODdjZTI5NTVlNGExM2U5NGNkZmQ0YzY0ZGVlODgxZGZiNDhkZDkyZSJ9fX0=").build();
		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.5, 0F, 0F, 0F, new CompanionEquipment().setHelmet(head).setChestplate(chest), new CompanionOptions(true, false, false, true))
			{
				int i = 0, x = 0;
				boolean iB = false, xB = false;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(x), 0, 0)));
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-x), 0, 0)));

					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, 0)));
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-15 + i), 0, 0)));

					if(x == 6 || x == -6) xB = !xB; x = x + (xB ? 1 : -1);
					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "body", 1, -0.5, 181F, 0.55F, 180F, new CompanionEquipment().setChestplate(chest), new CompanionOptions(true, false, false, true))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(i), 0, 0)));
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-i), 0, 0)));

					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, 0)));

					if(i == 6 || i == -6) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "tail", 1, -0.08, 198F, 0.60F, 0F, new CompanionEquipment().setHand(tail), new CompanionOptions(true, false, false, true))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(i), 0)));
					if(i == 3 || i == -3) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},
		};

		addCompanionParts(parts);
	}
}
