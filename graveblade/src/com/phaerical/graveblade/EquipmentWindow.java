package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array.ArrayIterator;
import com.phaerical.graveblade.Item.ItemType;
import com.phaerical.graveblade.entities.Hero;

public class EquipmentWindow extends Window
{
	private boolean open;
	
	private Label tooltip;
	//private ImageButton equip;
	
	private Hero hero;
	
	public EquipmentWindow (Skin skin, Hero h)
	{
		super ("EQUIPMENT", skin);
		
		this.hero = h;
		
		open = false;
		
		//*************************************
		// WINDOW PROPERTIES
		//*************************************
		setMovable (false);
		setKeepWithinStage (false);
		setSize (820, 390);
		setPosition (-getWidth(), 80);
		padTop (70);
		padLeft (35);
		padRight (35);
		left ();
		
		tooltip = new Label ("", skin, "small-boxed");
		tooltip.setAlignment (Align.center, Align.left);
		tooltip.setSize (200, 180);
		tooltip.setPosition(0, 0);
		tooltip.setVisible (false);
		
		ArrayIterator<Item> iter = new ArrayIterator<Item> (hero.getEquipment ());
		
		while (iter.hasNext ())
		{
			Item item = iter.next ();
			
			item.addListener (new InputListener ()
			{
				@Override
				public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor)
				{
					Item i = (Item) event.getListenerActor ();
					tooltip.setText (i.getTooltip ());
					tooltip.setVisible (true);
				}
				
				@Override
				public void exit (InputEvent event, float x, float y, int pointer, Actor toActor)
				{
					tooltip.setVisible (false);
				}
				
				@Override
				public boolean mouseMoved (InputEvent event, float x, float y)
				{
					float tX = x + event.getListenerActor().getX() + tooltip.getWidth () / 2 + 10;
					float tY = y + event.getListenerActor().getY() - tooltip.getHeight () / 2;
					
					// Handle tooltip clipping
					if (tX + tooltip.getWidth () > event.getStage().getWidth ())
					{
						tX = x + event.getListenerActor().getX() - tooltip.getWidth () / 2 - 10;
					}
					
					if (tY < 0)
					{
						tY = 0;
					}
					
					tooltip.setPosition (tX, tY);
					
					return true;
				}
			});
			
			add (item).size(64, 64).spaceRight (50).row ();
		}
		
		right ();
	}
	
	public void show ()
	{
		addAction (Actions.moveTo (100, 80, 0.15f));
		getStage().addActor(tooltip);
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
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
		super.draw (batch, parentAlpha);
		
		if (tooltip.isVisible ())
		{
			tooltip.draw (batch, parentAlpha);
		}
	}
}
