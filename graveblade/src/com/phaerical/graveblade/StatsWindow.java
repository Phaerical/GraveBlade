package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class StatsWindow extends Table
{
	private boolean open;
	
	public StatsWindow ()
	{
		super ();
		
		open = false;
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/uiskin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/uiskin.json"));
		skin.addRegions (atlas);
		
		Texture.setEnforcePotImages(false);
		
		NinePatch np = new NinePatch (new Texture (Gdx.files.internal ("backgrounds/window3.png")), 16, 16, 16, 16);
		NinePatchDrawable npd = new NinePatchDrawable (np);
		
		//setSize (265, 390);
		setSize (800, 390);
		setPosition (-getWidth(), 80);
		setBackground (npd);
		
		Label lblLevel = new Label ("LEVEL", skin, "small");
		Label lblHeroLevel = new Label ("5", skin, "small");
		Label lblHealth = new Label ("HEALTH", skin, "small");
		Label lblHeroHealth = new Label ("100/100", skin, "small");
		Label lblStrength = new Label ("STRENGTH", skin, "small");
		Label lblHeroStrength = new Label ("36", skin, "small");
		Label lblVitality = new Label ("VITALITY", skin, "small");
		Label lblHeroVitality = new Label ("25", skin, "small");
		Label lblDefence = new Label ("DEFENCE", skin, "small");
		Label lblHeroDefence = new Label ("11", skin, "small");
		Label lblAttackSpeed = new Label ("ATTACK SPEED", skin, "small");
		Label lblHeroAttackSpeed = new Label ("100%", skin, "small");
		Label lblAttackDamage = new Label ("ATTACK DAMAGE", skin, "small");
		Label lblHeroAttackDamage = new Label ("36-42", skin, "small");
		
		add (new Label ("STATS", skin)).align (Align.center).colspan(2).spaceBottom(15).row();
		add (lblLevel).align (Align.left).expandX ();
		add (lblHeroLevel).align (Align.right).row ();
		add (lblHealth).align (Align.left);
		add (lblHeroHealth).align (Align.right);
		row ().spaceTop(30);
		add (lblStrength).align (Align.left);
		add (lblHeroStrength).align (Align.right).row ();
		add (lblVitality).align (Align.left);
		add (lblHeroVitality).align (Align.right).row ();
		add (lblDefence).align (Align.left);
		add (lblHeroDefence).align (Align.right);
		row ().spaceTop(30);
		add (lblAttackSpeed).align (Align.left);
		add (lblHeroAttackSpeed).align (Align.right).row ();
		add (lblAttackDamage).align (Align.left);
		add (lblHeroAttackDamage).align (Align.right).row ();
		
		padTop (60);
		padBottom (85);
		padLeft (75);
		padRight (75);
	}
	
	public void show ()
	{
		addAction (Actions.moveTo (100, 80, 0.15f));
		open = true;
	}
	
	public void hide ()
	{
		addAction (Actions.moveTo (-getWidth(), 80, 0.15f));
		open = false;
	}
	
	public boolean isOpen ()
	{
		return open;
	}
	
	/*
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
	}*/
}
