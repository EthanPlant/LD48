package com.exedo.ld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.exedo.ld.screens.TitleScreen;

public class LudumDare extends Game {
	private SpriteBatch batch;
	public static AssetManager assets;

	// Viewport width and height
	public static int V_WIDTH = 640;
	public static int V_HEIGHT = 360;

	@Override
	public void create() {
		batch = new SpriteBatch();
		assets = new AssetManager();
		loadAssets();
		setScreen(new TitleScreen(this));
	}

	private void loadAssets() {
		assets.load("titlescreen.png", Texture.class);
		assets.load("blocks.atlas", TextureAtlas.class);
		assets.load("ui_font.fnt", BitmapFont.class);
		assets.finishLoading();
	}


	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		assets.dispose();
	}
}
