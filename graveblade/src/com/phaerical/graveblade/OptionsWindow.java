package com.phaerical.graveblade;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.phaerical.graveblade.screens.GameScreen;

public class OptionsWindow extends Window
{
	private boolean open;
	
	private GameScreen game;
	
	public OptionsWindow (GameScreen g)
	{
		super ("OPTIONS", Assets.skin);
		
		this.game = g;
		
		open = false;
		
		//*************************************
		// WINDOW PROPERTIES
		//*************************************
		setMovable (false);
		setKeepWithinStage (false);
		setSize (820, 390);
		setPosition (100, GameScreen.HEIGHT + getHeight ());
		padTop (70);
		padLeft (35);
		padRight (35);
		padBottom (25);
		left ();
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
}
