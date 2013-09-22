package com.phaerical.graveblade.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.phaerical.graveblade.Assets;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.entities.EntityAction.ActionType;
import com.phaerical.graveblade.screens.GameScreen;

public class ControlButtons extends Table
{
	private GameScreen game;
	private Hero hero;
	
	public ControlButtons (GameScreen g)
	{
		this.game = g;
		this.hero = game.getHero ();
		
		ImageButton btnLeft = new ImageButton (Assets.skin, "left");
		ImageButton btnRight = new ImageButton (Assets.skin, "right");
		ImageButton btnJump = new ImageButton (Assets.skin, "jump");
		ImageButton btnAttack = new ImageButton (Assets.skin, "attack");
		
		add (btnLeft).space (20);
		add (btnRight).space (20);
		add (btnJump).expandX().space (10).align (Align.right);
		add (btnAttack).space (20);
		
		btnLeft.addListener (new InputListener ()
		{
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.addEntityAction (ActionType.MOVE_LEFT);
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.removeEntityAction (ActionType.MOVE_LEFT);
				hero.setVelocityX (0);
			}
		});
		
		btnRight.addListener (new InputListener ()
		{
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.addEntityAction (ActionType.MOVE_RIGHT);
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.removeEntityAction (ActionType.MOVE_RIGHT);
				hero.setVelocityX (0);
			}
		});
		
		btnJump.addListener (new InputListener ()
		{
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.addEntityAction (ActionType.JUMP);
				return true;
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.removeEntityAction (ActionType.JUMP);
			}
		});
		
		btnAttack.addListener (new InputListener ()
		{
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				hero.attack ();
				return true;
			}
		});
		
		setFillParent (true);
		pad (50);
		padBottom (70);
		bottom ();
	}
}
