package com.phaerical.graveblade.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.SoundManager;

public class MenuScreen extends BasicScreen
{
	private GraveBlade game;
	
	public MenuScreen (GraveBlade game)
	{
		super (game);
		this.game = game;
	}
	
	@Override
	public void show ()
	{
		super.show ();
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/uiskin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/uiskin.json"));
		skin.addRegions (atlas);
		
		Image bg = new Image (new Texture ("assets/backgrounds/menu.jpg"));
		bg.getColor().a = 0f;
		bg.addAction (Actions.fadeIn (1f));
		stage.addActor (bg);
		
		Table table = new Table (skin);
		table.setFillParent (true);
		
		TextButton startGameButton = new TextButton ("START GAME", skin);
		
		startGameButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					SoundManager.play (SoundManager.CLICK);
					
					actor.getStage().addAction (sequence (fadeOut (1f), run (
							new Runnable ()
							{
								public void run ()
								{
									game.setScreen (new GameScreen (game));
								}
							}
					)));
				}
			}
		);
		
		table.add (startGameButton).padTop(30).align (Align.left);
		table.row ();
		
		TextButton optionsButton = new TextButton ("OPTIONS", skin);
		
		optionsButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					SoundManager.play (SoundManager.CLICK);
					
					actor.getStage().addAction (sequence (fadeOut (0.3f), run (
							new Runnable ()
							{
								public void run ()
								{
									game.setScreen (new OptionsScreen (game));
								}
							}
					)));
				}
			}
		);
		
		
		table.add (optionsButton).align (Align.left);
		table.row ();
		
		TextButton exitButton = new TextButton ("EXIT", skin);
		
		exitButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					Gdx.app.exit ();
				}
			}
		);
		
		table.add (exitButton).align (Align.left);
		
		table.left();
		table.padLeft (140);
		table.getColor().a = 0f;
		table.addAction (Actions.fadeIn (1f));
		stage.addActor (table);
	}
}
