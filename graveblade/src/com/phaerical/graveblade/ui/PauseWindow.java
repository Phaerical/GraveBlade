package com.phaerical.graveblade.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.SoundManager;

public class PauseWindow extends Table
{
	private GraveBlade game;
	
	public PauseWindow (GraveBlade g)
	{
		super ();
		
		this.game = g;
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/uiskin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/uiskin.json"));
		skin.addRegions (atlas);
		
		setFillParent (true);
		setSkin (skin);
		setVisible (false);
		
		//*********************************
		// RETURN TO MAIN MENU
		//*********************************
		TextButton resumeButton = new TextButton ("RESUME", skin);
		add (resumeButton).spaceBottom(30);
		row ();
		
		resumeButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					SoundManager.play (SoundManager.CLICK);
				}
			}
		);
		
		TextButton backButton = new TextButton ("MAIN MENU", skin);
		add (backButton).spaceBottom(30);
		row ();
		
		backButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					SoundManager.play (SoundManager.CLICK);
					game.getScreenManager().setScreen (1);
				}
			}
		);
		
		TextButton exitButton = new TextButton ("EXIT GAME", skin);
		add (exitButton).spaceBottom(30);
		
		exitButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					Gdx.app.exit ();
				}
			}
		);
	}
}