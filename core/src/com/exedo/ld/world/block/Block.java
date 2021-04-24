package com.exedo.ld.world.block;

// Block template class

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Block {
    protected final BlockType type;
    protected TextureRegion texture;
    protected Material material;

    public Block(BlockType type, TextureRegion texture, Material material) {
        this.type = type;
        this.texture = texture;
        this.material = material;
    }

    public TextureRegion getBlockTexture() {
        return texture;
    }

    public BlockType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isWall() {
        return false;
    }
}
