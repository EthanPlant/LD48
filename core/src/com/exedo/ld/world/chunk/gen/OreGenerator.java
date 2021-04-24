package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.world.World;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;

import java.util.Random;

// Generates ores and dirt patches underground

public class OreGenerator implements WorldGenerator {
    private Random oreChooser;

    public OreGenerator() {
        oreChooser = new Random(World.RANDOM_SEED);
    }

    @Override
    public void generate(SimplexNoise noise, Chunk chunk) {
        for (int y = 0; y < Chunk.CHUNK_SIZE; y++) {
            for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                generate(noise, chunk, x, y);
            }
        }
    }

    @Override
    public void generate(SimplexNoise noise, Chunk chunk, int x, int y) {
        int totalX = x + (Chunk.CHUNK_SIZE * chunk.getX());
        int totalY = (Chunk.CHUNK_SIZE * chunk.getY()) - y;

        float caveFreq = 1.0f / (Chunk.CHUNK_SIZE / 3.85f);
        float caveVal = noise.generate(totalX * caveFreq, totalY * caveFreq, 6, .25f, 1f);

        if (caveVal < -0.3f || caveVal > 0.3f) {
            chunk.setBlock(x, y, BlockType.STONE);
            // Attempt to spawn some ores
            float oreFreq = 1 / (Chunk.CHUNK_SIZE / 3.5f);
            float oreVal = noise.generate(x * oreFreq, y * oreFreq, 2, .9f, 1f);

            if (oreVal > 0.5f) {
                float ore = oreChooser.nextFloat();
                if (ore > 0.9) chunk.setBlock(x, y, BlockType.DIAMOND);
                else if (ore > 0.5) chunk.setBlock(x, y, BlockType.COPPER);
                else chunk.setBlock(x, y, BlockType.COAL);
            } else {
                // Attempt to spawn some dirt
                float dirtFreq = 1.0f / (Chunk.CHUNK_SIZE);
                float dirtVal = noise.generate(x * dirtFreq, y * dirtFreq, 2, .5f, 1f);
                if (dirtVal > .3f) {
                    chunk.setBlock(x, y, BlockType.DIRT);
                } else chunk.setBlock(x, y, BlockType.STONE);
            }
        } else {
            chunk.setBlock(x, y, BlockType.STONE_WALL);
        }
    }
}
