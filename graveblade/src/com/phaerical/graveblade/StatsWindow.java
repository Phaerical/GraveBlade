package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.phaerical.graveblade.entities.Hero;

public class StatsWindow extends Window
{
	private boolean open;
	
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
	
	private Hero hero;
	
	public StatsWindow (Skin skin, Hero hero)
	{
		super ("STATS", skin);
		
		this.hero = hero;
		
		open = false;
		
		NinePatch np = new NinePatch (new Texture (Gdx.files.internal ("backgrounds/window.png")), 10, 10, 10, 10);
		NinePatchDrawable npd = new NinePatchDrawable (np);
		
		setMovable(false);
		setKeepWithinStage(false);
		setSize (820, 390);
		setPosition (-getWidth(), 80);
		setBackground (npd);
		padTop(70);
		padLeft (35);
		padRight (35);
		left ();
		
		TextButton btnStrength = new TextButton ("+STRENGTH", skin);
		TextButton btnVitality = new TextButton ("+VITALITY", skin);
		TextButton btnDexterity = new TextButton ("+DEXTERITY", skin);
		TextButton btnLuck = new TextButton ("+LUCK", skin);
		
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
		
		
		Table tblButtons = new Table ();
		tblButtons.add (new Label ("STAT POINTS: 2", skin, "small")).align (Align.center).spaceBottom(15).row();
		tblButtons.add (btnStrength).size (150, 50).space(4).row ();
		tblButtons.add (btnVitality).size (150, 50).space(4).row ();
		tblButtons.add (btnDexterity).size (150, 50).space(4).row ();
		tblButtons.add (btnLuck).size (150, 50).space(4).row ();
		
		
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
		
		statLevel.setText (String.valueOf (hero.getLevel ()));
		statHealth.setText (String.valueOf (hero.getMaxHealth ()));
		statStrength.setText (String.valueOf (hero.getStrength ()));
		statVitality.setText (String.valueOf (hero.getVitality ()));
		statDexterity.setText (String.valueOf (hero.getDexterity ()));
		statLuck.setText (String.valueOf (hero.getLuck ()));
		statAttackSpeed.setText ("100%");
		statAttackDamage.setText (String.valueOf (hero.getDamage ()));
		statCritChance.setText ("5%");
		statCritDamage.setText ("100%");
	}
}
