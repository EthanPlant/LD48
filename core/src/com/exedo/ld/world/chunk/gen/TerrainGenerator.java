package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;

public class TerrainGenerator implements WorldGenerator {
    @Override
    public void generate(SimplexNoise noise, Chunk chunk) {
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            generate(noise, chunk, x, x);
        }
    }

    @Override
    public void generate(SimplexNoise noise, Chunk chunk, int x, int y) {
        //System.out.println("Hi");
        int totalX = x + (Chunk.CHUNK_SIZE * chunk.getX());
        float freq = 1.0f / (Chunk.CHUNK_SIZE * 1.25f);
        float i = noise.generate(totalX * freq, totalX * freq, 6, .25f, 1);
        int finalY = (int) (i * (Chunk.CHUNK_SIZE / 2)) + (Chunk.CHUNK_SIZE / 2) - 1;

        // Set blocks
        chunk.setBlock(x, finalY, BlockType.STONE);
        for (int j = 0; j < finalY; j++) {
            chunk.setBlock(x, j, BlockType.STONE);
        }
    }
}
