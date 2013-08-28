package com.phaerical.graveblade;

import com.badlogic.gdx.utils.Array;
import com.phaerical.graveblade.Item.ItemType;
import com.phaerical.graveblade.entities.Hero;

public class EquipmentManager
{
	public enum Stat
	{
		STRENGTH, VITALITY, DEXTERITY, LUCK;
	}
	
	private Array<Item> equips;
	private Hero hero;
	
	public EquipmentManager (Hero hero)
	{
		this.hero = hero;
		
		this.equips = new Array<Item> (true, 6);
		
		for (ItemType it : ItemType.values ())
		{
			this.equips.insert (it.getIndex(), null);
		}
	}
	
	public void equip (Item item)
	{
		equips.set (item.getType().getIndex(), item);
	}
	
	public void removeEquip (Item item)
	{
		equips.set (item.getType().getIndex(), null);
		hero.getInventory().add (item);
	}
	
	public void equipFromInventory (Item item)
	{
		if (isSlotEmpty (item.getType().getIndex ()))
		{
			hero.getInventory().removeValue (item, false);
			equips.set (item.getType().getIndex(), item);
		}
	}
	
	public boolean isSlotEmpty (int slot)
	{
		return (equips.get (slot) == null);
	}
	
	public Item getEquip (int slot)
	{
		return equips.get (slot);
	}
	
	public int getBonusStats (Stat stat)
	{
		int amount = 0;
		
		for (int i = 0; i < 6; i++)
		{
			if (equips.get(i) != null)
			{
				switch (stat)
				{
				case STRENGTH:
					amount += equips.get(i).getStrength ();
					break;
				case VITALITY:
					amount += equips.get(i).getVitality ();
					break;
				case DEXTERITY:
					amount += equips.get(i).getDexterity ();
					break;
				case LUCK:
					amount += equips.get(i).getLuck ();
					break;
				}
			}
		}
		
		return amount;
	}
}
