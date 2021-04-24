package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.Utils;
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

        // Determine whether to draw a tree
        float treeFreq = 1.0f / Chunk.CHUNK_SIZE;
        float treeValLast = Math.abs(noise.generate((totalX - 1) * treeFreq, (totalX - 1) * treeFreq, 3, .75f, 1f));
        float treeVal = Math.abs(noise.generate((totalX) * treeFreq, (totalX) * treeFreq, 3, .75f, 1f));
        float treeValNext = Math.abs(noise.generate((totalX + 1) * treeFreq, (totalX + 1) * treeFreq, 3, .75f, 1f));

        if (Utils.getRandomNumber(1, 10) == 1) {
            int plantToSpawn = Utils.getRandomNumber(1, 3);
            switch (plantToSpawn) {
                case 1:
                    chunk.setBlock(x, finalY + 1, BlockType.ROSE);
                    break;
                case 2:
                    chunk.setBlock(x, finalY + 1, BlockType.DANDELION);
                    break;
                case 3:
                    chunk.setBlock(x, finalY + 1, BlockType.BUSH);
                    break;
            }
        }

        if (treeVal > treeValLast && treeVal > treeValNext) { // Peak, spawn tree
            int treeY = finalY + 1;
            TreeGenerator.generateTree(chunk, x, treeY);
        }

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
