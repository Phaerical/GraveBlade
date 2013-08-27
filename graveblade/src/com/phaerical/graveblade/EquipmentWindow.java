package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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
		padBottom (25);
		left ();
		
		tooltip = new Label ("", skin, "tooltip");
		tooltip.setAlignment (Align.center, Align.left);
		tooltip.setPosition (0, 0);
		tooltip.addAction (Actions.alpha(0));
		
		Table tblEquipment = new Table ();
		
		ArrayIterator<Item> iter = new ArrayIterator<Item> (hero.getEquipment ());
		
		while (iter.hasNext ())
		{
			Item item = iter.next ();
			item.addListener (new TooltipManager (tooltip, item.getTooltip ()));
			
			Label lbl = new Label (item.getType().toString(), skin, "small");
			
			Table tbl = new Table ();
			tbl.setBackground (skin.getDrawable ("dark-box"));
			tbl.pad (15, 23, 15, 23);
			tbl.add (lbl).align (Align.center).spaceBottom (11).row ();
			tbl.add (item).size (64, 64);
			
			tblEquipment.add (tbl).space (4);
			
			if (item.getType() == ItemType.GLOVE)
			{
				tblEquipment.row ();
			}
		}
		
		Table tblInventory = new Table ();
		
		tblInventory.right ();
		
		for (int i = 0; i < 24; i++)
		{
			ImageButtonStyle style = new ImageButtonStyle ();
			ImageButton btn = new ImageButton (style);
			
			style.up = skin.getDrawable("box");
			
			// Fill in inventory
			if (i < hero.getInventory().size)
			{
				Item item = hero.getInventory().get (i);
				
				style.imageUp = item.getDrawable ();
				
				btn.addListener (new TooltipManager (tooltip, item.getTooltip ()));
			}
			
			tblInventory.add (btn).size (64, 64).spaceRight (4).spaceBottom (4);
			
			if ((i + 1) % 6 == 0)
			{
				tblInventory.row ();
			}
		}
		
		add (tblEquipment).spaceRight (30).expand ().bottom().left();
		add (tblInventory).bottom ();
	}
	
	public void show ()
	{
		addAction (Actions.moveTo (100, 80, 0.15f));
		getStage().addActor (tooltip);
		toFront ();
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
