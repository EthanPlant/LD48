package com.exedo.ld.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.exedo.ld.LudumDare;
import com.exedo.ld.ui.GameHud;
import com.exedo.ld.world.World;
import com.exedo.ld.world.block.BlockManager;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;

public class GameScreen implements Screen, InputProcessor {
    private LudumDare game;

    private OrthographicCamera cam;
    private Viewport port;

    private World world;
    private GameHud hud;

    private ShapeRenderer debugRenderer;

    public GameScreen(LudumDare game) {
        this.game = game;
        cam = new OrthographicCamera();
        port = new FitViewport(LudumDare.V_WIDTH, LudumDare.V_HEIGHT, cam);
        world = new World();
        cam.position.set(world.getPlayer().getPos(), 0);
        hud = new GameHud(this, world);
        debugRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.update();
        cam.position.set(world.getPlayer().getPos(), 0);
        cam.update();
        hud.update(cam);
        Gdx.gl.glClearColor(4.0f / 255.0f, 132.0f / 255.0f, 209.0f / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        game.getBatch().setProjectionMatrix(cam.combined);
        game.getBatch().begin();
        world.render(game.getBatch(), cam);
        game.getBatch().end();

        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        world.debugRender(debugRenderer);
        debugRenderer.end();

        hud.render(cam);
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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.A) {
            world.getPlayer().setVelocity(new Vector2(-10 * ChunkManager.TILE_SIZE, world.getPlayer().getVelocity().y));
        }
        if (keycode == Input.Keys.D) {
            world.getPlayer().setVelocity(new Vector2(10 * ChunkManager.TILE_SIZE, world.getPlayer().getVelocity().y));
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) {
            world.getPlayer().setVelocity(new Vector2(0, world.getPlayer().getVelocity().y));
        }
        if (keycode == Input.Keys.D) {
            world.getPlayer().setVelocity(new Vector2(0, world.getPlayer().getVelocity().y));
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
