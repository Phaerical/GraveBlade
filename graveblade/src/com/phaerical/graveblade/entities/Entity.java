package com.phaerical.graveblade.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.phaerical.graveblade.SoundManager;

public abstract class Entity extends Actor
{
	final float GRAVITY = -0.3f;
	
	private float speed;
	
	boolean movingRight = false;
	boolean movingLeft = false;
	boolean jumping = false;
	boolean falling = false;
	boolean facingRight = true;
	boolean attacking = false;
	boolean hurt = false;
	
	protected Vector2 velocity;
	
	private int maxHealth;
	private int health;
	private boolean alive;
	
	private Animation idleAnimation;
	private Animation runAnimation;
	private Animation jumpAnimation;
	private Animation attackAnimation;
	private Animation hurtAnimation;
	private Animation deathAnimation;
	
	protected float stateTime;
	protected float attackStateTime;
	
	private TiledMap map;
	private Array<Rectangle> tiles;
	
	private float spriteScale;
	
	
	public Entity (TiledMap map)
	{
		this.velocity = new Vector2 ();
		this.map = map;
		this.tiles = new Array<Rectangle> ();
		
		this.alive = true;
		this.stateTime = 0f;
		this.spriteScale = 1;
		this.speed = 1f;
	}
	
	
	public void setSpeed (float s)
	{
		speed = s;
	}
	
	
	/**************************************
	 * ANIMATION METHODS
	 **************************************/
	
	public Animation getIdleAnimation ()
	{
		return idleAnimation;
	}
	
	public Animation getRunAnimation ()
	{
		return runAnimation;
	}
	
	public Animation getJumpAnimation ()
	{
		return jumpAnimation;
	}
	
	public Animation getAttackAnimation ()
	{
		return attackAnimation;
	}
	
	public Animation getHurtAnimation ()
	{
		return hurtAnimation;
	}
	
	public Animation getDeathAnimation ()
	{
		return deathAnimation;
	}
	
	public void setIdleAnimation (Animation a)
	{
		idleAnimation = a;
	}
	
	public void setRunAnimation (Animation a)
	{
		runAnimation = a;
	}
	
	public void setJumpAnimation (Animation a)
	{
		jumpAnimation = a;
	}
	
	public void setAttackAnimation (Animation a)
	{
		attackAnimation = a;
	}
	
	public void setHurtAnimation (Animation a)
	{
		hurtAnimation = a;
	}
	
	public void setDeathAnimation (Animation a)
	{
		deathAnimation = a;
	}
	
	
	/**************************************
	 * HEALTH METHODS
	 **************************************/
	
	public int getHealth ()
	{
		return health;
	}
	
	public int getMaxHealth ()
	{
		return maxHealth;
	}
	
	public void setHealth (int amount)
	{
		if (amount > maxHealth)
		{
			health = maxHealth;
		}
		else if (amount <= 0)
		{
			health = 0;
			die ();
		}
		else
		{
			health = amount;
		}
	}
	
	public void setMaxHealth (int amount)
	{
		maxHealth = amount;
	}
	
	public void die ()
	{
		SoundManager.play (SoundManager.DEATH);
		stateTime = 0f;
		alive = false;
	}
	
	public boolean isAlive ()
	{
		return alive;
	}
	
	
	public void setSpriteScale (float scale)
	{
		spriteScale = scale;
	}
	
	

	/**************************************
	 * ACT METHOD
	 **************************************/
	
