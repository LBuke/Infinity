package com.infinity.hub.cosmetic.companions.companion.part.armorstand;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class CompanionEquipment
{
	public ItemStack head, chest, legs, boots, hand;

	public CompanionEquipment() {}

	/** INSERT */
	public CompanionEquipment setHelmet(ItemStack itemStack)
	{
		head = itemStack;
		return this;
	}

	public CompanionEquipment setChestplate(ItemStack itemStack)
	{
		chest = itemStack;
		return this;
	}

	public CompanionEquipment setLeggings(ItemStack itemStack)
	{
		legs = itemStack;
		return this;
	}

	public CompanionEquipment setBoots(ItemStack itemStack)
	{
		boots = itemStack;
		return this;
	}

	public CompanionEquipment setHand(ItemStack itemStack)
	{
		hand = itemStack;
		return this;
	}

	/** REMOVE */
	public CompanionEquipment removeHelmet(ArmorStand armorStand)
	{
		armorStand.getEquipment().setHelmet(new ItemStack(Material.AIR));
		return this;
	}

	public CompanionEquipment removeChestplate(ArmorStand armorStand)
	{
		armorStand.getEquipment().setChestplate(new ItemStack(Material.AIR));
		return this;
	}

	public CompanionEquipment removeLeggings(ArmorStand armorStand)
	{
		armorStand.getEquipment().setLeggings(new ItemStack(Material.AIR));
		return this;
	}

	public CompanionEquipment removeBoots(ArmorStand armorStand)
	{
		armorStand.getEquipment().setBoots(new ItemStack(Material.AIR));
		return this;
	}

	public CompanionEquipment removeHand(ArmorStand armorStand)
	{
		armorStand.getEquipment().setItemInHand(new ItemStack(Material.AIR));
		return this;
	}

	public void apply(ArmorStand armorStand)
	{
		if(head != null)
		{
			armorStand.getEquipment().setHelmet(head);
		}

		if(chest != null)
		{
			armorStand.getEquipment().setChestplate(chest);
		}

		if(legs != null)
		{
			armorStand.getEquipment().setLeggings(legs);
		}

		if(boots != null)
		{
			armorStand.getEquipment().setBoots(boots);
		}

		if(hand != null)
		{
			armorStand.getEquipment().setItemInHand(hand);
		}
	}
}
