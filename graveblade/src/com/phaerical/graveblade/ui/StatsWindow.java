package com.phaerical.graveblade.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.phaerical.graveblade.Assets;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.screens.GameScreen;

public class StatsWindow extends BasicWindow
{
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
	private GameScreen game;
	
	public StatsWindow (GameScreen g)
	{
		super (g, "STATS");
		
		this.game = g;
		this.hero = game.getHero ();
		
		
		//*************************************
		// STAT LABELS
		//*************************************
		statPoints = new Label ("", Assets.skin, "small");
		statLevel = new TextButton ("", Assets.skin, "box");
		statHealth = new TextButton ("", Assets.skin, "box");
		statStrength = new TextButton ("", Assets.skin, "box");
		statVitality = new TextButton ("", Assets.skin, "box");
		statDexterity = new TextButton ("", Assets.skin, "box");
		statLuck = new TextButton ("", Assets.skin, "box");
		statAttackSpeed = new TextButton ("", Assets.skin, "box");
		statAttackDamage = new TextButton ("", Assets.skin, "box");
		statCritChance = new TextButton ("", Assets.skin, "box");
		statCritDamage = new TextButton ("", Assets.skin, "box");
		
		
		//*************************************
		// STAT BUTTONS
		//*************************************
		btnStrength = new TextButton ("+STRENGTH", Assets.skin);
		btnVitality = new TextButton ("+VITALITY", Assets.skin);
		btnDexterity = new TextButton ("+DEXTERITY", Assets.skin);
		btnLuck = new TextButton ("+LUCK", Assets.skin);
		
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
		
		tbl.add (new TextButton ("LEVEL", Assets.skin, "box"));
		tbl.add (statLevel);
		tbl.add (new TextButton ("HEALTH", Assets.skin, "box"));
		tbl.add (statHealth);
		tbl.row ().spaceTop(30);
		
		tbl.add (new TextButton ("STRENGTH", Assets.skin, "box"));
		tbl.add (statStrength);
		tbl.add (new TextButton ("VITALITY", Assets.skin, "box"));
		tbl.add (statVitality).row ().spaceTop (4);
		tbl.add (new TextButton ("DEXTERITY", Assets.skin, "box"));
		tbl.add (statDexterity);
		tbl.add (new TextButton ("LUCK", Assets.skin, "box"));
		tbl.add (statLuck);
		tbl.row ().spaceTop(30);
		
		tbl.add (new TextButton ("ATTACK SPEED", Assets.skin, "box"));
		tbl.add (statAttackSpeed);
		tbl.add (new TextButton ("ATTACK DAMAGE", Assets.skin, "box"));
		tbl.add (statAttackDamage).row ().spaceTop (4);
		tbl.add (new TextButton ("CRITICAL CHANCE", Assets.skin, "box"));
		tbl.add (statCritChance);
		tbl.add (new TextButton ("CRITICAL DAMAGE", Assets.skin, "box"));
		tbl.add (statCritDamage);
		
		add (tblButtons).spaceRight (56).left().bottom();
		add (tbl).width (500);
	}
	
	@Override
	public void act (float delta)
	{
		super.act (delta);
		
		statPoints.setText("STAT POINTS: " + hero.getStatPoints ());
		
		statLevel.setText (String.valueOf (hero.getLevel ()));
		statHealth.setText (String.valueOf (hero.getMaxHealth ()));
		statStrength.setText (hero.getStrength () + "+" + hero.getBonusStrength ());
		statVitality.setText (hero.getVitality () + "+" + hero.getBonusVitality ());
		statDexterity.setText (hero.getDexterity () + "+" + hero.getBonusDexterity ());
		statLuck.setText (hero.getLuck () + "+" + hero.getBonusLuck ());
		statAttackSpeed.setText (hero.getAttackSpeed () + "%");
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
