package com.phaerical.graveblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.phaerical.graveblade.Controller;
import com.phaerical.graveblade.EquipmentWindow;
import com.phaerical.graveblade.FloatingText;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.PauseWindow;
import com.phaerical.graveblade.StatsWindow;
import com.phaerical.graveblade.StatusBar;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.entities.Mushroom;
import com.phaerical.graveblade.entities.Poring;

public class GameScreen extends BasicScreen
{
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private ShapeRenderer shape = new ShapeRenderer ();
	
	private Controller controller;
	
	private GraveBlade game;
	
	private Stage ui;
	
	private PauseWindow pauseWindow;
	private StatsWindow statsWindow;
	private EquipmentWindow equipWindow;
	
	public static FloatingText ft;
	
	private Hero hero;
	private Poring poring;
	private Mushroom mushroom;
	
	private Texture cursor;
	
	private float mouseActiveTime;
	
	Texture background;
	
	public enum State
	{
		PAUSED, RUNNING, VIEW_WINDOW;
	}
	
	public State state;
	
	public GameScreen (GraveBlade game)
	{
		super (game);
		this.game = game;
		this.state = State.RUNNING;
	}
	

	@Override
	public void show()
	{
		Texture.setEnforcePotImages (false);
		
		background = new Texture (Gdx.files.internal ("backgrounds/forest.png"));
		
		map = new TmxMapLoader().load (Gdx.files.internal ("maps/test-map.tmx").path ());
		renderer = new OrthogonalTiledMapRenderer (map);
		
		camera = new OrthographicCamera ();
		camera.setToOrtho (false, 1024, 576);
		
		hero = new Hero (game, map);
		hero.setPosition(500, 200);
		stage.addActor (hero);
		
		
		for (int i = 0; i < 5; i++)
		{
			poring = new Poring (map);
			poring.setPosition (400 + 100 * i, 200);
			//stage.addActor (poring);
		}
		
		for (int i = 0; i < 5; i++)
		{
			mushroom = new Mushroom (map);
			mushroom.setPosition (400 + 100 * i, 400);
			//stage.addActor (mushroom);
		}
		
		controller = new Controller (hero, this);
		
		stage.addListener (controller);
		
		ui = new Stage ();
		
		StatusBar status = new StatusBar (hero);
		ui.addActor (status);
		
		pauseWindow = new PauseWindow (game);
		ui.addActor (pauseWindow);
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/ui-skin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/ui-skin.json"));
		skin.addRegions (atlas);
		
		statsWindow = new StatsWindow (skin, hero);
		ui.addActor (statsWindow);
		
		equipWindow = new EquipmentWindow (skin, hero);
		ui.addActor (equipWindow);
		
		ft = new FloatingText ();
		stage.addActor (ft);
		
		Label tooltip = new Label ("", skin, "tooltip");
		tooltip.setName ("tooltip");
		tooltip.setAlignment (Align.center, Align.left);
		tooltip.setVisible (false);
		
		ui.addActor (tooltip);
		
		InputMultiplexer im = new InputMultiplexer (stage, ui);
		
		cursor = new Texture (Gdx.files.internal ("sprites/cursor.png"));
		mouseActiveTime = 100;
		
		Gdx.input.setInputProcessor (im);
	}
	
	
	public void triggerStatsWindow ()
	{
		if (statsWindow.isOpen ())
		{
			statsWindow.hide ();
		}
		else
		{
			statsWindow.show ();
		}
	}
	
	public void triggerEquipmentWindow ()
	{
		if (equipWindow.isOpen ())
		{
			equipWindow.hide ();
		}
		else
		{
			equipWindow.show ();
		}
	}
	
	
	@Override
	public void render (float delta)
	{
		ui.act (delta);
		
		if (state == State.PAUSED)
		{
			pauseWindow.setVisible (true);
		}
		else
		{
			pauseWindow.setVisible (false);
			
			stage.act (delta);
			
			if (!stage.getActors().contains (hero, false))
			{
				game.getScreenManager().setScreen (2);
			}
			
			Gdx.gl.glClearColor (0.8f, 0.8f, 0.8f, 1f);
			Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
			
			float tX = hero.getX() - camera.position.x;
			float tY = hero.getY() - camera.position.y;
			
			float lerp = 0.1f;
			
			int width = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
			int height = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
			
			if (hero.getX() < camera.viewportWidth / 2)
			{
				tX = camera.viewportWidth / 2 - camera.position.x;
			}
			else if (hero.getX() > width - camera.viewportWidth / 2)
			{
				tX = width - camera.viewportWidth / 2 - camera.position.x;
			}
			
			if (hero.getY () < camera.viewportHeight / 2)
			{
				tY = camera.viewportHeight / 2 - camera.position.y;
			}
			else if (hero.getY () > height - camera.viewportHeight / 2)
			{
				tY = height - camera.viewportHeight / 2 - camera.position.y;
			}
			
			camera.position.x += tX * lerp;
			camera.position.y += tY * lerp;
			camera.update ();
			
			ui.getSpriteBatch().begin ();
			ui.getSpriteBatch().draw (background, 0, 0);
			ui.getSpriteBatch().end ();
			
			renderer.setView (camera);
			renderer.render ();
			
			stage.setCamera(camera);
			stage.draw ();
		}
		
		if (state == State.PAUSED)
		{
		    tintScreen ();
		}
		
		ui.draw ();
		
		// Keep cursor within the screen
		if (Gdx.input.getX () < 0)
		{
			Gdx.input.setCursorPosition (0, - (Gdx.input.getY () - HEIGHT));
		}
		else if (Gdx.input.getX () > WIDTH - cursor.getWidth ())
		{
			Gdx.input.setCursorPosition (WIDTH - cursor.getWidth (), - (Gdx.input.getY () - HEIGHT));
		}
		
		if (Gdx.input.getY() < 0)
		{
			Gdx.input.setCursorPosition (Gdx.input.getX(), HEIGHT);
		}
		else if (Gdx.input.getY() > HEIGHT - cursor.getHeight ())
		{
			Gdx.input.setCursorPosition (Gdx.input.getX(), cursor.getHeight ());
		}
		
		// Handle mouse inactivity
		if (Gdx.input.getDeltaX() != 0 && Gdx.input.getDeltaY() != 0)
		{
			mouseActiveTime = 100;
		}
		
		mouseActiveTime--;
		
		// Draw cursor if active
		if (mouseActiveTime > 0)
		{
			ui.getSpriteBatch ().begin ();
			ui.getSpriteBatch ().draw (cursor, Gdx.input.getX (), - (Gdx.input.getY () - HEIGHT) - 32);
			ui.getSpriteBatch ().end ();
		}
	}
	
	
	public void tintScreen ()
	{
		Gdx.gl.glEnable (GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc (GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shape.begin (ShapeType.Filled);
		shape.setColor (0, 0, 0, 0.8f);
		shape.rect (0, 0, 1024, 576);
		shape.end ();
		Gdx.gl.glDisable (GL20.GL_BLEND);
	}
}
