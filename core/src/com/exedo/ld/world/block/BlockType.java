package com.exedo.ld.world.block;

public enum BlockType {
    AIR(0), STONE(1);

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
