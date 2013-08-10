package com.phaerical.graveblade.entities;

import com.badlogic.gdx.scenes.scene2d.Action;

public class EnemyAction extends Action
{
	enum Type
	{
		MOVE_LEFT, MOVE_RIGHT;
	}
	
	private Type type;
	private float time;
	private float duration;
	
	
	public EnemyAction (Type type, float duration)
	{
		this.type = type;
		this.duration = duration;
		this.time = 0f;
	}
	
	
	@Override
	public boolean act (float delta)
	{
		if (type == Type.MOVE_LEFT)
		{
			((Entity) getActor()).moveLeft (true);
		}
		else if (type == Type.MOVE_RIGHT)
		{
			((Entity) getActor()).moveRight (true);
		}
		
		if (time >= duration)
		{
			((Entity) getActor()).moveLeft (false);
			((Entity) getActor()).moveRight (false);
			return true;
		}
		
		time += delta;
		
		return false;
	}
}
