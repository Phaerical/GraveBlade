package com.phaerical.graveblade.entities;

import com.phaerical.graveblade.entities.EntityAction.ActionType;

public class ArtificialIntelligence
{
	public EntityAction getAction ()
	{
		double rand = Math.random ();
		
		if (rand < 0.1)
		{
			return new EntityAction (ActionType.MOVE_LEFT, 2f);
		}
		else if (rand < 0.2)
		{
			return new EntityAction (ActionType.MOVE_RIGHT, 2f);
		}
		
		return new EntityAction (ActionType.STAND, 0.5f);
	}
}
