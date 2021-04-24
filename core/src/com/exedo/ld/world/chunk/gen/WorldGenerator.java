package com.exedo.ld.world.chunk.gen;

import com.exedo.ld.world.chunk.Chunk;

public interface WorldGenerator {
    void generate(SimplexNoise noise, Chunk chunk);
    void generate(SimplexNoise noise, Chunk chunk, int x, int y);
}
