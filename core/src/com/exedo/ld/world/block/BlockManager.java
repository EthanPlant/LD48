package com.exedo.ld.world.block;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.exedo.ld.LudumDare;
import com.exedo.ld.world.block.special.Wall;

import java.util.HashMap;

public class BlockManager {
    private static final HashMap<BlockType, Block> blocks = new HashMap<BlockType, Block>();
    private static final BlockType[] blockTypeArray = BlockType.values();

    static {
        blocks.put(BlockType.AIR, new Block(BlockType.AIR, null, Material.AIR));
        blocks.put(BlockType.STONE, new Block((BlockType.STONE), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("stone"), Material.STONE));
        blocks.put(BlockType.DIRT, new Block((BlockType.DIRT), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("dirt"), Material.DIRT));
        blocks.put(BlockType.GRASS, new Block((BlockType.GRASS), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("grass"), Material.DIRT));
        blocks.put(BlockType.COAL, new Block((BlockType.COAL), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("coal"), Material.STONE));
        blocks.put(BlockType.COPPER, new Block((BlockType.COPPER), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("copper"), Material.STONE));
        blocks.put(BlockType.DIAMOND, new Block((BlockType.DIAMOND), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("diamond"), Material.STONE));
        blocks.put(BlockType.STONE_WALL, new Wall((BlockType.STONE_WALL), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("stone-wall")));
        blocks.put(BlockType.LOG, new Block((BlockType.LOG), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("log"), Material.WOOD));
        blocks.put(BlockType.LEAF, new Block((BlockType.LEAF), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("leaf"), Material.LEAF));
        blocks.put(BlockType.ROSE, new Block((BlockType.ROSE), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("rose"), Material.PLANT));
        blocks.put(BlockType.DANDELION, new Block((BlockType.DANDELION), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("dandelion"), Material.PLANT));
        blocks.put(BlockType.BUSH, new Block((BlockType.BUSH), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("bush"), Material.PLANT));
        blocks.put(BlockType.WATER, new Block((BlockType.WATER), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("water"), Material.LIQUID));
        blocks.put(BlockType.DIRT_WALL, new Wall((BlockType.DIRT_WALL), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("dirt-wall")));

    }

    public static Block getBlock(BlockType type) {
        return blocks.get(type);
    }
}
