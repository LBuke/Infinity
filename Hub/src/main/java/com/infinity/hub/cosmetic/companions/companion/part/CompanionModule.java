package com.infinity.hub.cosmetic.companions.companion.part;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionEquipment;
import com.infinity.hub.cosmetic.companions.companion.part.armorstand.CompanionOptions;

public class CompanionModule extends CompanionPart
{
	public CompanionModule(Companion parent, String name, int id, double yMod, float offset, float radius, float directionOffset, CompanionEquipment equipment, CompanionOptions options)
	{
		super(parent, name, id, yMod, offset, radius, directionOffset, equipment, options);
	}
}
