package com.phaerical.graveblade.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Mushroom extends Enemy
{
	public Mushroom (TiledMap map)
	{
		super (map);
		
		this.setSpeed (2f);
		
		// HP
		this.setMaxHealth (55);
		this.setHealth (55);
		this.setName ("mushroom");
		this.setExp (5);
		
		// BOUNDS
		this.setWidth (45);
		this.setHeight (55);
		this.setX (0);
		this.setY (0);
		this.setBounds (getX (), getY (), getWidth (), getHeight ());

		// ANIMATIONS
		TextureAtlas atlas = new TextureAtlas ("assets/sprites/mushroom.pack");
		this.setIdleAnimation (new Animation (0.2f, atlas.createSprites ("idle")));
		this.setRunAnimation (new Animation (0.2f, atlas.createSprites ("run")));
		this.setJumpAnimation (new Animation (0.2f, atlas.createSprites ("run")));
		this.setAttackAnimation (new Animation (0.2f, atlas.createSprites ("run")));
		this.setHurtAnimation (new Animation (0.5f, atlas.createSprites ("hurt")));
		this.setDeathAnimation (new Animation (0.3f, atlas.createSprites ("die")));
	}
}
