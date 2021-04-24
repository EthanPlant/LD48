package com.exedo.ld.world.block;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.exedo.ld.LudumDare;

import java.util.HashMap;

public class BlockManager {
    private static final HashMap<BlockType, Block> blocks = new HashMap<BlockType, Block>();
    private static final BlockType[] blockTypeArray = BlockType.values();

    static {
        blocks.put(BlockType.AIR, new Block(BlockType.AIR, null, Material.AIR));
        blocks.put(BlockType.STONE, new Block((BlockType.STONE), LudumDare.assets.get("blocks.atlas", TextureAtlas.class).findRegion("stone"), Material.STONE));
    }

    public static Block getBlock(BlockType type) {
        return blocks.get(type);
    }
}
