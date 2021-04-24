package com.exedo.ld.world.chunk;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.Utils;
import com.exedo.ld.world.block.BlockManager;
import com.exedo.ld.world.block.BlockType;

// Holds data for a chunk

public class Chunk {
    public static int CHUNK_SIZE = 25;
    private BlockType[][] blocks = new BlockType[CHUNK_SIZE][CHUNK_SIZE];

    // Coordinates of this chunk
    private int startX, startY;

    public Chunk(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;

        // Just fill all chunks with air by default
        for(int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = BlockType.AIR;
            }
        }
    }

    public void renderChunk(SpriteBatch sb, OrthographicCamera cam) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                // Determine tile coords
                float tx = x * ChunkManager.TILE_SIZE + (getX() * CHUNK_SIZE * ChunkManager.TILE_SIZE);
                float ty = y * ChunkManager.TILE_SIZE + (getY() * CHUNK_SIZE * ChunkManager.TILE_SIZE);
                // Only render tiles on screen to save performance
                if (Utils.isOnScreen(cam.position.x, cam.position.y, tx, ty, 16)) {
                    // Grab block to render
                    BlockType type = blocks[x][y];
                    // Only render opaque blocks
                    if (type != null && type != BlockType.AIR)
                        sb.draw(BlockManager.getBlock(blocks[x][y]).getBlockTexture(), tx, ty); // 16 pixels per block
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

    public void setBlock(int x, int y, BlockType type) {
        if (x >= 0 && y >= 0 && x < CHUNK_SIZE && y < CHUNK_SIZE) {
            blocks[x][y] = type;
        }
    }
}
