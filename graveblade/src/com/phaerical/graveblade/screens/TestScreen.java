package com.phaerical.graveblade.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.phaerical.graveblade.GraveBlade;
import com.phaerical.graveblade.SoundManager;

public class TestScreen extends BasicScreen
{

	public TestScreen(GraveBlade game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show ()
	{
		super.show ();
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/uiskin.atlas"));
		
		Skin skin = new Skin (Gdx.files.internal ("skins/uiskin.json"));
		skin.addRegions (atlas);
		
		Texture.setEnforcePotImages(false);
		
		NinePatch np = new NinePatch (new Texture ("assets/backgrounds/window.png"), 15, 15, 15, 15);
		NinePatchDrawable npd = new NinePatchDrawable (np);
		
		Table table = new Table (skin);
		table.setSize (265, 350);
		table.setPosition (-table.getWidth(), 100);
		table.setBackground (npd);
		
		Label lblLevel = new Label ("LEVEL", skin, "small");
		Label lblHeroLevel = new Label ("5", skin, "small");
		Label lblHealth = new Label ("HEALTH", skin, "small");
		Label lblHeroHealth = new Label ("100/100", skin, "small");
		Label lblStrength = new Label ("STRENGTH", skin, "small");
		Label lblHeroStrength = new Label ("36", skin, "small");
		Label lblVitality = new Label ("VITALITY", skin, "small");
		Label lblHeroVitality = new Label ("25", skin, "small");
		Label lblDefence = new Label ("DEFENCE", skin, "small");
		Label lblHeroDefence = new Label ("11", skin, "small");
		Label lblAttackSpeed = new Label ("ATTACK SPEED", skin, "small");
		Label lblHeroAttackSpeed = new Label ("100%", skin, "small");
		Label lblAttackDamage = new Label ("ATTACK DAMAGE", skin, "small");
		Label lblHeroAttackDamage = new Label ("36-42", skin, "small");
		
		table.add (new Label ("STATS", skin)).align (Align.center).colspan(2).spaceBottom(15).row();
		table.add (lblLevel).align (Align.left).expandX ();
		table.add (lblHeroLevel).align (Align.right).row ();
		table.add (lblHealth).align (Align.left);
		table.add (lblHeroHealth).align (Align.right);
		table.row().spaceTop(30);
		table.add (lblStrength).align (Align.left);
		table.add (lblHeroStrength).align (Align.right).row ();
		table.add (lblVitality).align (Align.left);
		table.add (lblHeroVitality).align (Align.right).row ();
		table.add (lblDefence).align (Align.left);
		table.add (lblHeroDefence).align (Align.right);
		table.row().spaceTop(30);
		table.add (lblAttackSpeed).align (Align.left);
		table.add (lblHeroAttackSpeed).align (Align.right).row ();
		table.add (lblAttackDamage).align (Align.left);
		table.add (lblHeroAttackDamage).align (Align.right).row ();
		
		table.padBottom (35);
		table.padLeft (25);
		table.padRight (25);
		
		stage.addActor (table);
		table.addAction (Actions.moveTo (100, 100, 0.5f));
	}
}
