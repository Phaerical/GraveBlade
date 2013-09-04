package com.phaerical.graveblade.ui;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.phaerical.graveblade.Assets;
import com.phaerical.graveblade.screens.GameScreen;

public class BasicWindow extends Window
{
	private boolean open;
	
	private ImageButton btnClose;
	
	public BasicWindow (GameScreen game, String name)
	{
		super (name, Assets.skin);
		
		open = false;
		
		//*************************************
		// WINDOW PROPERTIES
		//*************************************
		setMovable (false);
		setKeepWithinStage (false);
		setSize (820, 390);
		setPosition (100, GameScreen.HEIGHT + getHeight ());
		padTop (50);
		padLeft (35);
		padRight (35);
		padBottom (25);
		
		btnClose = new ImageButton (Assets.skin, "close");
		
		btnClose.addListener (new InputListener ()
		{
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				if (button == Buttons.LEFT)
				{
					hide ();
				}
				
				return true;
			}
		});
		
		game.getUI().addActor (btnClose);
	}
	
	public void show ()
	{
		addAction (Actions.moveTo (100, 80, 0.5f, Interpolation.fade));
		open = true;
	}
	
	public void hide ()
	{
		addAction (Actions.moveTo (100, GameScreen.HEIGHT + getHeight (), 0.5f, Interpolation.fade));
		open = false;
	}
	
	public boolean isOpen ()
	{
		return open;
	}
	
	@Override
	public void act (float delta)
	{
		super.act(delta);
		
		// Update close button position as window moves
		btnClose.setPosition (getRight() - 50, getTop() - 50);
		btnClose.toFront ();
	}
}
