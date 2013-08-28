package com.phaerical.graveblade;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Item extends Image
{
	public enum ItemType
	{
		HELM (0), ARMOR (1), GLOVES (2), BOOTS (3), WEAPON (4), MISC (5);
		
		private int index;
		
		private ItemType (int index)
		{
			this.index = index;
		}
		
		public int getIndex ()
		{
			return index;
		}
	}
	
	private String name;
	private ItemType type;
	private Sprite sprite;
	
	private int reqLevel;
	private int strength;
	private int vitality;
	private int dexterity;
	private int luck;
	
	public Item (String name, ItemType type, Sprite sprite, int reqLvl, int str, int vit, int dex, int lck)
	{
		this.name = name;
		this.type = type;
		this.sprite = sprite;
		this.reqLevel = reqLvl;
		this.strength = str;
		this.vitality = vit;
		this.dexterity = dex;
		this.luck = lck;
		
		this.setDrawable (new SpriteDrawable (sprite));
	}
	
	public String getName ()
	{
		return name;
	}
	
	public ItemType getType ()
	{
		return type;
	}
	
	public Sprite getSprite ()
	{
		return sprite;
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
	
	public String getTooltip ()
	{
		String tooltip = "";
		
		tooltip += name.toUpperCase() + "\n";
		tooltip += "LEVEL " + reqLevel + "\n\n";
		
		if (strength > 0)
			tooltip += "+" + strength + " STRENGTH\n";
		
		if (vitality > 0)
			tooltip += "+" + vitality + " VITALITY\n";
		
		if (dexterity > 0)
			tooltip += "+" + dexterity + " DEXTERITY\n";
		
		if (luck > 0)
			tooltip += "+" + luck + " LUCK";
		
		return tooltip;
	}
}
