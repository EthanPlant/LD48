package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.Utils;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;

// Generates terrain on the top layer of chunks

public class TerrainGenerator implements WorldGenerator {
    private static final int WATER_LEVEL = 15;

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
        chunk.highestY[x] = finalY;

        // Set blocks
        chunk.setBlock(x, finalY, BlockType.GRASS);
        chunk.setWall(x, finalY, BlockType.DIRT_WALL);

        // Water check
        if (finalY < WATER_LEVEL) {
            chunk.setBlock(x, finalY, BlockType.DIRT);
            for(int j = finalY; j <= WATER_LEVEL; j++){
                chunk.setBlock(x, j, BlockType.WATER);
                chunk.setWall(x, j, BlockType.DIRT_WALL);
            }
        }

        // Determine whether to draw a tree
        // Dont draw plants in water
        float treeFreq = 1.0f / Chunk.CHUNK_SIZE;
        float treeValLast = Math.abs(noise.generate((totalX - 1) * treeFreq, (totalX - 1) * treeFreq, 3, .75f, 1f));
        float treeVal = Math.abs(noise.generate((totalX) * treeFreq, (totalX) * treeFreq, 3, .75f, 1f));
        float treeValNext = Math.abs(noise.generate((totalX + 1) * treeFreq, (totalX + 1) * treeFreq, 3, .75f, 1f));

        // Plant check
        if (Utils.getRandomNumber(1, 10) == 1 && finalY > WATER_LEVEL) {
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

        if (treeVal > treeValLast && treeVal > treeValNext && finalY > WATER_LEVEL) { // Peak, spawn tree
            int treeY = finalY + 1;
            TreeGenerator.generateTree(chunk, x, treeY);
        }


        for (int j = 0; j < finalY; j++) {
            if (finalY - j <= 3) {
                float dirtFreq = 1.0f / Chunk.CHUNK_SIZE;
                float dirtVal = Math.abs(noise.generate(x * dirtFreq, j * dirtFreq, 2, .5f, 1f));
                if (dirtVal > .2f) {
                    chunk.setBlock(x, j, BlockType.DIRT);
                    chunk.setWall(x, j, BlockType.DIRT_WALL);
                }
                else {
                    chunk.setBlock(x, j, BlockType.STONE);
                    chunk.setWall(x, j, BlockType.DIRT_WALL);
                }
            }
            else
                oreGenerator.generate(noise, chunk, x, j);
        }
    }
}
