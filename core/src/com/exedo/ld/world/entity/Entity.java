package com.exedo.ld.world.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.exedo.ld.world.chunk.ChunkManager;

public abstract class Entity {
    protected Vector2 pos;
    protected Vector2 velocity = Vector2.Zero;
    protected Vector2 acceleration = Vector2.Zero;

    protected Rectangle boundingBox;

    public Entity(float x, float y) {
        pos = new Vector2(x, y);
        boundingBox = new Rectangle(x, y, ChunkManager.TILE_SIZE, ChunkManager.TILE_SIZE);
    }

    public abstract void update();

    public void updateVelocity() {
        velocity.add(acceleration.cpy().scl(Gdx.graphics.getDeltaTime()));
    }

    public void updatePos() {
        pos.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime()));
        boundingBox.setPosition(new Vector2(boundingBox.getX() + velocity.cpy().scl(Gdx.graphics.getDeltaTime()).x,
                boundingBox.getY() + velocity.cpy().scl(Gdx.graphics.getDeltaTime()).y));
    }

    public boolean isColliding(Entity other) {
        return isColliding(other.boundingBox);
    }

    public boolean isColliding(Rectangle other) {
        return boundingBox.overlaps(other);
    }

    public void drawBoundingBox(ShapeRenderer renderer, Color color) {
        renderer.setColor(color);
        renderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
        boundingBox.setPosition(pos);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 accel) {
        acceleration = accel;
    }
}
