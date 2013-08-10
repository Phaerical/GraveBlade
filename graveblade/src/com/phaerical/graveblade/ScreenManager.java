package com.phaerical.graveblade;

import com.phaerical.graveblade.screens.GameOverScreen;
import com.phaerical.graveblade.screens.MenuScreen;

public class ScreenManager
{
	private GraveBlade game;
	
	public ScreenManager (GraveBlade game)
	{
		this.game = game;
	}
	
	public void setScreen (int screen)
	{
		if (screen == 1)
		{
			game.setScreen (new MenuScreen (game));
		}
		else if (screen == 2)
		{
			game.setScreen (new GameOverScreen (game));
		}
	}
}
