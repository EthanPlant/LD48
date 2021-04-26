package com.exedo.ld.world;

// Handles all the world simulation

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.exedo.ld.world.block.BlockEntity;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;
import com.exedo.ld.world.entity.Player;

public class World {
    public static final long RANDOM_SEED = System.currentTimeMillis();
    public static final float GRAV_STRENGTH = -10 * ChunkManager.TILE_SIZE;

    private ChunkManager chunkManager;
    private Array<BlockEntity> blockEntities;
    private Player player;

    public World() {
        chunkManager = new ChunkManager();
        blockEntities = new Array<>();
        spawnPlayer();
        player.setAcceleration(new Vector2(player.getAcceleration().x, GRAV_STRENGTH)); // Set player gravity
    }

    private void spawnPlayer() {
        // Calculate player spawn position
        float playerX = ((ChunkManager.CHUNKS_X / 2) * Chunk.CHUNK_SIZE) * ChunkManager.TILE_SIZE;
        // Get highest Y value of the player spawn X
        Chunk spawnChunk = chunkManager.getChunk((ChunkManager.CHUNKS_X / 2), ChunkManager.CHUNKS_Y - 1);
        int playerTx = (int)playerX / ChunkManager.TILE_SIZE;
        int highestY = spawnChunk.highestY[playerTx - (spawnChunk.getX() * Chunk.CHUNK_SIZE)];
        float playerY = (highestY + (((ChunkManager.CHUNKS_Y - 1)) * Chunk.CHUNK_SIZE) + 1) * ChunkManager.TILE_SIZE;
        player = new Player(playerX, playerY);
    }

    public void update() {
        blockEntities.clear();
        player.update();
        chunkManager.updateChunks(player.getPos().x, player.getPos().y);
        for (int i = -ChunkManager.TILE_SIZE * 10; i < ChunkManager.TILE_SIZE * 10; i+= ChunkManager.TILE_SIZE) {
            for (int j = -ChunkManager.TILE_SIZE * 10; j < ChunkManager.TILE_SIZE * 10; j+= ChunkManager.TILE_SIZE) {
                blockEntities.add(chunkManager.generateBlockEntity(player.getPos().x + i, player.getPos().y + j));
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

    public Player getPlayer() {
        return player;
    }
}
