package com.pinzen.journey.entities;

import java.util.List;

public class TotemDamage extends Invocation {

    private int damage, radius;
    private long attackTime, attackTimer;

    public TotemDamage(Entity owner, int damage, int radius, long attackTime) {
        super(owner);
        this.damage = damage > 0 ? damage : 0;
        this.attackTime = attackTime;
        this.radius = radius;

        this.attackTimer = 0;
        this.maximumLife = this.life = 20;
    }

    public void update(long delta, List<Entity> entities) {
        this.attackTimer += delta;

        if (this.attackTimer >= this.attackTime) {
            this.attackTimer = 0;

            for (Entity e : entities) {
                if (this.isSameTeam(e))
                    continue;

                if (Math.abs(this.x - e.x) < radius && Math.abs(this.y - e.y) < radius)
                    e.damage(damage);
            }
        }
    }
}
