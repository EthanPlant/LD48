package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.Utils;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.Chunk;

public class TreeGenerator {
    public static void generateTree(Chunk chunk, int x, int y) {
        int randomNum = Utils.getRandomNumber(1, 4);
        int leaves;
        int stem;

        switch (randomNum) {
            case 2:
                stem = 4;
                leaves = Utils.getRandomNumber(1, 3);
                break;
            case 3:
                stem = 5;
                leaves = Utils.getRandomNumber(1, 4);
                break;
            case 4:
                stem = 6;
                leaves = Utils.getRandomNumber(1, 5);
                break;
            default:
                stem = 3;
                leaves = Utils.getRandomNumber(1, 2);
                break;
        }

        // Add stem
        for (int i = 0; i < stem; i++) {
            chunk.setBlock(x, y + i, BlockType.LOG);
        }

        // Add leaves
        for (int i = 1; i <= leaves; i++) {
            chunk.setBlock(x - 1, y + stem - i, BlockType.LEAF);
            chunk.setBlock(x + 1, y + stem - i, BlockType.LEAF);
        }
        chunk.setBlock(x, y + stem, BlockType.LEAF);
        chunk.highestY[x] = y + stem;
    }
}
