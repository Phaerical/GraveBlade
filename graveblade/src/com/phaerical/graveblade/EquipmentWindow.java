package com.phaerical.graveblade;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.phaerical.graveblade.Item.ItemType;
import com.phaerical.graveblade.entities.Hero;

public class EquipmentWindow extends Window
{
	private boolean open;
	
	private boolean needUpdate;
	
	private Hero hero;
	private Skin skin;
	
	public EquipmentWindow (Skin skin, Hero h)
	{
		super ("EQUIPMENT", skin);
		
		this.hero = h;
		this.skin = skin;
		
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
		
		this.needUpdate = true;
	}
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		if (needUpdate)
		{
			update ();
			needUpdate = false;
		}
	}
	
	public void update ()
	{
		clear ();
		
		Table tblEquipment = new Table ();
		
		for (int i = 0; i < 6; i++)
		{
			Table tbl = new Table ();
			tbl.setBackground (skin.getDrawable ("dark-box"));
			tbl.pad (15, 23, 15, 23);
			
			for (ItemType it : ItemType.values ())
			{
				if (it.getIndex () == i)
				{
					Label lbl = new Label (it.toString (), skin, "small");
					tbl.add (lbl).align (Align.center).spaceBottom (11).row ();
					
					if (hero.getEquipment().isSlotEmpty(i))
					{
						Image img = new Image (skin.getDrawable ("equip-" + it.toString().toLowerCase()));
						tbl.add (img).size (64, 64);
					}
					
					break;
				}
			}
			
			if (!hero.getEquipment().isSlotEmpty (i))
			{
				Item item = hero.getEquipment().getEquip (i);
				tbl.add (item).size (64, 64);
				
				TooltipManager tm = new TooltipManager ((Label) getStage().getRoot().findActor ("tooltip"), item.getTooltip ());
				tbl.setTouchable (Touchable.enabled);
				tbl.addListener (tm.getListener ());
				
				item.clearListeners ();
				item.addListener (new InputListener () {
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
					{
						if (button == Buttons.RIGHT)
						{
							hero.getEquipment().removeEquip ((Item) event.getListenerActor ());
							needUpdate = true;
						}
						
						return true;
					}
				});
			}
			
			tblEquipment.add (tbl).space (4);
			
			if (i == 2)
			{
				tblEquipment.row ();
			}
		}
		
		
		Table tblInventory = new Table ();
		
		tblInventory.right ();
		
		for (int i = 0; i < 24; i++)
		{
			Table tbl = new Table ();
			tbl.setBackground (skin.getDrawable ("box"));
			
			if (i < hero.getInventory().size)
			{
				Item item = hero.getInventory().get(i);
				tbl.add (item);
				
				TooltipManager tm = new TooltipManager ((Label) getStage().getRoot().findActor ("tooltip"), item.getTooltip ());
				tbl.setTouchable (Touchable.enabled);
				tbl.addListener (tm.getListener ());
				
				item.clearListeners ();
				item.addListener (new InputListener ()
				{
					public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
					{
						if (button == Buttons.RIGHT)
						{
							hero.getEquipment().equipFromInventory ((Item) event.getListenerActor ());
							needUpdate = true;
						}
						
						return true;
					}
				});
			}
			
			tblInventory.add (tbl).size(64, 64).space (4);
			
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
		addAction (Actions.moveTo (100, 80, 0.5f, Interpolation.swingOut));
		open = true;
	}
	
	public void hide ()
	{
		addAction (Actions.moveTo (-getWidth(), 80, 0.5f, Interpolation.swingIn));
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
	}
}
