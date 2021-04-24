package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;

// Generates terrain on the top layer of chunks

public class TerrainGenerator implements WorldGenerator {
    private OreGenerator oreGenerator;

    public TerrainGenerator(OreGenerator oreGenerator) {
        this.oreGenerator = oreGenerator;
    }

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
        chunk.setBlock(x, finalY, BlockType.GRASS);
        for (int j = 0; j < finalY; j++) {
            if (finalY - j <= 3) {
                float dirtFreq = 1.0f / Chunk.CHUNK_SIZE;
                float dirtVal = Math.abs(noise.generate(x * dirtFreq, y * dirtFreq, 2, .5f, 1f));
                if (dirtVal > .2f)
                    chunk.setBlock(x, j, BlockType.DIRT);
                else chunk.setBlock(x, j, BlockType.STONE);
            }
            else
                oreGenerator.generate(noise, chunk, x, j);
        }
    }
}
