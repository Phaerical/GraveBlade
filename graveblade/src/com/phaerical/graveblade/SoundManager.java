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
		CLICK = Gdx.audio.newSound(Gdx.files.internal ("sounds/click.wav"));
		JUMP = Gdx.audio.newSound(Gdx.files.internal ("sounds/jump.wav"));
		LEVEL_UP = Gdx.audio.newSound(Gdx.files.internal ("sounds/level_up.wav"));
		DEATH = Gdx.audio.newSound(Gdx.files.internal ("sounds/death.wav"));
		SWING = Gdx.audio.newSound(Gdx.files.internal ("sounds/swing.wav"));
		HIT = Gdx.audio.newSound(Gdx.files.internal ("sounds/hit.wav"));
	}
	
	
	public static void play (Sound sound)
	{
		if (!Settings.getSoundMuted ())
		{
			sound.play (Settings.getSoundVolume ());
		}
	}
}
