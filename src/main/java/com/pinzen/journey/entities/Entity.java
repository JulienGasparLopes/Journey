package com.pinzen.journey.entities;

import java.util.UUID;

public abstract class Entity {

    private UUID uuid;

    protected float x, y, vx, vy; // TODO use Vertex

    public Entity() {
        this.uuid = UUID.randomUUID();
    }

    public void update(long delta) {
        float deltaNormalized = delta / 10f;
        float dx = this.vx * deltaNormalized;
        float dy = this.vy * deltaNormalized;
        this.x += dx;
        this.y += dy;
    }

    public void setSpeed(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public int[] getPosition() {
        return new int[] { (int) x, (int) y };
    }

    public int[] getSpeed() {
        return new int[] { (int) vx, (int) vy };
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
