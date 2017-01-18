package com.infinity.hub.cosmetic.companions.companion.part;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEntityPart;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;

public class CompanionModuleAnimated extends CompanionPart
{
	public CompanionModuleAnimated(Companion parent, String name, int id, double yMod, float offset, float radius, float directionOffset, CompanionEquipment equipment, CompanionOptions options)
	{
		super(parent, name, id, yMod, offset, radius, directionOffset, equipment, options);
	}

	public void animate(CompanionEntityPart armorStand) {}
	public void moveAnimate(CompanionEntityPart armorStand) {}
	public void stillAnimate(CompanionEntityPart armorStand) {}
}
