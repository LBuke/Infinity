package com.infinity.hub.cosmetic.companions.type;

import com.infinity.hub.cosmetic.companions.companion.Companion;
import me.adamtanana.core.util.C;

/**
 * Created: 02/05/2016
 *
 * @author Teddeh
 * @version 1.0
 */
public enum CompanionType
{
	MC_8(
			C.gold + "Star" + C.white + "Bot " + C.reset + C.red + "$5",
			new String[]{"Description line1", "line2", "ect.."},
			1,
			BB8.class
	),

	DUCK(
			C.yellow + "Duck " + C.reset + C.red + "$5",
			new String[]{"Description line1", "line2", "ect.."},
			2,
			Duck.class
	),

	GORILLA(
			C.white + "Gorilla " + C.reset + C.red + "$15",
			new String[]{"Description line1", "line2", "ect.."},
			3,
			Gorilla.class
	),

	CHIMP(
			C.white + "Chimp " + C.reset + C.red + "$?",
			new String[]{"Description line1", "line2", "ect.."},
			4,
			Chimp.class
	),

	PUG(
			C.aqua + "Pug " + C.reset + C.red + "$?",
			new String[]{"Description line1", "line2", "ect.."},
			3,
			Gorilla.class
	),

	MINION(
			C.yellow + "Minion " + C.reset + C.red + "$?",
			new String[]{"Description line1", "line2", "ect.."},
			3,
			Gorilla.class
	),

	TURTLE(
			C.green + "Turtle " + C.reset + C.red + "$?",
			new String[]{"Description line1", "line2", "ect.."},
			3,
			Gorilla.class
	);

	private String name;
	private String[] description;
	private int id;
	private Class<? extends Companion> companionClass;

	CompanionType(String name, String[] description, int id, Class<? extends Companion> companionClass)
	{
		this.name = name;
		this.description = description;
		this.id = id;
		this.companionClass = companionClass;
	}

	public String getName()
	{
		return name;
	}

	public String[] getDescription()
	{
		return description;
	}

	public int getId()
	{
		return id;
	}

	public Class<? extends Companion> getCompanionClass()
	{
		return companionClass;
	}
}
