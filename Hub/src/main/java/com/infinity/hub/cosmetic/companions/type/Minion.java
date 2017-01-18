package com.infinity.hub.cosmetic.companions.type;

import com.infinity.hub.cosmetic.companions.companion.Companion;
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

/**
 * Created: 08/05/2016
 *
 * @author Teddeh
 * @version 1.0
 */
public class Minion extends Companion
{
	public Minion(Player player)
	{
		super(player, EntityType.PIG, CompanionType.MINION);

		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NjI3MTAzNDc2NzQsInByb2ZpbGVJZCI6ImM0MmVlN2Y2MjMzNTQ1MjQ4ZTA3YWViMmY5MWU2ZGJiIiwicHJvZmlsZU5hbWUiOiJ4VGVkZGVoIiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE3YjlkOTdlZDVlNjNlNjNiMGRlYWEyY2Y5YTYwNzZmYWM3ZDczOTA3ZDlkZjk3ZGZmZTI2NWQyYjEifX19").build();
		ItemStack body = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NjI3MTAzODA2NTEsInByb2ZpbGVJZCI6ImM0MmVlN2Y2MjMzNTQ1MjQ4ZTA3YWViMmY5MWU2ZGJiIiwicHJvZmlsZU5hbWUiOiJ4VGVkZGVoIiwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VmZjU4MzU1ODMwYWZlYWYzYWU5N2IxYTc5NjljNmZlZTNjOGVhNjZkMTIyMzhhZmI3NjFlYWNkYzkxMTgifX19").build();
		ItemStack arm = new ItemBuilder(Material.GOLD_SWORD).build();

		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModuleAnimated(this, "head", 1, -0.79, 0F, 0F, 0F, new CompanionEquipment().setHelmet(head), new CompanionOptions(false, false, false, false))
			{
				int i = 0;
				boolean iB = false;
				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(350), 0, Math.toRadians(i))));
					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

			new CompanionModuleAnimated(this, "body", 2, -1.35, 0F, 0F, 0F, new CompanionEquipment().setHelmet(body), new CompanionOptions(false, false, false, false))
			{
				@Override
				public void animate(CompanionEntityPart armorStand)
				{

				}
			},

			new CompanionModuleAnimated(this, "arm_left", 3, -0.1D, -55F, 0.50F, 0F, new CompanionEquipment().setHand(arm), new CompanionOptions(true, false, false, true))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(50), 0, Math.toRadians(-20))));
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(50 + i), 0, Math.toRadians(-20))));
					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},
			new CompanionModuleAnimated(this, "arm_right", 4, -0.1D, 0F, 0.3F, 0F, new CompanionEquipment().setHand(arm), new CompanionOptions(true, false, false, true))
			{
				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(50), Math.toRadians(8), Math.toRadians(20))));
				}

				int i = 0;
				boolean iB = false;
				@Override
				public void stillAnimate(CompanionEntityPart armorStand)
				{
					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(50 + i), Math.toRadians(8), Math.toRadians(20))));
					if(i == 4 || i == -4) iB = !iB; i = i + (iB ? 1 : -1);
				}
			},

//			new CompanionModuleAnimated(this, "leg_right", 3, -0.4f, -85F, 0.39F, 0F, new CompanionEquipment().setHand(feet), new CompanionOptions(true, false, false, false)){
//
//				int i2 = 0;
//				boolean iB2 = false;
//				@Override
//				public void moveAnimate(CompanionEntityPart armorStand)
//				{
//					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
//					if(i2 == 4 || i2 == -4) iB2 = !iB2;
//					i2 = i2 + (iB2 ? 12 : -1);
//				}
//
//				int i = 0;
//				boolean iB = false;
//				@Override
//				public void stillAnimate(CompanionEntityPart armorStand)
//				{
//					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
//					if(i == 4 || i == -4) iB = !iB;
//					i = i + (iB ? 1 : -1);
//				}
//			},
//			new CompanionModuleAnimated(this, "leg_left", 4, -0.4F, 0F, 0.06F, 0F, new CompanionEquipment().setHand(feet), new CompanionOptions(true, false, false, false))
//			{
//				@Override
//				public void moveAnimate(CompanionEntityPart armorStand)
//				{
//					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
//					if(i == 4|| i == -4) iB = !iB;
//					i = i + (iB ? 1 : -1);
//				}
//
//				int i = 0;
//				boolean iB = false;
//				@Override
//				public void stillAnimate(CompanionEntityPart armorStand)
//				{
//					armorStand.setRightArmPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(-(30+i)), 0, 0)));
//					if(i == 4 || i == -4) iB = !iB;
//					i = i + (iB ? 1 : -1);
//				}
//			},
		};

		addCompanionParts(parts);
	}
}
