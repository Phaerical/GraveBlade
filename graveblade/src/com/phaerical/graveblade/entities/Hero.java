package com.phaerical.graveblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.ExperienceTable;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.SoundManager;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.screens.GameScreen;

public class Hero extends Entity
{
	private float INVINCIBLE_DURATION = 1.5f;
	
	private final int STAT_POINTS_PER_LEVEL = 2 ;
	
	private int level;
	private int maxExp;
	private int exp;
	private int statPoints;
	
	private int critChance;
	private double critDamageMultiplier;
	
	private GraveBlade game;
	
	private float invincibleTime;
	
	public Hero (GraveBlade game, TiledMap map)
	{
		super (map);
		
		this.game = game;
		
		this.setSpeed (8f);
		this.setJumpSpeed (15f);
		
		this.invincibleTime = 0;
		
		// HP
		this.setMaxHealth (100);
		this.setHealth (100);
		this.setName ("hero");
		
		// BASE STATS
		this.setStrength (10);
		this.setVitality (10);
		this.setDexterity (10);
		this.setLuck (10);
		
		// CRIT
		this.critChance = 10;
		this.critDamageMultiplier = 2.05;
		
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
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("sprites/zero.pack"));
		this.setIdleAnimation (new Animation (0.3f, atlas.createSprites ("idle")));
		this.setRunAnimation (new Animation (0.1f, atlas.createSprites ("run")));
		this.setJumpAnimation (new Animation (0.15f, atlas.createSprites ("jump")));
		this.setAttackAnimation (new Animation (0.1f, atlas.createSprites ("attack")));
		this.setHurtAnimation (new Animation (0.3f, atlas.createSprites ("hurt")));
		this.setDeathAnimation (new Animation (0.7f, atlas.createSprites ("hurt")));
	}
	
	public int getDamage ()
	{
		int dmg = (int) (getStrength () + Math.random () * 4);
		
		/*if (Math.random() * 100 < critChance)
		{
			dmg *= critDamageMultiplier;
		}*/
		
		return dmg;
	}
	
	public int getMinDamage ()
	{
		return getStrength ();
	}
	
	public int getMaxDamage ()
	{
		return getStrength () + 4;
	}
	
	public int getCritChance ()
	{
		return critChance;
	}
	
	public int getCritDamage ()
	{
		return (int) Math.round (critDamageMultiplier * 100);
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
		
		/*
		if (invincibleTime == 0)
		{
			for (Actor a : getStage().getActors())
			{
				if (a.getClass().getSuperclass() == Enemy.class)
				{
					Enemy e = (Enemy) a;
					
					if (e.isAlive () && getBounds().overlaps (e.getBounds()))
					{
						hurt (5);
						
						break;
					}
				}
			}
		}*/
	}
}
