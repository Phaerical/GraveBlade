package com.phaerical.graveblade;

public class Formula
{
	public static int calculateCritChance (int luck)
	{
		return luck;
	}
	
	public static int calculateHealth (int vitality)
	{
		return vitality * 10;
	}
	
	public static double calculateAttackSpeed (int dexterity)
	{
		return 0.9 + (double) dexterity / 100;
	}
	
	public static int calculateMinDamage (int strength)
	{
		return strength - 2;
	}
	
	public static int calculateMaxDamage (int strength)
	{
		return strength + 2;
	}
}
