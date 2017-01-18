package com.infinity.hub.cosmetic.companions.companion.part.armorstand;

import org.bukkit.entity.ArmorStand;

public class CompanionOptions
{
	private final boolean small, gravity, visible, arms;

	public CompanionOptions(boolean small, boolean gravity, boolean visible, boolean arms)
	{
		this.small = small;
		this.gravity = gravity;
		this.visible = visible;
		this.arms = arms;
	}

	public void apply(ArmorStand armorStand)
	{
		armorStand.setSmall(small);
		armorStand.setGravity(gravity);
		armorStand.setVisible(visible);
		armorStand.setArms(arms);
	}
}
