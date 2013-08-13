package com.phaerical.graveblade.entities;

import com.phaerical.graveblade.entities.EntityAction.ActionType;

public class ArtificialIntelligence
{
	private Enemy enemy;
	
	public ArtificialIntelligence (Enemy enemy)
	{
		this.enemy = enemy;
	}
	
	public void performAction ()
	{
		double rand = Math.random ();
		
		if (rand < 0.1)
		{
			enemy.addEntityAction (ActionType.MOVE_LEFT, 2f);
		}
		else if (rand < 0.2)
		{
			enemy.addEntityAction (ActionType.MOVE_RIGHT, 2f);
		}
		
		enemy.addEntityAction (ActionType.STAND, 0.5f);
	}
}
