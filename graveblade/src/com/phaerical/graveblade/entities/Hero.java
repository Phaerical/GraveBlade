package com.phaerical.graveblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.phaerical.graveblade.EquipmentManager;
import com.phaerical.graveblade.EquipmentManager.Stat;
import com.phaerical.graveblade.ExperienceTable;
import com.phaerical.graveblade.Formula;
import com.phaerical.graveblade.Item;
import com.phaerical.graveblade.Item.ItemType;
import com.phaerical.graveblade.SoundManager;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.screens.GameScreen;

public class Hero extends Entity
{
	private float INVINCIBLE_DURATION = 1.5f;
	
	private final int STAT_POINTS_PER_LEVEL = 2;
	
	private int level;
	private int maxExp;
	private int exp;
	private int statPoints;
	
	private double critDamageMultiplier;
	
	private TextureAtlas atlas;
	
	//private Array<Item> equipment;
	private Array<Item> inventory;
	
	private EquipmentManager equipment;
	
	private float invincibleTime;
	
	public Hero (TiledMap map)
	{
		super (map);
		
		TextureAtlas itemAtlas = new TextureAtlas (Gdx.files.internal ("sprites/items.pack"));
		Item helmet = new Item ("Beginner Helmet", ItemType.HELM, itemAtlas.createSprite("helm1"), 1, 4, 4, 4, 4);
		Item armor = new Item ("Beginner Armor", ItemType.ARMOR, itemAtlas.createSprite("armor1"), 1, 4, 4, 4, 4);
		Item glove = new Item ("Beginner Gloves", ItemType.GLOVES, itemAtlas.createSprite("glove1"), 1, 4, 4, 4, 4);
		Item boots = new Item ("Beginner Boots", ItemType.BOOTS, itemAtlas.createSprite("boots1"), 1, 4, 4, 4, 4);
		Item weapon = new Item ("Beginner Sword", ItemType.WEAPON, itemAtlas.createSprite("sword1"), 1, 4, 4, 4, 4);
		Item misc = new Item ("Lucky Earrings", ItemType.MISC, itemAtlas.createSprite("earring1"), 1, 0, 0, 0, 10);
		
		this.equipment = new EquipmentManager (this);
		
		this.equipment.equip (helmet);
		this.equipment.equip (armor);
		this.equipment.equip (glove);
		this.equipment.equip (boots);
		this.equipment.equip (weapon);
		this.equipment.equip (misc);
		
		this.inventory = new Array<Item> (true, 24);
		this.inventory.add (new Item ("Beginner Helmet", ItemType.HELM, itemAtlas.createSprite("helm1"), 1, 4, 4, 4, 4));
		this.inventory.add (new Item ("Beginner Armor", ItemType.ARMOR, itemAtlas.createSprite("armor1"), 1, 4, 4, 4, 4));
		this.inventory.add (new Item ("Beginner Helmet", ItemType.HELM, itemAtlas.createSprite("helm1"), 1, 4, 4, 4, 4));
		this.inventory.add (new Item ("Beginner Sword", ItemType.WEAPON, itemAtlas.createSprite("sword1"), 1, 4, 4, 4, 4));
		this.inventory.add (new Item ("Beginner Sword", ItemType.WEAPON, itemAtlas.createSprite("sword1"), 1, 4, 4, 4, 4));
		
		this.setSpeed (8f);
		this.setJumpSpeed (15f);
		
		this.invincibleTime = 0;
		
		// BASE STATS
		this.setStrength (10);
		this.setVitality (10);
		this.setDexterity (10);
		this.setLuck (10);
		
		// HP
		this.setHealth (getMaxHealth ());
		this.setName ("hero");
		
		// CRIT
		this.critDamageMultiplier = 2;
		
		// LEVEL
		this.level =  1;
		this.exp = 0;
		this.maxExp = ExperienceTable.getExpRequired (level);
		this.statPoints = 0;
		
		// BOUNDS
		this.setWidth (80);
		this.setHeight (80);
		this.setX (0);
		this.setY (0);
		this.setBounds (getX (), getY (), getWidth (), getHeight ());
		this.setSpriteScale (2);
		
		// ANIMATIONS
		this.atlas = new TextureAtlas (Gdx.files.internal ("sprites/zero.pack"));
		this.setIdleAnimation (new Animation (0.3f, atlas.createSprites ("idle")));
		this.setRunAnimation (new Animation (0.1f, atlas.createSprites ("run")));
		this.setJumpAnimation (new Animation (0.15f, atlas.createSprites ("jump")));
		this.setAttackAnimation (new Animation (0.1f * getAttackSpeed () / 100, atlas.createSprites ("attack")));
		this.setHurtAnimation (new Animation (0.3f, atlas.createSprites ("hurt")));
		this.setDeathAnimation (new Animation (0.7f, atlas.createSprites ("hurt")));
	}
	
	public EquipmentManager getEquipment ()
	{
		return equipment;
	}
	
	public Array<Item> getInventory ()
	{
		return inventory;
	}
	
	public int getBonusStrength ()
	{
		return equipment.getBonusStats (Stat.STRENGTH);
	}
	
	public int getBonusVitality ()
	{
		return equipment.getBonusStats (Stat.VITALITY);
	}
	
	public int getBonusDexterity ()
	{
		return equipment.getBonusStats (Stat.DEXTERITY);
	}
	
	public int getBonusLuck ()
	{
		return equipment.getBonusStats (Stat.LUCK);
	}
	
	public int getTotalStrength ()
	{
		return getStrength () + getBonusStrength ();
	}
	
	public int getTotalVitality ()
	{
		return getVitality () + getBonusVitality ();
	}
	
