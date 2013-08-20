package com.phaerical.graveblade.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.phaerical.graveblade.GraveBlade;

public class SplashScreen extends BasicScreen
{
	private Texture splash;
	private GraveBlade game;
	
	
	public SplashScreen (GraveBlade game)
	{
		super (game);
		this.game = game;
	}
	

	@Override
	public void show()
	{
		Texture.setEnforcePotImages (false);
		
		splash = new Texture (Gdx.files.internal ("backgrounds/splash.jpg"));
		
		Image image = new Image (splash);
		image.setFillParent (true);
		
		image.getColor().a = 0f;
		
		image.addAction (sequence (fadeIn (0.5f), delay (2f), fadeOut (0.5f), run (
				new Runnable ()
				{
					public void run ()
					{
						game.setScreen (new MenuScreen (game));
					}
				}
		)));
		
		stage.addActor (image);
	}
}
