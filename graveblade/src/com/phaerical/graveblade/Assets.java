package com.phaerical.graveblade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets
{
	//public static AssetManager manager;
	public static Skin skin;
	
	public static void load ()
	{
		//manager = new AssetManager ();
		//manager.load ("skins/ui-skin.atlas", TextureAtlas.class);
		
		TextureAtlas atlas = new TextureAtlas (Gdx.files.internal ("skins/ui-skin.atlas"));
		
		skin = new Skin (Gdx.files.internal ("skins/ui-skin.json"));
		skin.addRegions (atlas);
	}
}
