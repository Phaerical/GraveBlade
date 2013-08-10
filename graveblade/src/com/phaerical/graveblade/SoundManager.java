package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager
{
	public static Sound CLICK;
	public static Sound JUMP;
	public static Sound LEVEL_UP;
	public static Sound DEATH;
	public static Sound SWING;
	public static Sound HIT;
	
	
	public static void load ()
	{
		CLICK = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/click.wav"));
		JUMP = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/jump.wav"));
		LEVEL_UP = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/level_up.wav"));
		DEATH = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/death.wav"));
		SWING = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/swing.wav"));
		HIT = Gdx.audio.newSound(Gdx.files.internal ("assets/sounds/hit.wav"));
	}
	
	
	public static void play (Sound sound)
	{
		if (!Settings.getSoundMuted ())
		{
			sound.play (Settings.getSoundVolume ());
		}
	}
}
