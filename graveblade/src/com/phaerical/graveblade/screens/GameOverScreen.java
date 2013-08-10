package com.phaerical.graveblade.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.phaerical.graveblade.GraveBlade;

public class GameOverScreen extends BasicScreen
{
	private GraveBlade game;
	
	public GameOverScreen (GraveBlade game)
	{
		super (game);
		this.game = game;
	}
	

	@Override
	public void show()
	{
		Image image = new Image (new Texture ("assets/backgrounds/gameover.jpg"));
		image.getColor().a = 0f;
		
		image.addAction (sequence (fadeIn (1f), delay (3f), fadeOut (0.5f), run (
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
