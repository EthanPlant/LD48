package com.exedo.ld.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.exedo.ld.LudumDare;
import com.exedo.ld.screens.GameScreen;
import com.exedo.ld.world.World;
import com.exedo.ld.world.chunk.Chunk;

public class GameHud {
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private World world;

    private GameScreen screen;

    public GameHud(GameScreen screen, World world) {
        this.screen = screen;
        this.world = world;
        sb = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, LudumDare.V_WIDTH, LudumDare.V_HEIGHT);
    }

    public void update(OrthographicCamera gameCam) {
        cam.update();
    }

    public void render(OrthographicCamera gameCam) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        LudumDare.assets.get("ui_font.fnt", BitmapFont.class).draw(sb, "FPS: " + Gdx.graphics.getFramesPerSecond(), LudumDare.V_WIDTH - 200, LudumDare.V_HEIGHT - 90);
        Chunk chunk = world.getChunkManager().getLoadedChunks()[2][2];
        if (chunk != null)
            LudumDare.assets.get("ui_font.fnt", BitmapFont.class).draw(sb, "Chunk: " + chunk.getX() + ", " + chunk.getY(), LudumDare.V_WIDTH - 200, LudumDare.V_HEIGHT - 60);
        sb.end();

        //stage.draw();
    }
}