	@Override
	public void act (float delta)
	{
		if (alive)
		{
			super.act (delta);
			
			if (!hurt)
			{
				if (movingLeft)
				{
					if (!attacking)
						facingRight = false;
					velocity.x = -speed;
				}
				else if (movingRight)
				{
					if (!attacking)
						facingRight = true;
					velocity.x = speed;
				}
				else
				{
					velocity.x = 0;
				}
			}
			else
			{
				velocity.x = 0;
			}

			
			if (!hurt)
			{
				entityCollisionCheck ();
			}
			
			
			/*
			float newX = getX () + velocity.x * delta;
			float newY = getY () + velocity.y * delta;
			
			getCollisionTiles (tiles, newX, newY);
			
			boolean contactX, contactBottom, contactTop;
			contactX = contactBottom = contactTop = true;

			Vector2 leftUpperBound = new Vector2 (newX, newY + getHeight() * 2 / 3);
			Vector2 leftLowerBound = new Vector2 (newX, newY + getHeight() / 3);
			Vector2 rightUpperBound = new Vector2 (newX + getWidth(), newY + getHeight() * 2 / 3);
			Vector2 rightLowerBound = new Vector2 (newX + getWidth(), newY + getHeight() / 3);
			Vector2 upperLeftBound = new Vector2 (newX + getWidth() / 3, newY + getHeight());
			Vector2 upperRightBound = new Vector2 (newX + getWidth() * 2 / 3, newY + getHeight());
			Vector2 bottomLeftBound = new Vector2 (newX + getWidth() / 3, newY);
			Vector2 bottomRightBound = new Vector2 (newX + getWidth() * 2 / 3, newY);
			
			for (int i = 0; i < 2 && (contactTop || contactBottom || contactX); i++)
			{
				contactTop = contactBottom = contactX = false;
				
				for (Rectangle r : tiles)
				{
					
					while ((r.contains (leftUpperBound) || r.contains (leftLowerBound)) && velocity.x < 0)
					{
						setX (getX() + 0.01f);
						leftUpperBound.x = getX() * velocity.x;
						leftLowerBound.x = getX() * velocity.x;
						contactX = true;
					}
					
					while ((r.contains (upperLeftBound) || r.contains (upperRightBound)) && velocity.y > 0)
					{
						setY (getY() - 0.01f);
						upperLeftBound.y = getY() + velocity.y * delta + getHeight();
						upperRightBound.y = getY() + velocity.y * delta + getHeight();
						contactTop = true;
					}
					
					while ((r.contains (bottomLeftBound) || r.contains (bottomRightBound)) && velocity.y < 0)
					{
						setY (getY() + 0.01f);
						bottomLeftBound.y = getY () + velocity.y * delta;
						bottomRightBound.y = getY () + velocity.y * delta;
						contactBottom = true;
					}
					
					while ((r.contains (rightUpperBound) || r.contains(rightLowerBound)) && velocity.x > 0)
					{
						setX (getX() - 0.01f);
						rightUpperBound.x = getX() * velocity.x + getWidth();
						rightLowerBound.x = getX() * velocity.x + getWidth();
						contactX = true;
					}
				}
				
				if (contactTop)
				{
					velocity.y = 0;
				}
				
				if (contactBottom)
				{
					velocity.y = 0;
					jumping = false;
					falling = false;
				}
				
				if (contactX)
				{
					velocity.x = 0;
				}
			}
			
			if (velocity.y < 0)
			{
				falling = true;
			}
			
			velocity.add (0, GRAVITY);
			
			setPosition (getX () + velocity.x, getY () + velocity.y);*/
			
			float newX = getX () + velocity.x * delta;
			float newY = getY () + velocity.y * delta;
			
			getCollisionTiles (tiles, newX, newY);
			
			boolean contactX, contactBottom, contactTop;
			contactX = contactBottom = contactTop = true;

			Vector2 leftUpperBound = new Vector2 (newX, newY + getHeight() * 2 / 3);
			Vector2 leftLowerBound = new Vector2 (newX, newY + getHeight() / 3);
			Vector2 rightUpperBound = new Vector2 (newX + getWidth(), newY + getHeight() * 2 / 3);
			Vector2 rightLowerBound = new Vector2 (newX + getWidth(), newY + getHeight() / 3);
			Vector2 upperLeftBound = new Vector2 (newX + getWidth() / 3, newY + getHeight());
			Vector2 upperRightBound = new Vector2 (newX + getWidth() * 2 / 3, newY + getHeight());
			Vector2 bottomLeftBound = new Vector2 (newX + getWidth() / 3, newY);
			Vector2 bottomRightBound = new Vector2 (newX + getWidth() * 2 / 3, newY);
			
			for (int i = 0; i < 2 && (contactTop || contactBottom || contactX); i++)
			{
				contactTop = contactBottom = contactX = false;
				
				for (Rectangle r : tiles)
				{
					
					while ((r.contains (leftUpperBound) || r.contains (leftLowerBound)) && velocity.x < 0)
					{
						setX (getX() + 0.01f);
						leftUpperBound.x = getX() * velocity.x;
						leftLowerBound.x = getX() * velocity.x;
						contactX = true;
					}
					
					
					while ((r.contains (upperLeftBound) || r.contains (upperRightBound)) && velocity.y > 0)
					{
						setY (getY() - 0.01f);
						upperLeftBound.y = getY() + velocity.y * delta + getHeight();
						upperRightBound.y = getY() + velocity.y * delta + getHeight();
						contactTop = true;
					}
					
					while ((r.contains (bottomLeftBound) || r.contains (bottomRightBound)) && velocity.y < 0)
					{
						setY (getY() + 0.01f);
						bottomLeftBound.y = getY () + velocity.y * delta;
						bottomRightBound.y = getY () + velocity.y * delta;
						contactBottom = true;
					}
					
					while ((r.contains (rightUpperBound) || r.contains(rightLowerBound)) && velocity.x > 0)
					{
						setX (getX() - 0.01f);
						rightUpperBound.x = getX() * velocity.x + getWidth();
						rightLowerBound.x = getX() * velocity.x + getWidth();
						contactX = true;
					}
				}
				
				if (contactTop)
				{
					velocity.y = 0;
				}
				
				if (contactBottom)
				{
					velocity.y = 0;
					jumping = false;
					falling = false;
				}
				
				if (contactX)
				{
					velocity.x = 0;
				}
			}
			
			if (velocity.y < 0)
			{
				falling = true;
			}
			
			velocity.add (0, GRAVITY);
			
			setPosition (getX () + velocity.x, getY () + velocity.y);
		}
	}
	
	
	public void moveLeft (boolean move)
	{
		movingLeft = move;
	}
	
	
	public void moveRight (boolean move)
	{
		movingRight = move;
	}
	
