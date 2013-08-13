package com.phaerical.graveblade;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.phaerical.graveblade.entities.EntityAction;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.screens.GameScreen;

public class Controller extends InputListener
{
	private Hero hero;
	private GameScreen screen;
	
	private EntityAction moveLeft = new EntityAction (ActionType.MOVE_LEFT);
	private EntityAction moveRight = new EntityAction (ActionType.MOVE_RIGHT);
	
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
			hero.jump ();
		}
		
		if (keycode == Keys.LEFT)
		{
			hero.addAction (moveLeft);
		}
		
		if (keycode == Keys.RIGHT)
		{
			hero.addAction (moveRight);
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
		
		return true;
	}
	
	
	@Override
	public boolean keyUp (InputEvent event, int keycode)
	{
		if (keycode == Keys.LEFT)
		{
			hero.removeAction (moveLeft);
			hero.setVelocityX (0);
		}
		
		if (keycode == Keys.RIGHT)
		{
			hero.removeAction (moveRight);
			hero.setVelocityX (0);
		}
		
		return true;
	}
}
