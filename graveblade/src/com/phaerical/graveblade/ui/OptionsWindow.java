package com.phaerical.graveblade.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.phaerical.graveblade.Assets;
import com.phaerical.graveblade.Settings;
import com.phaerical.graveblade.SoundManager;
import com.phaerical.graveblade.screens.GameScreen;

public class OptionsWindow extends BasicWindow
{
	private Slider soundSlider;
	private Slider musicSlider;
	
	private GameScreen game;
	
	public OptionsWindow (GameScreen g)
	{
		super (g, "OPTIONS");
		
		this.game = g;
		
		//*********************************
		// SOUND CONTROLS
		//*********************************
		
		this.add (new Label ("SOUND", Assets.skin, "small")).spaceBottom (4).colspan (2);
		
		soundSlider = new Slider (0f, 1f, 0.01f, false, Assets.skin);
		soundSlider.setValue (Settings.getSoundVolume ());
		
		soundSlider.setAnimateInterpolation (Interpolation.circle);
		
		soundSlider.addListener (new InputListener ()
			{
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
				{
					float volume = ((Slider) event.getListenerActor ()).getValue ();
					Settings.setSoundVolume (volume);
					SoundManager.play (SoundManager.CLICK);
					
					return true;
				}
			}
		);
		
		this.row ();
		this.add (soundSlider).width (300).spaceBottom (20).colspan (2);
		this.row ();
		
		this.add (new Label ("MUSIC", Assets.skin, "small")).spaceBottom (4).colspan (2);
		
		musicSlider = new Slider (0f, 1f, 0.01f, false, Assets.skin);
		musicSlider.setValue (Settings.getMusicVolume ());
		
		this.row ();
		this.add (musicSlider).width (300).spaceBottom (40).colspan (2);
		
		this.row ();
		
		TextButton backButton = new TextButton ("BACK TO MAIN MENU", Assets.skin);
		backButton.addListener (new ChangeListener ()
		{
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				SoundManager.play (SoundManager.CLICK);
			}
		});
	
		TextButton exitButton = new TextButton ("EXIT GAME", Assets.skin);
		exitButton.addListener (new ChangeListener ()
		{
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				Gdx.app.exit ();
			}
		});
		
		this.add (backButton).size(200, 50).spaceRight (10);
		this.add (exitButton).size(200, 50);
		
		//CheckBox soundCheck = new CheckBox ("", skin);
		//soundCheck.setChecked (!Settings.getSoundMuted ());
		//table.add (soundCheck).spaceBottom(25).spaceLeft(25);
		
		/*soundCheck.addListener (new ChangeListener ()
			{
				@Override
				public void changed (ChangeEvent event, Actor actor)
				{
					boolean muted = !((CheckBox) actor).isChecked ();
					Settings.setSoundMuted (muted);
					SoundManager.play (SoundManager.CLICK);
				}
			}
		);*/
		
		row();
		
		
		//*********************************
		// MUSIC CONTROLS
		//*********************************
		/*table.add ("MUSIC").padTop(-10).space(10).spaceBottom(25);
		
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
		);*/
	}
}
