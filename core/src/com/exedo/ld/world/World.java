package com.exedo.ld.world;

// Handles all the world simulation

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.exedo.ld.world.block.BlockEntity;
import com.exedo.ld.world.chunk.Chunk;
import com.exedo.ld.world.chunk.ChunkManager;
import com.exedo.ld.world.entity.Entity;
import com.exedo.ld.world.entity.Player;

public class World {
    public static final long RANDOM_SEED = System.currentTimeMillis();
    public static final float GRAV_STRENGTH = -10 * ChunkManager.TILE_SIZE;

    private ChunkManager chunkManager;
    private Array<BlockEntity> blockEntities;
    private Array<Entity> entitiesToRender;
    private Player player;

    public World() {
        chunkManager = new ChunkManager();
        blockEntities = new Array<>();
        entitiesToRender = new Array<>();
        spawnPlayer();
        chunkManager.updateChunks(player.getPos().x, player.getPos().y);
        entitiesToRender.add(player);
        player.setAcceleration(new Vector2(player.getAcceleration().x, GRAV_STRENGTH)); // Set player gravity
    }

    private void spawnPlayer() {
        // Calculate player spawn position
        float playerX = ((ChunkManager.CHUNKS_X / 2) * Chunk.CHUNK_SIZE) * ChunkManager.TILE_SIZE;
        // Get highest Y value of the player spawn X
        Chunk spawnChunk = chunkManager.getChunk((ChunkManager.CHUNKS_X / 2), ChunkManager.CHUNKS_Y - 1);
        int playerTx = (int)playerX / ChunkManager.TILE_SIZE;
        int highestY = spawnChunk.highestY[playerTx - (spawnChunk.getX() * Chunk.CHUNK_SIZE)];
        float playerY = (highestY + (((ChunkManager.CHUNKS_Y - 1)) * Chunk.CHUNK_SIZE) + 16) * ChunkManager.TILE_SIZE;
        player = new Player(playerX, playerY);
    }

    public void update() {
        // BlockEntity stuff
//        blockEntities.clear();
//        for (int i = 0; i < ChunkManager.TILE_SIZE * 3; i+= ChunkManager.TILE_SIZE) {
//            for (int j = 0; j <= ChunkManager.TILE_SIZE * 3; j+= ChunkManager.TILE_SIZE) {
//                blockEntities.add(chunkManager.generateBlockEntity(player.getPos().x + i, player.getPos().y + j));
//            }
//        }
        entitiesToRender.clear();
        populateBlockEntities((int) player.getPos().x / ChunkManager.TILE_SIZE - 10,
                (int) player.getPos().y / ChunkManager.TILE_SIZE - 10,
                (int) player.getPos().x / ChunkManager.TILE_SIZE + 10,
                (int) player.getPos().x / ChunkManager.TILE_SIZE + 10);

        // Player movement
        player.updateVelocity();
        checkPlayerCollision();
        player.updatePos();
        player.update();
        // Update loaded chunks
        chunkManager.updateChunks(player.getPos().x, player.getPos().y);
    }

    private void checkPlayerCollision() {
        // Copy player bounding box
        Rectangle box = new Rectangle(player.getBoundingBox());

        int startX;
        int startY;
        int endX;
        int endY;

        // Check horizontal
        startY = (int)player.getBoundingBox().y / ChunkManager.TILE_SIZE + 1;
        endY = (int)(player.getBoundingBox().y + player.getBoundingBox().height) / ChunkManager.TILE_SIZE + 1;
        if (player.getVelocity().x < 0) {
            startX = endX = (int)(player.getBoundingBox().x + (player.getVelocity().x * Gdx.graphics.getDeltaTime())) / ChunkManager.TILE_SIZE;
        } else {
            startX = endX = (int)(player.getBoundingBox().x + player.getBoundingBox().width + (player.getVelocity().x * Gdx.graphics.getDeltaTime())) / ChunkManager.TILE_SIZE;
        }
        populateBlockEntities(startX, startY, endX, endY);
        box.x += player.getVelocity().x * Gdx.graphics.getDeltaTime();
        for (BlockEntity b : blockEntities) {
            if (b == null) continue;
            if (b.isColliding(box)) {
                player.setVelocity(new Vector2(0, player.getVelocity().y));
                box.x = player.getBoundingBox().x;
                entitiesToRender.add(b);
                break;
            }
        }

        // Check vertical
        // Generate relevant block entities
        startX = (int)player.getBoundingBox().x / ChunkManager.TILE_SIZE;
        endX = (int)(player.getBoundingBox().x + player.getBoundingBox().width) / ChunkManager.TILE_SIZE;
        if (player.getVelocity().y < 0) {
            startY = endY = (int)(player.getBoundingBox().y + (player.getVelocity().x * Gdx.graphics.getDeltaTime())) / ChunkManager.TILE_SIZE;
        }
        else {
            startY = endY = (int)(player.getBoundingBox().y + player.getBoundingBox().height + (player.getVelocity().x * Gdx.graphics.getDeltaTime())) / ChunkManager.TILE_SIZE;
        }
        populateBlockEntities(startX, startY, endX, endY);
        box.y += player.getVelocity().y * Gdx.graphics.getDeltaTime();
        for (BlockEntity b : blockEntities) {
            if (b == null) continue;
            if (b.isColliding(box)) {
                player.setVelocity(new Vector2(player.getVelocity().x, 0));
                box.y = player.getBoundingBox().y;
                entitiesToRender.add(b);
                break;
            }
        }

        player.setPos(new Vector2(box.x - 3, box.y));
    }

    // Populates BlockEntities from tile coords
    private void populateBlockEntities(int startX, int startY, int endX, int endY) {
        blockEntities.clear();
        for (int i = startX; i <= endX; ++i) {
            for (int j = startY; j <= endY; ++j) {
                blockEntities.add(chunkManager.generateBlockEntity(i * ChunkManager.TILE_SIZE, j * ChunkManager.TILE_SIZE));
            }
        }
    }

    public void render(SpriteBatch batch, OrthographicCamera cam) {
        chunkManager.renderChunks(batch, cam);
        player.render(batch);
    }

    public void debugRender(ShapeRenderer renderer) {
        for (Entity e : entitiesToRender) {
            if (e == null) continue;
            Color c;
            switch (e.getClass().getSimpleName()) {
                case "Player":
                    c = Color.GREEN;
                    break;
                case "BlockEntity":
                    c = Color.RED;
                    break;
                default:
                    c = Color.BLACK;
            }
            e.drawBoundingBox(renderer, c);
        }
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public Player getPlayer() {
        return player;
    }
}
