package com.phaerical.graveblade;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.screens.GameScreen;

public class Controller extends InputListener
{
	private Hero hero;
	private GameScreen screen;
	
	public Controller (Hero hero, GameScreen screen)
	{
		this.hero = hero;
		this.screen = screen;
	}
	
	
	@Override
	public boolean keyDown (InputEvent event, int keycode)
	{
		if (keycode == Keys.UP)
		{
			hero.addEntityAction (ActionType.JUMP);
		}
		
		if (keycode == Keys.LEFT)
		{
			hero.addEntityAction (ActionType.MOVE_LEFT);
		}
		
		if (keycode == Keys.RIGHT)
		{
			hero.addEntityAction (ActionType.MOVE_RIGHT);
		}
		
		if (keycode == Keys.C)
		{
			hero.castFrostPillar ();
		}
		
		if (keycode == Keys.X)
		{
			hero.attack ();
		}
		
		if (keycode == Keys.Z)
		{
			hero.increaseExp (5);
		}
		
		if (keycode == Keys.ESCAPE)
		{
			screen.state = (screen.state == GameScreen.State.PAUSED)
					? GameScreen.State.RUNNING : GameScreen.State.PAUSED; 
		}
		
		if (keycode == Keys.U)
		{
			screen.triggerStatsWindow ();
		}
		
		if (keycode == Keys.I)
		{
			screen.triggerEquipmentWindow ();
		}
		
		return true;
	}
	
	
	@Override
	public boolean keyUp (InputEvent event, int keycode)
	{
		if (keycode == Keys.LEFT)
		{
			hero.removeEntityAction (ActionType.MOVE_LEFT);
			hero.setVelocityX (0);
		}
		
		if (keycode == Keys.RIGHT)
		{
			hero.removeEntityAction (ActionType.MOVE_RIGHT);
			hero.setVelocityX (0);
		}
		
		if (keycode == Keys.UP)
		{
			hero.removeEntityAction (ActionType.JUMP);
		}
		
		return true;
	}
}
