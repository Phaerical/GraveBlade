package com.phaerical.graveblade.entities;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.phaerical.graveblade.SoundManager;
import com.phaerical.graveblade.entities.Entity.EntityState;

public class EntityAction extends Action
{
	public enum ActionType
	{
		STAND, MOVE_LEFT, MOVE_RIGHT, JUMP, KNOCKBACK_LEFT, KNOCKBACK_RIGHT;
	}
	
	private Entity entity;
	private ActionType type;
	private float time;
	private float duration;
	private float speed;
	
	public EntityAction (ActionType type)
	{
		this.type = type;
		this.duration = -1;
		this.time = 0f;
	}
	
	public EntityAction (ActionType type, float duration)
	{
		this.type = type;
		this.duration = duration;
		this.time = 0f;
	}
	
	public ActionType getType ()
	{
		return type;
	}
	
	
	public boolean canPerformAction ()
	{
		if (type == ActionType.JUMP)
		{
			return (entity.getState() == EntityState.MOVING);
		}
		
		return true;
	}
	
	
	public void start ()
	{
		if (type == ActionType.JUMP)
		{
			SoundManager.play (SoundManager.JUMP);
			entity.setState (EntityState.JUMPING);
			speed = entity.getJumpSpeed () / 3;
		}
		else if (type == ActionType.KNOCKBACK_LEFT)
		{
			SoundManager.play (SoundManager.HIT);
			entity.setState (EntityState.HURT);
			speed = -entity.getSpeed () * 10;
		}
		else if (type == ActionType.KNOCKBACK_RIGHT)
		{
			SoundManager.play (SoundManager.HIT);
			entity.setState (EntityState.HURT);
			speed = entity.getSpeed () * 10;
		}
	}
	
	
	@Override
	public boolean act (float delta)
	{
		if (time == 0)
		{
			entity = ((Entity) getActor ());
			
			if (!canPerformAction ())
			{
				return true;
			}
			
			start ();
		}
		else if (time >= duration && duration != -1)
		{
			entity.setVelocityX (0);
			entity.setVelocityY (0);
			return true;
		}
		
		if (type == ActionType.STAND)
		{
			entity.setVelocityX (0);
		}
		else if (type == ActionType.MOVE_LEFT)
		{
			entity.setVelocityX (-entity.getSpeed ());
			entity.setFacingRight (false);
		}
		else if (type == ActionType.MOVE_RIGHT)
		{
			entity.setVelocityX (entity.getSpeed ());
			entity.setFacingRight (true);
		}
		else if (type == ActionType.JUMP)
		{
			if (speed <= 0.2f)
			{
				return true;
			}
			
			if (entity.velocity.y + speed > entity.getJumpSpeed ())
			{
				entity.setVelocityY (entity.getJumpSpeed ());
			}
			else
			{
				entity.setVelocityY (entity.velocity.y + speed);
			}
			
			speed *= 0.7f;
		}
		else if (type == ActionType.KNOCKBACK_LEFT || type == ActionType.KNOCKBACK_RIGHT)
		{
			if (Math.abs (speed) < 0.2f)
			{
				entity.setVelocityX (0);
				entity.setVelocityY (0);
				return true;
			}
			
			entity.velocity.x = speed;
			
			speed *= 0.5f;
		}
		
		time += delta;
		
		return false;
	}
}
