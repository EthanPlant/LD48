package com.exedo.ld.world.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.exedo.ld.LudumDare;
import com.exedo.ld.world.chunk.ChunkManager;

public class Player extends Entity {
    private Texture texture;
    private boolean xFlip = false;

    public Player(float x, float y) {
        super(x, y);
        this.boundingBox.setPosition(x + 3, y);
        this.boundingBox.setWidth(10);
        this.boundingBox.setHeight(2 * ChunkManager.TILE_SIZE);
        texture = LudumDare.assets.get("player.png", Texture.class);
    }

    @Override
    public void update() {
        super.update();
        if (velocity.x < 0) xFlip = true;
        else if (velocity.x > 0) xFlip = false;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, texture.getWidth(), texture.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), xFlip, false);
    }
}
