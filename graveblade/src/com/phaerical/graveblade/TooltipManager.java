package com.phaerical.graveblade;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TooltipManager
{
	private Label tooltip;
	private String text;
	private float offsetX;
	private float offsetY;
	
	public TooltipManager (Label tooltip, String text)
	{
		this.tooltip = tooltip;
		this.text = text;
		this.offsetX = 0;
		this.offsetY = 0;
	}
	
	public TooltipManager (Label tooltip, String text, float offsetX, float offsetY)
	{
		this.tooltip = tooltip;
		this.text = text;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	private void updatePosition (InputEvent event, float x, float y)
	{
		Vector2 position = event.getListenerActor ().localToStageCoordinates (new Vector2 (x, y));
		
		float tX = position.x + 10 + offsetX;
		float tY = position.y - tooltip.getHeight () + offsetY;
		
		// Handle tooltip clipping
		if (tX + tooltip.getWidth () > event.getStage().getWidth ())
		{
			tX = position.x - tooltip.getWidth () - 10 - offsetX;
		}
		
		if (tY < 0)
		{
			tY = 0;
		}
		
		tooltip.setPosition (tX, tY);
	}
	
	public InputListener getListener ()
	{
		return new InputListener ()
		{
			@Override
			public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				tooltip.setText (text);
				tooltip.setSize (tooltip.getPrefWidth() + 20, tooltip.getPrefHeight() + 20);
				tooltip.setVisible (true);
				tooltip.toFront ();
				updatePosition (event, x, y);
			}
			
			@Override
			public void exit (InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				tooltip.setVisible (false);
			}
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				tooltip.setVisible (false);
				return false;
			}
			
			@Override
			public boolean mouseMoved (InputEvent event, float x, float y)
			{
				updatePosition (event, x, y);
				return true;
			}
		};
	}
}
