package com.exedo.ld.world.chunk;

// Manages all of the chunks in the world

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.chunk.gen.SimplexNoise;
import com.exedo.ld.world.chunk.gen.TerrainGenerator;

import java.util.Date;
import java.util.Random;

public class ChunkManager {
    public static int TILE_SIZE = 16; // Size of a block

    // World size of 50x20 chunks (800x320 blocks)
    private static int CHUNKS_X = 50;
    private static int CHUNKS_Y = 20;

    private Chunk[][] chunks = new Chunk[CHUNKS_X][CHUNKS_Y];
    private Chunk[][] nearbyChunks = new Chunk[5][5]; // Stores a 5x5 grid of chunks around the player to render

    private SimplexNoise noise = new SimplexNoise();
    private static final TerrainGenerator terrainGenerator = new TerrainGenerator();

    public ChunkManager() {
        noise.genGrad(System.currentTimeMillis());
        generateTerrain();
    }

    public void generateTerrain() {
        // Generate all of the chunks in the world
        for (int x = 0; x < CHUNKS_X; x++) {
            for (int y = 0; y < CHUNKS_Y; y++) {
                if (chunks[x][y] == null) { // Don't overwrite a chunk that already exists
                    chunks[x][y] = new Chunk(x, y);
                    // Generate chunks
                    if (y == CHUNKS_Y - 1) // Are we on the top layer of chunks?
                        terrainGenerator.generate(noise, chunks[x][y]);
                    else {
                        for (int i = 0; i < Chunk.CHUNK_SIZE; i++) {
                            for (int j = 0; j < Chunk.CHUNK_SIZE; j++) {
                                chunks[x][y].setBlock(i, j, BlockType.STONE);
                            }
                        }
                    }
                }
            }
        }
    }

    // Update the chunks near the player
    public void updateChunks(float playerX, float playerY) {
        // Check if we need to update the nearby chunks
        if (nearbyChunks[2][2] == null || getChunkFromPos(playerX, playerY) != nearbyChunks[2][2]) {
            // Grab the coordinates of the player's current chunk
            int chunkX = (int)(playerX / TILE_SIZE) / Chunk.CHUNK_SIZE;
            int chunkY = (int)(playerY / TILE_SIZE) / Chunk.CHUNK_SIZE;
            // Update all of the nearby chunks
            for (int i = -2; i < 2; i++) {
                for (int j = -2; j < 2; j++) {
                    nearbyChunks[i + 2][j + 2] = getChunk(chunkX + i, chunkY + j);
                }
            }
            // Don't look at this
//            nearbyChunks[0][0] = getChunk(chunkX - 2, chunkY - 2); // Top left
//            nearbyChunks[0][1] = getChunk(chunkX - 2, chunkY - 1);
//            nearbyChunks[0][2] = getChunk(chunkX - 2, chunkY);
//            nearbyChunks[0][3] = getChunk(chunkX - 2, chunkY + 1);
//            nearbyChunks[0][4] = getChunk(chunkX - 2, chunkY + 2); // Bottom left
//
//            nearbyChunks[1][0] = getChunk(chunkX - 1, chunkY - 2);
//            nearbyChunks[1][1] = getChunk(chunkX - 1, chunkY - 1);
//            nearbyChunks[1][2] = getChunk(chunkX - 1, chunkY);
//            nearbyChunks[1][3] = getChunk(chunkX - 1, chunkY + 1);
//            nearbyChunks[1][4] = getChunk(chunkX - 1, chunkY + 2);
//
//            nearbyChunks[2][0] = getChunk(chunkX, chunkY - 2);
//            nearbyChunks[2][1] = getChunk(chunkX, chunkY - 1);
//            nearbyChunks[2][2] = getChunk(chunkX, chunkY); // Centre
//            nearbyChunks[2][3] = getChunk(chunkX, chunkY + 1);
//            nearbyChunks[2][4] = getChunk(chunkX, chunkY + 2);
//
//            nearbyChunks[3][0] = getChunk(chunkX + 1, chunkY - 2);
//            nearbyChunks[3][1] = getChunk(chunkX + 1, chunkY - 1);
//            nearbyChunks[3][2] = getChunk(chunkX + 1, chunkY);
//            nearbyChunks[3][3] = getChunk(chunkX + 1, chunkY + 1);
//            nearbyChunks[3][4] = getChunk(chunkX + 1, chunkY + 2);
//
//            nearbyChunks[4][0] = getChunk(chunkX + 2, chunkY - 2); // Top right
//            nearbyChunks[4][1] = getChunk(chunkX + 2, chunkY - 1);
//            nearbyChunks[4][2] = getChunk(chunkX + 2, chunkY);
//            nearbyChunks[4][3] = getChunk(chunkX + 2, chunkY + 1);
//            nearbyChunks[4][4] = getChunk(chunkX + 2, chunkY + 2); // Bottom right
        }
    }

    public void renderChunks(SpriteBatch sb, OrthographicCamera cam) {
        // TODO Only render chunks in frame
        // I don't feel like figuring out the logic
        // It shouldn't affect performance much anyway as any non-visible blocks won't be rendered
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (nearbyChunks[x][y] != null) // Don't need to render a non-existent chunk
                    nearbyChunks[x][y].renderChunk(sb, cam);
            }
        }
    }

    // Get a chunk given it's chunk coords
    public Chunk getChunk(int x, int y) {
        if (x >= 0 && y >= 0 && x < CHUNKS_X && y < CHUNKS_Y)
            return chunks[x][y];
        return null; // Invalid chunk coords
    }

    public Chunk getChunkFromPos(float x, float y) {
        // Get tile coords
        int tx = (int) x / TILE_SIZE;
        int ty = (int) y / TILE_SIZE;

        // Convert to chunk coords and grab chunk
        return getChunk(tx / Chunk.CHUNK_SIZE, ty / Chunk.CHUNK_SIZE);
    }
}
