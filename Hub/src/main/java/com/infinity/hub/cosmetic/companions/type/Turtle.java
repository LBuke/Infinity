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

public class Turtle extends Companion
{
	public Turtle(Player player)
	{
		super(player, EntityType.PIG, CompanionType.TURTLE);

		ItemStack chest = new ArmorBuilder(new ItemStack(Material.LEATHER_CHESTPLATE)).setColor(Color.fromRGB(58, 152, 52)).build();
		ItemStack shell = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NjI2ODg1ODk0ODAsInByb2ZpbGVJZCI6ImM0MmVlN2Y2MjMzNTQ1MjQ4ZTA3YWViMmY5MWU2ZGJiIiwicHJvZmlsZU5hbWUiOiJ4VGVkZGVoIiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzVhOTZlYjU5Yzg2MmUxZjNjOTFlYjk3YzQ3MTUwZGQ0NDgzOWZiMDM0NTNiYWNmMWVlMzU1ODQzZGYyMTg1In19fQ==").build();
		ItemStack head  = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NjI3NTU2Mzc4MTIsInByb2ZpbGVJZCI6IjAzMWM3YjNlMmQ5MDQ4Nzg4NTdlOTAzZjNiNzViMTYzIiwicHJvZmlsZU5hbWUiOiJhbGV4X21hcmtleSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mNTllY2IxY2U0NzdjZWE0MTk2ZmUzZDBmYTM3ZTdjZDYyY2UxZjZjMmRiM2Q1YjRkMTFiZmI5ZDhmYWFiNjcifSwiQ0FQRSI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzIyYjljNWVhNzYzYzg2ZmM1Y2FlYTMzZDgyYjBmYTY1YTdjMjI4ZmQzMjFiYTU0NzY2ZWE5NWEzZDBiOTc5MyJ9fX0=").build();

		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.38D, 0F, 0.12F, 0F, new CompanionEquipment().setHelmet(head), new CompanionOptions(true, false, false, true))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-12), Math.toRadians(i), 0)));

					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "body_front", 2, -1.1D, 0F, -0.2F, 0F, new CompanionEquipment().setChestplate(chest), new CompanionOptions(false, false, false, true))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, 0)));

					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(i), 0, Math.toRadians(50))));
					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-i), 0, Math.toRadians(310))));

					if(i == 10 || i == -10) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "body_back", 3, -1.1D, 180F, 0.79F, 180F, new CompanionEquipment().setChestplate(chest), new CompanionOptions(false, false, false, true))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setBodyPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, 0)));

					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(i), 0, Math.toRadians(50))));
					armorStand.setLeftArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-i), 0, Math.toRadians(310))));

					if(i == 10 || i == -10) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "shell", 4, -1.1D, 180F, 0.44F, 0F, new CompanionEquipment().setHelmet(shell), new CompanionOptions(false, false, false, false))
			{
				int i = 0;
				boolean iB = true;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(0, Math.toRadians(i), 0)));

					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			}
		};

		addCompanionParts(parts);
	}
}
