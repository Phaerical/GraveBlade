package com.phaerical.graveblade.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.Settings;
import com.phaerical.graveblade.SoundManager;

public class OptionsScreen extends BasicScreen
{
	public OptionsScreen (GraveBlade game)
	{
		super (game);
	}
	
	
	@Override
	public void show ()
	{
		super.show ();
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/uiskin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/uiskin.json"));
		skin.addRegions (atlas);
		
		Table table = new Table (skin);
		table.setFillParent (true);
		
		table.add ("OPTIONS").colspan(3).spaceBottom(50);
		table.row ();
		
		
		//*********************************
		// SOUND CONTROLS
		//*********************************
		table.add ("SOUND").padTop(-10).space(10).spaceBottom(25);
		
		Slider soundSlider = new Slider (0f, 1f, 0.1f, false, skin);
		soundSlider.setValue (Settings.getSoundVolume ());
		table.add (soundSlider).spaceBottom(25).spaceLeft(25);
		
		soundSlider.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					float volume = ((Slider) actor).getValue ();
					Settings.setSoundVolume (volume);
					SoundManager.play (SoundManager.CLICK);
				}
			}
		);
		
		CheckBox soundCheck = new CheckBox ("", skin);
		soundCheck.setChecked (!Settings.getSoundMuted ());
		table.add (soundCheck).spaceBottom(25).spaceLeft(25);
		
		soundCheck.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					boolean muted = !((CheckBox) actor).isChecked ();
					Settings.setSoundMuted (muted);
					SoundManager.play (SoundManager.CLICK);
				}
			}
		);
		
		table.row();
		
		
		//*********************************
		// MUSIC CONTROLS
		//*********************************
		table.add ("MUSIC").padTop(-10).space(10).spaceBottom(25);
		
		Slider musicSlider = new Slider (0f, 1f, 0.1f, false, skin);
		musicSlider.setValue (Settings.getMusicVolume ());
		table.add (musicSlider).spaceBottom(25).spaceLeft(25);
		
		musicSlider.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					float volume = ((Slider) actor).getValue ();
					Settings.setMusicVolume (volume);
					//SoundManager.play (SoundManager.CLICK);
				}
			}
		);
		
		CheckBox musicCheck = new CheckBox ("", skin);
		musicCheck.setChecked (!Settings.getMusicMuted ());
		table.add (musicCheck).spaceBottom(25).spaceLeft(25);
		
		musicCheck.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					boolean muted = !((CheckBox) actor).isChecked ();
					Settings.setMusicMuted (muted);
					SoundManager.play (SoundManager.CLICK);
				}
			}
		);
		
		
		table.row ();
		
		
		//*********************************
		// RETURN TO MAIN MENU
		//*********************************
		TextButton backButton = new TextButton ("BACK TO MAIN MENU", skin);
		table.add (backButton).colspan(3).spaceTop(30);
		
		backButton.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					SoundManager.play (SoundManager.CLICK);
					
					actor.getParent().addAction (sequence (fadeOut (0.3f), run (
							new Runnable ()
							{
								public void run ()
								{
									game.setScreen (new MenuScreen (game));
								}
							}
					)));
				}
			}
		);
		
		stage.addActor (table);
	}
	
	
	@Override
	public void render (float delta)
	{
		stage.act (delta);
		
		Gdx.gl.glClearColor (0f, 0f, 0f, 1f);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw ();
	}
}
