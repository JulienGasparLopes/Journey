package com.pinzen.journey.entities;

import com.pinzen.journey.logic.User;
import com.pinzen.journey.network.NetworkMessage;

public class Player extends Entity {

    private User user;

    public Player(User user) {
        this.user = user;

        this.x = 20;
        this.y = 20;
    }

    @Override
    public void update(long delta) {
        this.user.update(delta);
        super.update(delta);
    }

    public void sendMessage(NetworkMessage msg) {
        this.user.sendMessage(msg);
    }
}
