package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.phaerical.graveblade.entities.Hero;

public class StatusBar extends Table
{
	private Hero hero;
	private ShapeRenderer shape;
	private BitmapFont font;
	private FreeTypeFontGenerator generator;
	private Slider healthBar;
	private Slider manaBar;
	private Slider expBar;
	
	public StatusBar (Hero hero)
	{
		this.hero = hero;
		
		shape = new ShapeRenderer ();
		generator = new FreeTypeFontGenerator (Gdx.files.internal("assets/fonts/pixel_font.ttf"));
		font = generator.generateFont (16);
		
		TextureRegion tr = new TextureRegion (new Texture ("assets/backgrounds/status-bar.png"));
		TiledDrawable bg = new TiledDrawable (tr);
		setBackground (bg);
		setSize (1024, 60);
		
		NinePatch np = new NinePatch (new Texture ("assets/backgrounds/hp-bar.png"), 2, 2, 2, 2);
		NinePatch np2 = new NinePatch (new Texture ("assets/backgrounds/gray-bar.png"), 2, 2, 2, 2);
		NinePatch np3 = new NinePatch (new Texture ("assets/backgrounds/mp-bar.png"), 2, 2, 2, 2);
		NinePatch np4 = new NinePatch (new Texture ("assets/backgrounds/exp-bar.png"), 2, 2, 2, 2);
		NinePatch np5 = new NinePatch (new Texture ("assets/backgrounds/dark-gray-bar.png"), 2, 2, 2, 2);
		NinePatch np6 = new NinePatch (new Texture ("assets/backgrounds/light-gray-bar.png"), 2, 2, 2, 2);
		NinePatchDrawable npd = new NinePatchDrawable (np);
		NinePatchDrawable npd2 = new NinePatchDrawable (np2);
		NinePatchDrawable npd3 = new NinePatchDrawable (np3);
		NinePatchDrawable npd4 = new NinePatchDrawable (np4);
		NinePatchDrawable npd5 = new NinePatchDrawable (np5);
		NinePatchDrawable npd6 = new NinePatchDrawable (np6);
		
		npd2.setMinHeight(45);
		npd.setMinHeight(45);
		npd3.setMinHeight(45);
		npd4.setMinHeight(45);
		npd5.setMinHeight(45);
		npd6.setMinHeight(45);
		
		Image levelBar = new Image (npd5);
		
		SliderStyle style = new SliderStyle ();
		style.background = npd2;
		style.knobBefore = npd;
		healthBar = new Slider (0, 100, 0.1f, false, style);
		healthBar.setTouchable (Touchable.disabled);
		
		SliderStyle style2 = new SliderStyle ();
		style2.background = npd2;
		style2.knobBefore = npd3;
		manaBar = new Slider (0, 100, 0.1f, false, style2);
		manaBar.setValue (100);
		manaBar.setTouchable (Touchable.disabled);
		
		SliderStyle style3 = new SliderStyle ();
		style3.background = npd2;
		style3.knobBefore = npd4;
		expBar = new Slider (0, 100, 0.1f, false, style3);
		expBar.setValue (35);
		expBar.setTouchable (Touchable.disabled);
		
		
		ButtonStyle btnStyle = new ButtonStyle ();
		btnStyle.up = npd2;
		btnStyle.over = npd6;
		btnStyle.down = npd5;
		
		LabelStyle lblStyle = new LabelStyle ();
		lblStyle.font = new BitmapFont (Gdx.files.internal("assets/skins/pixel_font.fnt"), false);
		
		Image sword = new Image (new Texture ("assets/backgrounds/sword.png"));
		Image magic = new Image (new Texture ("assets/backgrounds/magic.png"));
		Image gear = new Image (new Texture ("assets/backgrounds/gear.png"));
		Image person = new Image (new Texture ("assets/backgrounds/person.png"));
		
		ImageButtonStyle stylez = new ImageButtonStyle ();
		stylez.up = npd2;
		stylez.over = npd6;
		stylez.down = npd5;
		stylez.imageUp = sword.getDrawable ();
		ImageButton imgBtn = new ImageButton (stylez);
		
		ImageButtonStyle stylez2 = new ImageButtonStyle ();
		stylez2.up = npd2;
		stylez2.over = npd6;
		stylez2.down = npd5;
		stylez2.imageUp = magic.getDrawable ();
		ImageButton imgBtn2 = new ImageButton (stylez2);
		
		ImageButtonStyle stylez3 = new ImageButtonStyle ();
		stylez3.up = npd2;
		stylez3.over = npd6;
		stylez3.down = npd5;
		stylez3.imageUp = gear.getDrawable ();
		ImageButton imgBtn3 = new ImageButton (stylez3);
		
		ImageButtonStyle stylez4 = new ImageButtonStyle ();
		stylez4.up = npd2;
		stylez4.over = npd6;
		stylez4.down = npd5;
		stylez4.imageUp = person.getDrawable ();
		ImageButton imgBtn4 = new ImageButton (stylez4);
		
		add (levelBar).width(100).spaceRight (3);
		add (healthBar).width(260).spaceRight (3);
		add (manaBar).width(200).spaceRight (3);
		add (expBar).width(250).spaceRight (3);
		add (imgBtn4).width(45).spaceRight (3);
		add (imgBtn).width(45).spaceRight (3);
		add (imgBtn2).width(45).spaceRight (3);
		add (imgBtn3).width(45).spaceRight (3);
		
		left ();
		padLeft (6);
		//padBottom (4);
	}
	
	@Override
	public void act (float delta)
	{
		healthBar.setValue (hero.getHealth ());
		expBar.setValue ((float) hero.getExp() / hero.getMaxExp() * 100);
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
        
        font.setColor (Color.WHITE);
        
        font.drawWrapped (batch, "LEVEL " + hero.getLevel (), 6, 33, 100, HAlignment.CENTER);
        
        font.draw (batch, "HP", healthBar.getX()+20, 33);
        font.draw (batch, hero.getHealth() + "/" + hero.getMaxHealth(), 290, 33);
        
        font.draw (batch, "MP", manaBar.getX()+20, 33);
        font.draw (batch, "85/100", 480, 33);
        
        font.draw (batch, "XP", expBar.getX()+20, 33);
        font.draw (batch, Math.round (((double) hero.getExp () / hero.getMaxExp () * 100) * 100) / 100.00 + "%", 710, 33);
	}
}
