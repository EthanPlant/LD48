package com.exedo.ld.world.chunk;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.Utils;
import com.exedo.ld.world.block.BlockManager;
import com.exedo.ld.world.block.BlockType;

// Holds data for a chunk

public class Chunk {
    public static int CHUNK_SIZE = 50;
    private BlockType[][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
    private BlockType[][] walls = new BlockType[CHUNK_SIZE][CHUNK_SIZE];
    public int[] highestY = new int[CHUNK_SIZE];

    // Coordinates of this chunk
    private int startX, startY;

    public Chunk(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;

        // Just fill all chunks with air by default
        for(int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                blocks[i][j] = BlockType.AIR;
                walls[i][j] = BlockType.AIR;
            }
            highestY[i] = CHUNK_SIZE;
        }
    }

    public void renderChunk(SpriteBatch sb, OrthographicCamera cam) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                // Determine tile coords
                float tx = x * ChunkManager.TILE_SIZE + (getX() * CHUNK_SIZE * ChunkManager.TILE_SIZE);
                float ty = y * ChunkManager.TILE_SIZE + (getY() * CHUNK_SIZE * ChunkManager.TILE_SIZE);
                // Only render tiles on screen to save performance
                if (Utils.isOnScreen(cam.position.x, cam.position.y, tx, ty, ChunkManager.TILE_SIZE)) {
                    // Grab block to render
                    BlockType type = getBlock(x, y);
                    // Only draw its wall if a block has transparency
                    if (type == null || type == BlockType.AIR || BlockManager.getBlock(type).getMaterial().isTransparent()) {
                        BlockType wallType = getWall(x, y);
                        if (wallType != null && wallType != BlockType.AIR)
                            sb.draw(BlockManager.getBlock(wallType).getBlockTexture(), tx, ty);
                    }
                    // Draw block
                    if (type != null && type != BlockType.AIR)
                        sb.draw(BlockManager.getBlock(type).getBlockTexture(), tx, ty);
                }
            }
        }
    }

    public int getX() {
        return startX;
    }

    public int getY() {
        return startY;
    }

    public BlockType getBlock(int x, int y) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            return blocks[x][y];
        }
        return BlockType.AIR;
    }

    public BlockType getWall(int x, int y) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            return walls[x][y];
        }
        return BlockType.AIR;
    }

    public void setBlock(int x, int y, BlockType type) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            blocks[x][y] = type;
        }
    }

    public void setWall(int x, int y, BlockType type) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            walls[x][y] = type;
        }
    }
}
