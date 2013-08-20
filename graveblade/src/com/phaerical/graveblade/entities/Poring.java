package com.phaerical.graveblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Poring extends Enemy
{
	public Poring (TiledMap map)
	{
		super (map);
		
		this.setSpeed (1f);
		
		// HP
		this.setMaxHealth (25);
		this.setHealth (25);
		this.setName ("poring");
		this.setExp (3);
		
		// BOUNDS
		this.setWidth (40);
		this.setHeight (40);
		this.setX (0);
		this.setY (0);
		this.setBounds (getX (), getY (), getWidth (), getHeight ());

		// ANIMATIONS
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("sprites/poring.pack"));
		this.setIdleAnimation (new Animation (0.2f, atlas.createSprites ("idle")));
		this.setRunAnimation (new Animation (0.2f, atlas.createSprites ("idle")));
		this.setJumpAnimation (new Animation (0.2f, atlas.createSprites ("idle")));
		this.setAttackAnimation (new Animation (0.2f, atlas.createSprites ("idle")));
		this.setHurtAnimation (new Animation (0.3f, atlas.createSprites ("hurt")));
		this.setDeathAnimation (new Animation (0.8f, atlas.createSprites ("hurt")));
	}
}
