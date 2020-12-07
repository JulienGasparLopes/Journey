package com.pinzen.journey.entities;

import java.util.List;

public class TotemDamage extends Entity {

    public TotemDamage() {
        super();

        this.maximumLife = this.life = 20;
    }

    public void update(long delta, List<Entity> entities) {
        for (Entity e : entities) {
            if (e != this) {
                if (Math.abs(this.x - e.x) < 30 && Math.abs(this.y - e.y) < 30) {
                    e.damage(3);
                }
            }
        }
    }
}
