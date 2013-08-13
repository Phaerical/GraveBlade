package com.phaerical.graveblade.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.entities.EntityAction.ActionType;

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
	
	public void setExp (int amount)
	{
		exp = amount;
	}
	
	@Override
	protected void entityCollisionCheck ()
	{
		for (Actor a : getStage().getActors())
		{
			if (a.getClass() == Hero.class)
			{
				Hero h = ((Hero) a);
				Rectangle hit = h.getAttackBounds ();
				
				if (hit != null && hit.overlaps (getBounds ()))
				{
					int damage = h.getDamage();
					
					hurt (damage);
					
					if (!isAlive ())
					{
						h.increaseExp (exp);
					}
				}
			}
		}
	}
}
