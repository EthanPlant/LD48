package com.exedo.ld.world.block.special;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.exedo.ld.world.block.Block;
import com.exedo.ld.world.block.BlockType;
import com.exedo.ld.world.block.Material;

public class Wall extends Block {

    public Wall(BlockType type, TextureRegion texture) {
        super(type, texture, Material.WALL);
    }

    @Override
    public boolean isWall() {
        return true;
    }
}
