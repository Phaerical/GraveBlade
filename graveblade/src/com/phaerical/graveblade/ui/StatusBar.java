package com.phaerical.graveblade.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.phaerical.graveblade.TooltipManager;
import com.phaerical.graveblade.entities.Hero;
import com.phaerical.graveblade.screens.GameScreen;

public class StatusBar extends Table
{
	private GameScreen game;
	private Hero hero;
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	
	private TextButton levelBar;
	private Slider healthBar;
	private Slider manaBar;
	private Slider expBar;
	
	private ImageButton btnStats;
	private ImageButton btnEquipment;
	private ImageButton btnSkills;
	private ImageButton btnOptions;
	
	public StatusBar (GameScreen g)
	{
		this.game = g;
		this.hero = game.getHero ();
		
		generator = new FreeTypeFontGenerator (Gdx.files.internal ("fonts/pixel_font.ttf"));
		font = generator.generateFont (16);
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/ui-skin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/ui-skin.json"));
		skin.addRegions (atlas);
		
		skin.getDrawable ("gray-bar").setMinHeight (45);
		skin.getDrawable ("red-bar").setMinHeight (45);
		skin.getDrawable ("blue-bar").setMinHeight (45);
		skin.getDrawable ("yellow-bar").setMinHeight (45);
		
		setBackground (skin.getTiledDrawable ("status-bar"));
		setSize (GameScreen.WIDTH, 60);
		left ();
		padLeft (8);
		
		levelBar = new TextButton ("LEVEL", skin);
		levelBar.setDisabled (true);
		
		healthBar = new Slider (0, 100, 0.1f, false, skin, "hp-bar");
		healthBar.setTouchable (Touchable.disabled);
		
		manaBar = new Slider (0, 100, 0.1f, false, skin, "mp-bar");
		manaBar.setTouchable (Touchable.disabled);
		manaBar.setValue (100);
		
		expBar = new Slider (0, 100, 0.1f, false, skin, "exp-bar");
		expBar.setTouchable (Touchable.disabled);
		
		btnStats = new ImageButton (skin, "person");
		btnEquipment = new ImageButton (skin, "sword");
		btnSkills = new ImageButton (skin, "magic");
		btnOptions = new ImageButton (skin, "gear");
		
		int space = GameScreen.WIDTH - 3 * 45 - 8 * 4;
		
		
		add (levelBar).width(space * 0.2f).height(45).spaceRight (4);
		add (healthBar).width(space * 0.4f).spaceRight (4);
		//add (manaBar).width(space * 0.2f).spaceRight (4);
		add (expBar).width(space * 0.4f).spaceRight (4);
		add (btnStats).width(45).height(45).spaceRight (4);
		add (btnEquipment).width(45).height(45).spaceRight (4);
		//add (btnSkills).width(45).height(45).spaceRight (4);
		add (btnOptions).width(45).height(45).spaceRight (4);
		
		btnStats.addListener ((new TooltipManager (game.getTooltip(), "STATS (U)", 0, 70).getListener ()));
		btnEquipment.addListener ((new TooltipManager (game.getTooltip(), "EQUIPMENT (I)", 0, 70).getListener ()));
		btnSkills.addListener ((new TooltipManager (game.getTooltip(), "SKILLS (K)", 0, 70).getListener ()));
		btnOptions.addListener ((new TooltipManager (game.getTooltip(), "OPTIONS (O)", 0, 70).getListener ()));
		
		btnStats.addListener (new InputListener ()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				game.triggerStatsWindow ();
				return true;
			}
		});
		
		btnEquipment.addListener (new InputListener ()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				game.triggerEquipmentWindow ();
				return true;
			}
		});
		
		btnOptions.addListener (new InputListener ()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
			{
				game.triggerOptionsWindow ();
				return true;
			}
		});
	}
	
	@Override
	public void act (float delta)
	{
		levelBar.setText ("LEVEL " + hero.getLevel ());
		healthBar.setValue ((float) hero.getHealth () / hero.getMaxHealth () * 100);
		expBar.setValue ((float) hero.getExp() / hero.getMaxExp() * 100);
		
		if (game.getStatsWindow().isOpen ())
		{
			btnStats.setChecked (true);
		}
		else
		{
			btnStats.setChecked (false);
		}
		
		if (game.getEquipmentWindow().isOpen ())
		{
			btnEquipment.setChecked (true);
		}
		else
		{
			btnEquipment.setChecked (false);
		}
		
		if (game.getOptionsWindow().isOpen ())
		{
			btnOptions.setChecked (true);
		}
		else
		{
			btnOptions.setChecked (false);
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
        
        font.setColor (Color.WHITE);
        
        font.draw (batch, "HP", healthBar.getX()+20, 33);
        font.drawWrapped (batch, hero.getHealth() + "/" + hero.getMaxHealth(), healthBar.getX(), 33, healthBar.getWidth() - 20, HAlignment.RIGHT);
        
        //font.draw (batch, "MP", manaBar.getX()+20, 33);
        //font.drawWrapped (batch, "100/100", manaBar.getX(), 33, manaBar.getWidth() - 20, HAlignment.RIGHT);
        
        font.draw (batch, "XP", expBar.getX()+20, 33);
        font.drawWrapped (batch, Math.round (((double) hero.getExp () / hero.getMaxExp () * 100) * 100) / 100.00 + "%",
        		expBar.getX(), 33, expBar.getWidth() - 20, HAlignment.RIGHT);
	}
}
