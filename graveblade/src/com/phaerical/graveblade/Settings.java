package com.phaerical.graveblade;

public class Settings
{
	private static float soundVolume = 0.1f;
	private static boolean soundMuted = false;
	private static float musicVolume = 0.4f;
	private static boolean musicMuted = false;
	
	
	public static float getSoundVolume ()
	{
		return soundVolume;
	}
	
	public static void setSoundVolume (float volume)
	{
		soundVolume = volume;
	}
	
	public static boolean getSoundMuted ()
	{
		return soundMuted;
	}
	
	public static void setSoundMuted (boolean muted)
	{
		soundMuted = muted;
	}
	
	public static float getMusicVolume ()
	{
		return musicVolume;
	}
	
	public static void setMusicVolume (float volume)
	{
		musicVolume = volume;
	}
	
	public static boolean getMusicMuted ()
	{
		return musicMuted;
	}
	
	public static void setMusicMuted (boolean muted)
	{
		musicMuted = muted;
	}
}
