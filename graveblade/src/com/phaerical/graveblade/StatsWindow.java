package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.phaerical.graveblade.entities.Hero;

public class StatsWindow extends Window
{
	private boolean open;
	
	private Label statPoints;
	private TextButton statLevel;
	private TextButton statHealth;
	private TextButton statStrength;
	private TextButton statVitality;
	private TextButton statDexterity;
	private TextButton statLuck;
	private TextButton statAttackSpeed;
	private TextButton statAttackDamage;
	private TextButton statCritChance;
	private TextButton statCritDamage;
	
	private TextButton btnStrength;
	private TextButton btnVitality;
	private TextButton btnDexterity;
	private TextButton btnLuck;
	
	private Hero hero;
	
	public StatsWindow (Skin skin, Hero h)
	{
		super ("STATS", skin);
		
		this.hero = h;
		
		open = false;
		
		NinePatch np = new NinePatch (new Texture (Gdx.files.internal ("backgrounds/window.png")), 10, 10, 10, 10);
		NinePatchDrawable npd = new NinePatchDrawable (np);
		
		//*************************************
		// WINDOW PROPERTIES
		//*************************************
		setMovable (false);
		setKeepWithinStage (false);
		setSize (820, 390);
		setPosition (-getWidth(), 80);
		setBackground (npd);
		padTop(70);
		padLeft (35);
		padRight (35);
		left ();
		
		
		//*************************************
		// STAT LABELS
		//*************************************
		statPoints = new Label ("", skin, "small");
		statLevel = new TextButton ("", skin, "box");
		statHealth = new TextButton ("", skin, "box");
		statStrength = new TextButton ("", skin, "box");
		statVitality = new TextButton ("", skin, "box");
		statDexterity = new TextButton ("", skin, "box");
		statLuck = new TextButton ("", skin, "box");
		statAttackSpeed = new TextButton ("", skin, "box");
		statAttackDamage = new TextButton ("", skin, "box");
		statCritChance = new TextButton ("", skin, "box");
		statCritDamage = new TextButton ("", skin, "box");
		
		
		//*************************************
		// STAT BUTTONS
		//*************************************
		btnStrength = new TextButton ("+STRENGTH", skin);
		btnVitality = new TextButton ("+VITALITY", skin);
		btnDexterity = new TextButton ("+DEXTERITY", skin);
		btnLuck = new TextButton ("+LUCK", skin);
		
		btnStrength.addListener (new ChangeListener () {
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				hero.setStrength (hero.getStrength() + 1);
				hero.decreaseStatPoints (1);
			}});
		
		btnVitality.addListener (new ChangeListener () {
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				hero.setVitality (hero.getVitality() + 1);
				hero.decreaseStatPoints (1);
			}});
		
		btnDexterity.addListener (new ChangeListener () {
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				hero.setDexterity (hero.getDexterity() + 1);
				hero.decreaseStatPoints (1);
			}});
		
		btnLuck.addListener (new ChangeListener () {
			@Override
			public void changed (ChangeEvent event, Actor actor)
			{
				hero.setLuck (hero.getLuck() + 1);
				hero.decreaseStatPoints (1);
			}});
		
		
		//*************************************
		// BUTTON PANEL
		//*************************************
		Table tblButtons = new Table ();
		tblButtons.add (statPoints).align (Align.center).spaceBottom(15).row();
		tblButtons.add (btnStrength).size (150, 50).space(4).row ();
		tblButtons.add (btnVitality).size (150, 50).space(4).row ();
		tblButtons.add (btnDexterity).size (150, 50).space(4).row ();
		tblButtons.add (btnLuck).size (150, 50).space(4).row ();
		
		
		//*************************************
		// LABEL PANEL
		//*************************************
		Table tbl = new Table ();
		tbl.columnDefaults(0).align (Align.left).width (180);
		tbl.columnDefaults(1).align (Align.right).width (90).padLeft(-4).spaceRight (10);
		tbl.columnDefaults(2).align (Align.left).width (180);
		tbl.columnDefaults(3).align (Align.right).width (90).padLeft(-4);
		
		tbl.add (new TextButton ("LEVEL", skin, "box"));
		tbl.add (statLevel);
		tbl.add (new TextButton ("HEALTH", skin, "box"));
		tbl.add (statHealth);
		tbl.row ().spaceTop(30);
		
		tbl.add (new TextButton ("STRENGTH", skin, "box"));
		tbl.add (statStrength);
		tbl.add (new TextButton ("VITALITY", skin, "box"));
		tbl.add (statVitality).row ().spaceTop (4);
		tbl.add (new TextButton ("DEXTERITY", skin, "box"));
		tbl.add (statDexterity);
		tbl.add (new TextButton ("LUCK", skin, "box"));
		tbl.add (statLuck);
		tbl.row ().spaceTop(30);
		
		tbl.add (new TextButton ("ATTACK SPEED", skin, "box"));
		tbl.add (statAttackSpeed);
		tbl.add (new TextButton ("ATTACK DAMAGE", skin, "box"));
		tbl.add (statAttackDamage).row ().spaceTop (4);
		tbl.add (new TextButton ("CRITICAL CHANCE", skin, "box"));
		tbl.add (statCritChance);
		tbl.add (new TextButton ("CRITICAL DAMAGE", skin, "box"));
		tbl.add (statCritDamage);
		
		add (tblButtons).spaceRight (70).left().bottom();
		add (tbl).width (500);
	}
	
	public void show ()
	{
		addAction (Actions.moveTo (100, 80, 0.15f));
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
	public void act (float delta)
	{
		super.act (delta);
		
		statPoints.setText("STAT POINTS: " + hero.getStatPoints ());
		
		statLevel.setText (String.valueOf (hero.getLevel ()));
		statHealth.setText (String.valueOf (hero.getMaxHealth ()));
		statStrength.setText (String.valueOf (hero.getStrength ()));
		statVitality.setText (String.valueOf (hero.getVitality ()));
		statDexterity.setText (String.valueOf (hero.getDexterity ()));
		statLuck.setText (String.valueOf (hero.getLuck ()));
		statAttackSpeed.setText ("100%");
		statAttackDamage.setText (hero.getMinDamage () + "-" + hero.getMaxDamage ());
		statCritChance.setText (hero.getCritChance () + "%");
		statCritDamage.setText (hero.getCritDamage () + "%");
		
		if (hero.getStatPoints () == 0)
		{
			btnStrength.setDisabled (true);
			btnVitality.setDisabled (true);
			btnDexterity.setDisabled (true);
			btnLuck.setDisabled (true);
		}
		else
		{
			btnStrength.setDisabled (false);
			btnVitality.setDisabled (false);
			btnDexterity.setDisabled (false);
			btnLuck.setDisabled (false);
		}
	}
}
