package com.phaerical.graveblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.phaerical.graveblade.Controller;
import com.phaerical.graveblade.FloatingText;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.PauseWindow;
import com.phaerical.graveblade.StatsWindow;
import com.phaerical.graveblade.StatusUI;
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
	
	public static FloatingText ft;
	
	private Hero hero;
	private Poring poring;
	private Mushroom mushroom;
	
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
		
		map = new TmxMapLoader().load ("assets/maps/map2.tmx");
		renderer = new OrthogonalTiledMapRenderer (map);
		
		camera = new OrthographicCamera ();
		camera.setToOrtho (false, 1024, 576);
		
		hero = new Hero (game, map);
		hero.setPosition(500, 200);
		stage.addActor (hero);
		
		
		for (int i = 0; i < 10; i++)
		{
			poring = new Poring (map);
			poring.setPosition (400 + 100 * i, 200);
			stage.addActor (poring);
		}
		
		for (int i = 0; i < 10; i++)
		{
			mushroom = new Mushroom (map);
			mushroom.setPosition (400 + 100 * i, 600);
			//stage.addActor (mushroom);
		}
		
		controller = new Controller (hero, this);
		
		stage.addListener (controller);
		
		ui = new Stage ();
		
		StatusUI status = new StatusUI (hero);
		ui.addActor (status);
		
		pauseWindow = new PauseWindow (game);
		ui.addActor (pauseWindow);
		
		statsWindow = new StatsWindow ();
		ui.addActor (statsWindow);
		
		ft = new FloatingText ();
		stage.addActor (ft);
		
		InputMultiplexer im = new InputMultiplexer (stage, ui);
		
		Gdx.input.setInputProcessor (im);
	}
	
	
	public void triggerStatsWindow ()
	{
		if (statsWindow.isOpen ())
		{
			statsWindow.hide ();
			state = State.RUNNING;
		}
		else
		{
			statsWindow.show ();
			state = State.VIEW_WINDOW;
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
			
			renderer.setView (camera);
			renderer.render ();
			
			stage.setCamera(camera);
			stage.draw ();
			
		}
		

		
		if (state == State.VIEW_WINDOW || state == State.PAUSED)
		{
		    tintScreen ();
		}
		
		ui.draw ();
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
