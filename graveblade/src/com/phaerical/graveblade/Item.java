package com.phaerical.graveblade;

public class Item
{
	public enum ItemType
	{
		HELMET, ARMOR, GLOVE, BOOTS, WEAPON
	}
	
	private String name;
	private ItemType type;
	
	private int reqLevel;
	private int strength;
	private int vitality;
	private int dexterity;
	private int luck;
	
	public Item (String name, ItemType type, int reqLvl, int str, int vit, int dex, int lck)
	{
		this.name = name;
		this.type = type;
		this.strength = str;
		this.vitality = vit;
		this.dexterity = dex;
		this.luck = lck;
	}
	
	public String getName ()
	{
		return name;
	}
	
	public ItemType getType ()
	{
		return type;
	}
	
	public int getRequiredLevel ()
	{
		return reqLevel;
	}
	
	public int getStrength ()
	{
		return strength;
	}
	
	public int getVitality ()
	{
		return vitality;
	}
	
	public int getDexterity ()
	{
		return dexterity;
	}
	
	public int getLuck ()
	{
		return luck;
	}
}
