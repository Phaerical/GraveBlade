package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.phaerical.graveblade.entities.Hero;

public class StatusUI extends Actor
{
	private Hero hero;
	private ShapeRenderer shape;
	private BitmapFont font;
	private BitmapFont fontBig;
	private FreeTypeFontGenerator generator;
	
	public StatusUI (Hero hero)
	{
		this.hero = hero;
		
		shape = new ShapeRenderer ();
		generator = new FreeTypeFontGenerator (Gdx.files.internal ("fonts/upheaval.ttf"));
		font = generator.generateFont (16);
		fontBig = generator.generateFont (30);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
        batch.end();
		shape.begin (ShapeType.Filled);
		
		// HP BAR
		shape.setColor (Color.DARK_GRAY);
		shape.rect (30, 500, 208, 30);
		shape.setColor (Color.WHITE);
		shape.rect (32, 502, 204, 26);
		shape.setColor (Color.GRAY);
		shape.rect (34, 504, 200, 22);
		shape.setColor (Color.RED);
		shape.rect (34, 504, (float) hero.getHealth() / hero.getMaxHealth() * 200, 22);
		shape.setColor (1, 0.15f, 0.3f, 1);
		shape.rect (34, 515, (float) hero.getHealth() / hero.getMaxHealth() * 200, 11);
		
		
		// MANA BAR
		/*shape.setColor (Color.DARK_GRAY);
		shape.rect (30, 490, 108, 26);
		shape.setColor (Color.WHITE);
		shape.rect (32, 492, 104, 22);
		shape.setColor (Color.GRAY);
		shape.rect (34, 494, 100, 18);
		shape.setColor (Color.BLUE);
		shape.rect (34, 494, 100, 18);*/
		
		
		// EXP BAR
		shape.setColor (0.35f, 0.35f, 0.35f, 1);
		shape.rect (0, 0, 1024, 32);
		shape.setColor (0.25f, 0.25f, 0.25f, 1);
		shape.rect (0, 0, 1024, 29);
		shape.setColor (0.2f, 0.2f, 0.2f, 1);
		shape.rect (0, 0, 1024, 15);
		shape.setColor (Color.DARK_GRAY);
		shape.rect (50, 10, 894, 8);
		shape.setColor (Color.GRAY);
		shape.rect (52, 12, 890, 4);
		shape.setColor (0.6f, 0.7f, 0.2f, 1);
		shape.rect (50, 10, (float) hero.getExp() / hero.getMaxExp() * 890 + 4, 8);
		shape.setColor (0.85f, 1f, 0f, 1);
		shape.rect (52, 12, (float) hero.getExp() / hero.getMaxExp() * 890, 4);
		shape.end ();
		
        batch.begin();
        
        // HP BAR
        font.setColor (Color.WHITE);
        font.draw (batch, "HP", 40, 519);
        font.draw (batch, hero.getHealth() + "/" + hero.getMaxHealth(), 170, 519);
        
        // EXP BAR
        font.setColor (0.85f, 1f, 0f, 1);
        font.draw (batch, "EXP", 12, 18);
        font.draw (batch, Math.round (((double) hero.getExp () / hero.getMaxExp () * 100) * 100) / 100.00 + "%", 950, 18);
        
        // LEVEL
        fontBig.setColor (Color.BLACK);
        fontBig.draw (batch, "LEVEL " + hero.getLevel(), 42, 553);
        fontBig.setColor (Color.WHITE);
        fontBig.draw (batch, "LEVEL " + hero.getLevel(), 40, 555);
	}
}