	public void attack ()
	{
		SoundManager.play (SoundManager.SWING);
		attacking = true;
		attackStateTime = 0f;
	}
	
	public void hurt (int damage)
	{
		SoundManager.play (SoundManager.HIT);
		setHealth (getHealth() - damage);
		hurt = true;
		attacking = false;
		stateTime = 0;
		velocity.y = 3;
	}
	
	public Rectangle getBounds ()
	{
		return new Rectangle (getX (), getY (), getWidth (), getHeight ());
	}
	
	
	public Rectangle getAttackBounds ()
	{
		if (attacking)
		{
			if (facingRight)
			{
				return new Rectangle (getX()+getWidth(), getY(), 40, 50);
			}
			else
			{
				return new Rectangle (getX()-getWidth(), getY(), 60, 50);
			}
		}
		
		return null;
	}
	
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{	
		stateTime += Gdx.graphics.getDeltaTime ();
		TextureRegion frame;
		
		if (movingLeft || movingRight)
		{
			frame = runAnimation.getKeyFrame (stateTime, true);
		}
		else
		{
			frame = idleAnimation.getKeyFrame (stateTime, true);
		}
		
		if (jumping)
		{
			frame = jumpAnimation.getKeyFrame (stateTime, false);
		}
		else if (falling)
		{
			frame = jumpAnimation.getKeyFrame (1, false);
		}
		
		if (attacking)
		{
			attackStateTime += Gdx.graphics.getDeltaTime ();
			
			if (attackAnimation.isAnimationFinished (attackStateTime))
			{
				attacking = false;
			}
			else
			{
				frame = attackAnimation.getKeyFrame (attackStateTime, false);
			}
		}
		
		if (hurt)
		{
			if (hurtAnimation.isAnimationFinished (stateTime))
			{
				hurt = false;
			}
			else
			{
				frame = hurtAnimation.getKeyFrame (stateTime, false);
			}
		}
		
		if (!alive)
		{
			if (deathAnimation.isAnimationFinished (stateTime))
			{
				remove ();
			}
			else
			{
				frame = deathAnimation.getKeyFrame (stateTime, false);
			}
		}
		
		setSpriteColor (batch);
		
		if (facingRight)
		{
			batch.draw (frame, getX(), getY(), frame.getRegionWidth() * spriteScale, frame.getRegionHeight() * spriteScale);
		}
		else
		{
			batch.draw (frame, getX() + getWidth(), getY(), -frame.getRegionWidth() * spriteScale, frame.getRegionHeight() * spriteScale);
		}
	}
	

	public void setSpriteColor (SpriteBatch batch)
	{
		if (!alive)
		{
			batch.setColor (1, 0, 0, Math.max (0, 1 - stateTime / deathAnimation.animationDuration));
		}
		else if (hurt)
		{
			batch.setColor (Color.RED);
		}
		else
		{
			batch.setColor (Color.WHITE);
		}
	}
	
	
	protected abstract void entityCollisionCheck ();
	
	
	private void getCollisionTiles (Array<Rectangle> tiles, float startX, float startY)
	{
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
		
		tiles.clear ();
		
		for (int x = (int) (startX / 32) - 4; x < (int) (startX / 32) + 4; x++)
		{
			for (int y = (int) (startY / 32) - 4; y < (int) (startY / 32) + 4; y++)
			{
				Cell cell = layer.getCell (x, y);
				
				if (cell != null && cell.getTile().getProperties().containsKey("blocked"))
				{
					tiles.add (new Rectangle (x * map.getProperties().get("tilewidth", Integer.class),
							y * map.getProperties().get("tileheight", Integer.class),
							map.getProperties().get("tilewidth", Integer.class),
							map.getProperties().get("tileheight", Integer.class)));
				}
			}
		}
	}
}
