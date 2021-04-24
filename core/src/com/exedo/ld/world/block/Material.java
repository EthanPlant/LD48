package com.exedo.ld.world.block;

// Various material data

public enum Material {
    AIR(true),
    STONE(false),
    DIRT(false),
    WALL(false);

    private final boolean transparent;

    Material(boolean transparent) {
        this.transparent = transparent;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
