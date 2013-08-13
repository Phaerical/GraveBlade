package com.phaerical.graveblade.entities;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Entity
{
	private int exp;
	
	private ArtificialIntelligence ai;
	
	public Enemy (TiledMap map)
	{
		super (map);
		
		this.ai = new ArtificialIntelligence ();
	}
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		if (this.getActions().size == 0)
		{
			addAction (ai.getAction ());
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
