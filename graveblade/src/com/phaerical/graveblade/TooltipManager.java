package com.phaerical.graveblade;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class TooltipManager extends InputListener
{
	private Label tooltip;
	private String text;
	
	public TooltipManager (Label tooltip, String text)
	{
		this.tooltip = tooltip;
		this.text = text;
	}
	
	@Override
	public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor)
	{
		tooltip.setText (text);
		tooltip.setSize (tooltip.getPrefWidth() + 30, tooltip.getPrefHeight() + 30);
		tooltip.addAction (Actions.fadeIn (0.15f));
	}
	
	@Override
	public void exit (InputEvent event, float x, float y, int pointer, Actor toActor)
	{
		tooltip.addAction (Actions.fadeOut (0.15f));
	}
	
	@Override
	public boolean mouseMoved (InputEvent event, float x, float y)
	{
		Vector2 position = event.getListenerActor ().localToStageCoordinates (new Vector2 (x, y));
		
		float tX = position.x;
		float tY = position.y - tooltip.getHeight ();
		
		// Handle tooltip clipping
		if (tX + tooltip.getWidth () > event.getStage().getWidth ())
		{
			tX = position.x - tooltip.getWidth ();
		}
		
		if (tY < 0)
		{
			tY = 0;
		}
		
		tooltip.setPosition (tX, tY);
		
		return true;
	}
}
