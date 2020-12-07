package com.pinzen.journey.entities;

import java.util.List;
import java.util.UUID;

public abstract class Entity {

    private UUID uuid;

    protected float x, y, vx, vy; // TODO use Vertex
    protected int maximumLife, life;

    public Entity() {
        this.uuid = UUID.randomUUID();
        this.maximumLife = this.life = 1;
    }

    public void update(long delta, List<Entity> entities) {
        float deltaNormalized = delta / 10f;
        float dx = this.vx * deltaNormalized;
        float dy = this.vy * deltaNormalized;
        this.x += dx;
        this.y += dy;
    }

    /**
     * Apply damage to Entity and return a boolean telling if the target is dead
     * 
     * @param dmg
     * @return
     */
    public boolean damage(int dmg) {
        this.life -= dmg;
        if (this.life > this.maximumLife) {
            this.life = this.maximumLife;
        }
        return this.isDead();
    }

    public boolean isDead() {
        return this.life <= 0;
    }

    public int getLife() {
        return this.life;
    }

    public int getMaximumLife() {
        return this.maximumLife;
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
