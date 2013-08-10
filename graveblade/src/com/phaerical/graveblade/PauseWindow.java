package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseWindow extends Table
{
	private ShapeRenderer shape;
	private GraveBlade game;
	
	public PauseWindow (GraveBlade g)
	{
		super ();
		
		this.game = g;
		
		shape = new ShapeRenderer ();
		
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
	
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
		batch.end ();
		Gdx.gl.glEnable (GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	    shape.begin(ShapeType.Filled);
	    shape.setColor(0, 0, 0, 0.8f);
	    shape.rect (0, 0, 1024, 576);
	    shape.end();
	    Gdx.gl.glDisable (GL20.GL_BLEND);
	    batch.begin ();
	    
	    super.draw (batch, parentAlpha);
	}
}