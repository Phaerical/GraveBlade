package com.phaerical.graveblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FrostPillar extends Actor
{
	private Animation spellAnimation;
	private float stateTime;
	
	public FrostPillar ()
	{
		this.setWidth (160);
		this.setHeight (160);
		this.setX (0);
		this.setY (0);
		this.setBounds (getX (), getY (), getWidth (), getHeight ());
		
		TextureAtlas atlas = new TextureAtlas ("assets/sprites/frost_pillar.pack");
		this.spellAnimation = new Animation (0.12f, atlas.createSprites ("spell"));
		this.stateTime = 0f;
	}
	
	public Rectangle getBounds ()
	{
		return new Rectangle (getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void act (float delta)
	{
		if (spellAnimation.isAnimationFinished (stateTime))
		{
			remove ();
		}
		else
		{
			for (Actor a : getStage().getActors())
			{
				if (a.getClass().getSuperclass() == Enemy.class)
				{
					Enemy e = ((Enemy) a);
					
					if (getBounds().overlaps(e.getBounds()))
					{
						e.hurt (3);
					}
				}
			}
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
		stateTime += Gdx.graphics.getDeltaTime ();
		
		TextureRegion frame = spellAnimation.getKeyFrame (stateTime, false);
		batch.draw (frame, getX(), getY(), frame.getRegionWidth()*2, frame.getRegionHeight()*2);
	}
}
