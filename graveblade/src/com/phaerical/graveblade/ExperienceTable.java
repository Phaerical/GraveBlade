package com.phaerical.graveblade;

public class ExperienceTable
{
	private static int[] table = {
			0,
			10, //LEVEL 1
			20,
			30,
			40,
			50, //LEVEL 5
			60,
			70,
			80,
			90,
			100, //LEVEL 10
			200,
			300,
			400,
			500,
			600, //LEVEL 15
			700,
			800,
			900,
			1000,
			5000 //LEVEL 20 (MAX)
		};
	
	public static int getExpRequired (int level)
	{
		return table[level];
	}
}
