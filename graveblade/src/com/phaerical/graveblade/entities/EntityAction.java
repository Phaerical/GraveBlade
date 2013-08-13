package com.phaerical.graveblade.entities;

import com.badlogic.gdx.scenes.scene2d.Action;

public class EntityAction extends Action
{
	public enum ActionType
	{
		STAND, MOVE_LEFT, MOVE_RIGHT, KNOCKBACK;
	}
	
	private ActionType type;
	private float time;
	private float duration;
	
	private float speed;
	
	
	public EntityAction (ActionType type)
	{
		this.type = type;
		this.duration = -1;
		this.time = 0f;
		this.speed = 10f;
	}
	
	public EntityAction (ActionType type, float duration)
	{
		this.type = type;
		this.duration = duration;
		this.time = 0f;
		this.speed = 10f;
	}
	
	
	@Override
	public boolean act (float delta)
	{
		Entity e = ((Entity) getActor());
		
		if (time >= duration && duration != -1)
		{
			e.setVelocityX (0);
			return true;
		}
		
		if (type == ActionType.STAND)
		{
			e.setVelocityX (0);
		}
		else if (type == ActionType.MOVE_LEFT)
		{
			e.setVelocityX (-e.getSpeed ());
			e.setFacingRight (false);
		}
		else if (type == ActionType.MOVE_RIGHT)
		{
			e.setVelocityX (e.getSpeed ());
			e.setFacingRight (true);
		}
		else if (type == ActionType.KNOCKBACK)
		{
			if (speed < 0.2f)
			{
				System.out.println ("lel");
				return true;
			}
			else
			{
				e.velocity.x = speed;
				e.velocity.y = 2f;
				
				speed *= 0.5f;
			}
		}
		
		time += delta;
		
		return false;
	}
}
