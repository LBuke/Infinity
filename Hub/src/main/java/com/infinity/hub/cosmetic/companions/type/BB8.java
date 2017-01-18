package com.infinity.hub.cosmetic.companions.type;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModule;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionModuleAnimated;
import com.infinity.hub.cosmetic.companions.companion.part.CompanionPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;
import me.adamtanana.core.util.builder.SkullBuilder;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

/**
 * Created: 29/04/2016
 *
 * @author Teddeh
 * @version 1.0
 */
public class BB8 extends Companion
{
	public BB8(Player player)
	{
		super(player, EntityType.PIG, CompanionType.MC_8);

		ItemStack head = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NTI4MDM4MTI2OTgsInByb2ZpbGVJZCI6IjYyMTQxMTlhODVlOTQzZDZhMjNmZDNhOTNjNjJjODdmIiwicHJvZmlsZU5hbWUiOiJBbGNhdHJheklUQSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jYTNkNGM1OTM4ZmU3ZjdkMmY3MjVkMDJkNjk0YzExMTFlNGQ5YzdlZjQ5Y2JkYmVjZWFhYzlmYmQ5ZiJ9fX0=").build();
		ItemStack body = new SkullBuilder().setCustomSkull("eyJ0aW1lc3RhbXAiOjE0NTI4MDM4NTE5NjksInByb2ZpbGVJZCI6IjYyMTQxMTlhODVlOTQzZDZhMjNmZDNhOTNjNjJjODdmIiwicHJvZmlsZU5hbWUiOiJBbGNhdHJheklUQSIsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iNDI4Mjk4OWU1MjY4YThjM2MwYmU3OTQ5ZWMyZGUxMzc3N2EzZDc2YTAyZjZmMjNiODJlODRkNDgwNGJlYSJ9fX0=").build();

		CompanionPart[] parts = new CompanionPart[]
		{
			new CompanionModule(this, "head", 1, -0.12, 0f, 0f, 0f, new CompanionEquipment().setHelmet(head), new CompanionOptions(true, false, false, false)),
			new CompanionModuleAnimated(this, "body", 2, -1.1, (float) -90, 0.3f, 90f, new CompanionEquipment().setHelmet(body), new CompanionOptions(false, false, false, false)) {
				int i = 0;

				@Override
				public void moveAnimate(CompanionEntityPart armorStand)
				{
					if(this.getParent().isStatue()) return;

					if(i >= 36) i=0;
					if(!isMoving())
					{
						if(i==1 || i==10 || i==19 || i==28) return;
					}

					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, Math.toRadians(i * 10))));
					i+=1;
				}

				@Override
				public void animate(CompanionEntityPart armorStand)
				{
					if(!this.getParent().isStatue()) return;

					if(i >= 36) i=0;
					armorStand.setHeadPose(armorStand.fromEulerAngle(new EulerAngle(Math.toRadians(90), 0, Math.toRadians(i * 10))));
					i+=1;
				}
			}
		};

		addCompanionParts(parts);
	}
}
