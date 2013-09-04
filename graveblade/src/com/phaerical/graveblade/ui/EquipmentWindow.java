package com.phaerical.graveblade.ui;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.phaerical.graveblade.Assets;
import com.phaerical.graveblade.Item;
import com.phaerical.graveblade.TooltipManager;
import com.phaerical.graveblade.Item.ItemType;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.screens.GameScreen;

public class EquipmentWindow extends BasicWindow
{
	private boolean needUpdate;
	
	private Hero hero;
	private GameScreen game;
	
	public EquipmentWindow (GameScreen g)
	{
		super (g, "EQUIPMENT");
		
		this.game = g;
		this.hero = game.getHero ();
		
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
			tbl.setBackground (Assets.skin.getDrawable ("dark-box"));
			tbl.pad (15, 23, 15, 23);
			
			for (ItemType it : ItemType.values ())
			{
				if (it.getIndex () == i)
				{
					Label lbl = new Label (it.toString (), Assets.skin, "small");
					tbl.add (lbl).align (Align.center).spaceBottom (11).row ();
					
					if (hero.getEquipment().isSlotEmpty(i))
					{
						Image img = new Image (Assets.skin.getDrawable ("equip-" + it.toString().toLowerCase()));
						tbl.add (img).size (64, 64);
					}
					
					break;
				}
			}
			
			if (!hero.getEquipment().isSlotEmpty (i))
			{
				Item item = hero.getEquipment().getEquip (i);
				tbl.add (item).size (64, 64);
				
				TooltipManager tm = new TooltipManager (game.getTooltip (), item.getTooltip ());
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
			tbl.setBackground (Assets.skin.getDrawable ("box"));
			
			if (i < hero.getInventory().size)
			{
				Item item = hero.getInventory().get(i);
				tbl.add (item);
				
				TooltipManager tm = new TooltipManager (game.getTooltip (), item.getTooltip ());
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
}
