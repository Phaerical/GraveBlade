package com.phaerical.graveblade.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.screens.GameScreen;

public class Enemy extends Entity
{
	private int exp;
	
	public Enemy (TiledMap map)
	{
		super (map);
	}
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		if (this.getActions().size == 0)
		{
			double random = Math.random ();
			
			if (random < 0.005)
			{
				this.addAction (new EnemyAction (EnemyAction.Type.MOVE_LEFT, 2f));
			}
			else if (random < 0.01)
			{
				this.addAction (new EnemyAction (EnemyAction.Type.MOVE_RIGHT, 2f));
			}
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
