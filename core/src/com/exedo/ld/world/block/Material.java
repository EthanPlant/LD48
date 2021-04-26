package com.exedo.ld.world.block;

// Various material data

public enum Material {
    AIR(true, false),
    STONE(false, true),
    DIRT(false, true),
    WALL(false, false),
    WOOD(false, true),
    LEAF(true, true),
    PLANT(true, false),
    LIQUID(true, false);

    private final boolean transparent;
    private final boolean doesCollision;

    Material(boolean transparent, boolean doesCollision) {
        this.transparent = transparent;
        this.doesCollision = doesCollision;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public boolean doesCollide() {
        return doesCollision;
    }
}
