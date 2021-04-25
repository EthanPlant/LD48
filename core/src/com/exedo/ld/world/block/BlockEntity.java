package com.exedo.ld.world.block;

import com.exedo.ld.world.entity.Entity;

public class BlockEntity extends Entity {
    private Material mat;

    public BlockEntity(float x, float y, Material mat) {
        super(x, y);
        this.mat = mat;
    }
}
