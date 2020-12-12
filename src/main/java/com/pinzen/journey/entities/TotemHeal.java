package com.pinzen.journey.entities;

import java.util.List;

public class TotemHeal extends Invocation {

    private int healingAmount, radius;
    private long healTime, healTimer;

    public TotemHeal(Entity owner, int healingAmount, int radius, long healTime) {
        super(owner);
        this.healingAmount = healingAmount > 0 ? healingAmount : 0;
        this.healTime = healTime;
        this.radius = radius;

        this.healTimer = 0;
        this.maximumLife = this.life = 20;
    }

    public void update(long delta, List<Entity> entities) {
        this.healTimer += delta;

        if (this.healTimer >= this.healTime) {
            this.healTimer = 0;
            for (Entity e : entities) {
                if (e == this || !this.isSameTeam(e))
                    continue;

                if (Math.abs(this.x - e.x) < radius && Math.abs(this.y - e.y) < radius)
                    e.heal(healingAmount);
            }
        }
    }
}
