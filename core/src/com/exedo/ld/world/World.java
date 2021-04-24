package com.exedo.ld.world;

// Handles all the world simulation

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.world.chunk.Chunk;

public class World {
    Chunk chunk;

    public World() {
        chunk = new Chunk();
    }

    public void render(SpriteBatch batch) {
        chunk.renderChunk(batch);
    }
}
