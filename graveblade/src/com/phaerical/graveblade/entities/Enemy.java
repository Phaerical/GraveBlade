package com.phaerical.graveblade.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.entities.Entity.EntityState;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.screens.GameScreen;

public class Enemy extends Entity
{
	private int exp;
	
	private ArtificialIntelligence ai;
	
	public Enemy (TiledMap map)
	{
		super (map);
		
		this.ai = new ArtificialIntelligence (this);
	}
	
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		if (getActions().size == 0)
		{
			ai.performAction ();
		}
	}
	
	
	public int getExp ()
	{
		return exp;
	}
	
	public void setExp (int amount)
	{
		exp = amount;
	}
	
	public void hitTarget (Hero hero, int damage)
	{
		if (hero.getState() != EntityState.HURT && !hero.isInvincible ())
		{
			GameScreen.ft.show (String.valueOf (damage), Color.RED, hero.getX(), hero.getY() + getHeight() + 10);
			
			hero.setHealth (hero.getHealth() - damage);
			
			if (getX() < hero.getX())
			{
				hero.addEntityAction (ActionType.KNOCKBACK_RIGHT);
			}
			else
			{
				hero.addEntityAction (ActionType.KNOCKBACK_LEFT);
			}
			
			hero.setInvincible ();
		}
	}
	
	
	@Override
	protected void entityCollisionCheck ()
	{
		for (Actor a : getStage().getActors())
		{
			if (a.getClass() == Hero.class)
			{
				Hero h = (Hero) a;
				
				if (h.isAlive() && getBounds().overlaps (h.getBounds ()))
				{
					hitTarget (h, 5);
				}
			}
		}
	}
}
