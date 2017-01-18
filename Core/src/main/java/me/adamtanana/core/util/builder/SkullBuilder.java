package me.adamtanana.core.util.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class SkullBuilder
{
	private ItemStack itemStack;
	private SkullMeta itemMeta;

	public SkullBuilder()
	{
		itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		itemMeta = (SkullMeta) itemStack.getItemMeta();
	}

	public SkullBuilder setName(String name)
	{
		itemMeta.setDisplayName(name);
		return this;
	}

	public SkullBuilder setOwner(String name)
	{
		itemMeta.setOwner(name);
		return this;
	}

	public SkullBuilder setLore(String... lore)
	{
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public SkullBuilder setAmount(int amount)
	{
		itemStack.setAmount(amount);
		return this;
	}

	public SkullBuilder setCustomSkull(String urlString)
	{
		if (urlString == null || urlString.equals(""))
			return this;

		UUID uid = UUID.randomUUID();
		GameProfile profile = new GameProfile(uid,uid.toString().substring(0,10).replace("-",""));
		profile.getProperties().put("textures", new Property("textures", urlString));
		Field field;
		try
		{
			field = itemMeta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(itemMeta, profile);
		}
		catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return this;
	}

	public ItemStack build()
	{
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}
