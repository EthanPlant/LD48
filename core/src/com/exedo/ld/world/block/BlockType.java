package com.exedo.ld.world.block;

public enum BlockType {
    AIR(0),
    STONE(1),
    DIRT(2),
    GRASS(3),
    COAL(4),
    COPPER(5),
    DIAMOND(6),
    STONE_WALL(7),
    LOG(8),
    LEAF(9),
    ROSE(10),
    DANDELION(11),
    BUSH(12);

    private static final BlockType[] blockTypeArray = values();
    private final int id;

    BlockType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BlockType getBlockTypeFromId(int id) {
        for (BlockType type : blockTypeArray) {
            if (type.id == id) {
                return type;
            }
        }
        return AIR;
    }
}
