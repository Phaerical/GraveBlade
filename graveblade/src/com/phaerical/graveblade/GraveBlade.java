package com.phaerical.graveblade;

import com.badlogic.gdx.Game;
import com.phaerical.graveblade.screens.GameScreen;
import com.phaerical.graveblade.screens.SplashScreen;

public class GraveBlade extends Game
{
	private ScreenManager screenManager;
	
	@Override
	public void create()
	{
		SoundManager.load ();
		screenManager = new ScreenManager (this);
		
		//setScreen (new SplashScreen (this));
		setScreen (new GameScreen (this));
	}
	
	
	public ScreenManager getScreenManager ()
	{
		return screenManager;
	}
}