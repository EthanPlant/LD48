package com.exedo.ld.world.chunk;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.world.block.BlockManager;
import com.exedo.ld.world.block.BlockType;

public class Chunk {
    public static int CHUNK_SIZE = 16;
    private BlockType[][] blocks = new BlockType[16][16];

    public Chunk() {
        // Just fill all chunks with stone for now
        for(int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = BlockType.STONE;
            }
        }
    }

    public void renderChunk(SpriteBatch sb) {
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                sb.draw(BlockManager.getBlock(blocks[x][y]).getBlockTexture(), x * 16, y * 16); // 16 pixels per block
            }
        }
    }
}
