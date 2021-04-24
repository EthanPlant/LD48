package com.exedo.ld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exedo.ld.LudumDare;
import com.exedo.ld.world.World;
import com.exedo.ld.world.block.BlockManager;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;

public class GameScreen implements Screen {
    private LudumDare game;

    private OrthographicCamera cam;
    private Viewport port;

    private World world;

    public GameScreen(LudumDare game) {
        this.game = game;
        cam = new OrthographicCamera();
        port = new FitViewport(LudumDare.V_WIDTH, LudumDare.V_HEIGHT, cam);
        cam.position.set(400 * ChunkManager.TILE_SIZE, 320 * ChunkManager.TILE_SIZE, 0);
        world = new World();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) cam.position.set(cam.position.x - 10, cam.position.y, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) cam.position.set(cam.position.x + 10, cam.position.y, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) cam.position.set(cam.position.x, cam.position.y + 10, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) cam.position.set(cam.position.x, cam.position.y - 10, 0);
        cam.update();
        world.update(cam.position.x, cam.position.y);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        world.render(game.getBatch(), cam);
        game.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
