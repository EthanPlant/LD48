package com.exedo.ld.world;

// Handles all the world simulation

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;

public class World {
    private ChunkManager chunkManager;

    public World() {
        chunkManager = new ChunkManager();
    }

    public void update(float playerX, float playerY) {
        chunkManager.updateChunks(playerX, playerY);
    }

    public void render(SpriteBatch batch, OrthographicCamera cam) {
        chunkManager.renderChunks(batch, cam);
    }
}
