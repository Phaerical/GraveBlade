package com.phaerical.graveblade.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.ExperienceTable;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.ScreenManager;
import com.phaerical.graveblade.SoundManager;
import com.phaerical.graveblade.screens.GameScreen;

public class Hero extends Entity
{
	private float JUMP_SPEED = 15f;
	private float INVINCIBLE_DURATION = 1.5f;
	
	private int level;
	private int maxExp;
	private int exp;
	
	private int damage;
	private int critChance;
	private double critDamageMultiplier;
	
	private GraveBlade game;
	
	private float invincibleTime;
	
	public Hero (GraveBlade game, TiledMap map)
	{
		super (map);
		
		this.game = game;
		
		this.setSpeed (8f);
		
		this.invincibleTime = 0;
		
		// HP
		this.setMaxHealth (100);
		this.setHealth (100);
		this.setName ("hero");
		
		this.damage = 8;
		this.critChance = 10;
		this.critDamageMultiplier = 2;
		
		this.level =  1;
		this.exp = 0;
		this.maxExp = ExperienceTable.getExpRequired (level);
		
		// BOUNDS
		this.setWidth (80);
		this.setHeight (80);
		this.setX (0);
		this.setY (0);
		this.setBounds (getX (), getY (), getWidth (), getHeight ());
		this.setSpriteScale (2);
		
		// ANIMATIONS
		TextureAtlas atlas = new TextureAtlas ("assets/sprites/zero.pack");
		this.setIdleAnimation (new Animation (0.3f, atlas.createSprites ("idle")));
		this.setRunAnimation (new Animation (0.1f, atlas.createSprites ("run")));
		this.setJumpAnimation (new Animation (0.15f, atlas.createSprites ("jump")));
		this.setAttackAnimation (new Animation (0.1f, atlas.createSprites ("attack")));
		this.setHurtAnimation (new Animation (0.7f, atlas.createSprites ("hurt")));
		this.setDeathAnimation (new Animation (0.7f, atlas.createSprites ("hurt")));
	}
	
	
	public int getDamage ()
	{
		int dmg = (int) (damage + Math.random () * 4);
		
		/*if (Math.random() * 100 < critChance)
		{
			dmg *= critDamageMultiplier;
		}*/
		
		return dmg;
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
	
	public void jump ()
	{
		if (!jumping && !falling && !hurt)
		{
			SoundManager.play (SoundManager.JUMP);
			velocity.y = JUMP_SPEED;
			jumping = true;
			stateTime = 0f;
		}
	}
	
	public void attack ()
	{
		if (!attacking && !hurt)
		{
			SoundManager.play (SoundManager.SWING);
			attacking = true;
			attackStateTime = 0f;
		}
	}
	
	public Rectangle getAttackBounds ()
	{
		if (attacking && getAttackAnimation().getKeyFrameIndex (attackStateTime) == 2)
		{
			if (facingRight)
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
	public void setSpriteColor (SpriteBatch batch)
	{
		if (!isAlive ())
		{
			batch.setColor (1, 0, 0, Math.max (0, 1 - stateTime / getDeathAnimation().animationDuration));
		}
		else if (hurt)
		{
			batch.setColor (Color.RED);
		}
		else if (invincibleTime > 0)
		{
			batch.setColor (Color.LIGHT_GRAY);
		}
		else
		{
			batch.setColor (Color.WHITE);
		}
	}
	
	
	@Override
	public void hurt (int damage)
	{
		super.hurt (damage);
		
		invincibleTime = INVINCIBLE_DURATION;
	}
	
	
	@Override
	protected void entityCollisionCheck ()
	{
		if (invincibleTime == 0)
		{
			for (Actor a : getStage().getActors())
			{
				if (a.getClass().getSuperclass() == Enemy.class)
				{
					Enemy e = (Enemy) a;
					
					if (e.isAlive () && getBounds().overlaps (e.getBounds()))
					{
						hurt (15);
						
						GameScreen.ft.show("-15", Color.RED, getX(), getY() + getHeight());
					}
				}
			}
		}
	}
}
