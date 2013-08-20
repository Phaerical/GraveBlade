package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class FloatingText extends Actor
{
	private float DURATION = 1.5f;
	
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	
	private Array<Text> textArray;
	
	private class Text
	{
		String text;
		Color color;
		float x;
		float y;
		float time;
		
		public Text (String text, Color color, float x, float y, float time)
		{
			this.text = text;
			this.color = color;
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
	
	public FloatingText ()
	{
		generator = new FreeTypeFontGenerator (Gdx.files.internal ("fonts/upheaval.ttf"));
		font = generator.generateFont (40);
		textArray = new Array<Text> ();
	}
	
	
	public void show (String text, Color color, float x, float y)
	{
		textArray.add (new Text (text, color, x, y, 0));
	}
	
	
	@Override
	public void act (float delta)
	{
		for (Text t : textArray)
		{
			if (t.time >= DURATION)
			{
				textArray.removeValue (t, true);
			}
			else
			{
				t.y += 0.5f;
				t.time += delta;
			}
		}
	}
	
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
        for (Text t : textArray)
        {
        	float alpha = (float) Math.max (0, 1 - 1.2 * t.time / DURATION);
        	font.setColor (0, 0, 0, alpha);
        	font.draw (batch, t.text, t.x+2, t.y-2);
        	font.draw (batch, t.text, t.x-2, t.y-2);
        	font.draw (batch, t.text, t.x+2, t.y+2);
        	font.draw (batch, t.text, t.x-2, t.y+2);
        	
        	alpha = (float) Math.min (1, 3.1 - 3 * t.time / DURATION);
        	font.setColor (t.color.r, t.color.g, t.color.b, alpha);
        	font.draw (batch, t.text, t.x, t.y);
        }
	}
}
