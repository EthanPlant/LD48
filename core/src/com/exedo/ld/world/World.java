package com.exedo.ld.world;

// Handles all the world simulation

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.exedo.ld.world.block.BlockEntity;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;
import com.exedo.ld.world.entity.Player;

public class World {
    public final static long RANDOM_SEED = System.currentTimeMillis();
    private ChunkManager chunkManager;
    private Array<BlockEntity> blockEntities;
    private Player player;

    public World() {
        chunkManager = new ChunkManager();
        blockEntities = new Array<>();
        player = new Player(1250 * ChunkManager.TILE_SIZE , 975 * ChunkManager.TILE_SIZE);
    }

    public void update(float playerX, float playerY) {
        blockEntities.clear();
        player.update();
        chunkManager.updateChunks(playerX, playerY);
        for (int i = -ChunkManager.TILE_SIZE * 10; i < ChunkManager.TILE_SIZE * 10; i+= ChunkManager.TILE_SIZE) {
            for (int j = -ChunkManager.TILE_SIZE * 10; j < ChunkManager.TILE_SIZE * 10; j+= ChunkManager.TILE_SIZE) {
                blockEntities.add(chunkManager.generateBlockEntity(playerX + i, playerY + j));
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera cam) {
        chunkManager.renderChunks(batch, cam);
        player.render(batch);
    }

    public void debugRender(ShapeRenderer renderer) {
        for (BlockEntity b : blockEntities) {
            if (b != null)
                b.drawBoundingBox(renderer, Color.BLUE);
        }
        player.drawBoundingBox(renderer, Color.GREEN);
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }
}