	public int getTotalDexterity ()
	{
		return getDexterity () + getBonusDexterity ();
	}
	
	public int getTotalLuck ()
	{
		return getLuck () + getBonusLuck ();
	}
	
	public int getDamage ()
	{
		int dmg = getMinDamage () + (int) (Math.random () * (getMaxDamage () - getMinDamage() + 1));
		
		return dmg;
	}
	
	public int getMinDamage ()
	{
		return Formula.calculateMinDamage (getTotalStrength ());
	}
	
	public int getMaxDamage ()
	{
		return Formula.calculateMaxDamage (getTotalStrength ());
	}
	
	public int getAttackSpeed ()
	{
		return (int) (Formula.calculateAttackSpeed (getTotalDexterity ()) * 100);
	}
	
	public int getCritChance ()
	{
		return Formula.calculateCritChance (getTotalLuck ());
	}
	
	public int getCritDamage ()
	{
		return (int) Math.round (critDamageMultiplier * 100);
	}
	
	@Override
	public int getMaxHealth ()
	{
		return Formula.calculateHealth (getTotalVitality ());
	}
	
	public void increaseExp (int amount)
	{
		exp += amount;
		
		if (exp >= maxExp)
		{
			exp -= maxExp;
			levelUp ();
		}
	}
	
	public void levelUp ()
	{
		SoundManager.play (SoundManager.LEVEL_UP);
		GameScreen.ft.show ("LEVEL UP!", Color.GREEN, getX() - 60, getY() + getHeight() + 40);
		maxExp = ExperienceTable.getExpRequired (++level);
		setHealth (getMaxHealth ());
		increaseStatPoints (STAT_POINTS_PER_LEVEL);
	}
	
	public void increaseStatPoints (int amount)
	{
		statPoints += amount;
	}
	
	public void decreaseStatPoints (int amount)
	{
		statPoints = Math.max (0, statPoints - amount);
	}
	
	public int getStatPoints ()
	{
		return statPoints;
	}
	
	public int getLevel ()
	{
		return level;
	}
	
	public int getExp ()
	{
		return exp;
	}
	
	public int getMaxExp ()
	{
		return maxExp;
	}
	
	@Override
	public void attack ()
	{
		// Recalculate attack speed
		setAttackAnimation (new Animation (0.1f * 100 / getAttackSpeed (), atlas.createSprites ("attack")));
		super.attack ();
	}
	
	public Rectangle getAttackBounds ()
	{
		if (getState() == EntityState.ATTACKING && getAttackAnimation().getKeyFrameIndex (stateTime) == 2)
		{
			if (isFacingRight ())
			{
				return new Rectangle (getX() + getWidth(), getY(), 80, 80);
			}
			else
			{
				return new Rectangle (getX() - getWidth(), getY(), 80, 80);
			}
		}
		
		return null;
	}
	
	
	public void castFrostPillar ()
	{
		FrostPillar fp = new FrostPillar ();
		
		if (isFacingRight ())
		{
			fp.setPosition (getX()+100, getY());
		}
		else
		{
			fp.setPosition (getX()-100, getY());
		}
		
		getStage().addActor(fp);
	}
	
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		if (invincibleTime < 0)
		{
			invincibleTime = 0;
		}
		else
		{
			invincibleTime -= delta;
		}
	}
	
	
	@Override
	public void setSpriteColor ()
	{
		if (invincibleTime > 0)
		{
			for (int i = 0; i < 10; i += 2)
			{
				if (invincibleTime > INVINCIBLE_DURATION * i / 10 &&
					invincibleTime < INVINCIBLE_DURATION * (i + 1) / 10)
				{
					setColor (0.6f, 0.6f, 0.6f, 0.7f);
					break;
				}
				else
				{
					setColor (1, 1, 1, 0.7f);
				}
			}
		}
		
		super.setSpriteColor ();
	}
	
	@Override
	public void hurt (int damage)
	{
		if (getState() != EntityState.HURT && getState() != EntityState.DYING)
		{
			GameScreen.ft.show ("-" + damage, Color.RED, getX(), getY() + getHeight() + 10);
			setHealth (getHealth() - damage);
			addEntityAction (ActionType.KNOCKBACK_RIGHT);
			
			invincibleTime = INVINCIBLE_DURATION;
		}
	}
	
	
	public void setInvincible ()
	{
		invincibleTime = INVINCIBLE_DURATION;
	}
	
	public boolean isInvincible ()
	{
		return (invincibleTime != 0);
	}
	
	
	public void hitTarget (Enemy enemy, int damage)
	{
		if (enemy.getState() != EntityState.HURT)
		{
			GameScreen.ft.show (String.valueOf (damage), Color.WHITE, enemy.getX(), enemy.getY() + getHeight() + 10);
			
			enemy.setHealth (enemy.getHealth() - damage);
			
			if (getX() < enemy.getX())
			{
				enemy.addEntityAction (ActionType.KNOCKBACK_RIGHT);
			}
			else
			{
				enemy.addEntityAction (ActionType.KNOCKBACK_LEFT);
			}
			
			if (!enemy.isAlive ())
			{
				increaseExp (enemy.getExp ());
			}
		}
	}
	
	
	@Override
	protected void entityCollisionCheck ()
	{
		if (getAttackBounds() != null)
		{
			for (Actor a : getStage().getActors())
			{
				if (a.getClass().getSuperclass() == Enemy.class)
				{
					Enemy e = (Enemy) a;
					
					if (e.isAlive() && getAttackBounds().overlaps (e.getBounds()))
					{
						hitTarget (e, getDamage ());
					}
				}
			}
		}
	}
}
