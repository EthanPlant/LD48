package com.exedo.ld.world.block;

import com.badlogic.gdx.math.Rectangle;
import com.exedo.ld.world.entity.Entity;

public class BlockEntity extends Entity {
    private Material mat;

    public BlockEntity(float x, float y, Material mat) {
        super(x, y);
        this.mat = mat;
    }

    // Blocks don't need an update
    // Might change later for stuff like liquids
    @Override
    public void update() {}

    @Override
    public boolean isColliding(Entity other) {
        if (mat.doesCollide()) return super.isColliding(other);
        else return false;
    }

    @Override
    public boolean isColliding(Rectangle other) {
        if (mat.doesCollide()) return super.isColliding(other);
        else return false;
    }
}
