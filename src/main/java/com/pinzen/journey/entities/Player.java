package com.pinzen.journey.entities;

import java.util.List;

import com.pinzen.journey.logic.User;
import com.pinzen.journey.network.NetworkMessage;

public class Player extends Entity {

    private User user;

    public Player(User user) {
        this.user = user;

        this.x = 20;
        this.y = 20;
        this.maximumLife = this.life = 100;
    }

    @Override
    public void update(long delta, List<Entity> entities) {
        this.user.update(delta);
        super.update(delta, entities);
    }

    public void sendMessage(NetworkMessage msg) {
        this.user.sendMessage(msg);
    }
}
