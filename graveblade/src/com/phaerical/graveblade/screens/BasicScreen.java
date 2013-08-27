package com.phaerical.graveblade.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.phaerical.graveblade.GraveBlade;

public abstract class BasicScreen implements Screen
{
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 576;
	
	protected final GraveBlade game;
	protected final Stage stage;
	
	public BasicScreen (GraveBlade game)
	{
		this.game = game;
		this.stage = new Stage (WIDTH, HEIGHT, true);
		//Gdx.input.setCursorCatched (true);
	}

	@Override
	public void render (float delta)
	{
		stage.act (delta);
		
		Gdx.gl.glClearColor (0f, 0f, 0f, 1f);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw ();
	}
	
	
	@Override
	public void show()
	{
		Gdx.input.setInputProcessor (stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		dispose ();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose ();
	}

}
