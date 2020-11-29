package com.pinzen.journey.logic;

import java.util.UUID;

import com.pinzen.journey.entities.Player;
import com.pinzen.journey.network.NetworkMessage;

import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;

public class User {

    private UUID uuid;
    private WebSocket connection;

    public Player player;
    public UUID currentMapUuid;
    private boolean keyLeftArrow, keyRightArrow, keyUpArrow, keyDownArrow; // TODO create object to store + use enum

    public User(WebSocket conn) {
        this.uuid = UUID.randomUUID();
        this.connection = conn;
        this.keyLeftArrow = this.keyDownArrow = this.keyRightArrow = this.keyUpArrow = false;

        this.player = new Player(this);
    }

    public void update(long delta) {
        this.player.setSpeed(keyLeftArrow ? -1 : keyRightArrow ? 1 : 0, keyDownArrow ? -1 : keyUpArrow ? 1 : 0);
    }

    public void updateKey(int keyCode, boolean isDown) {
        if (keyCode == 1) { // UP
            this.keyUpArrow = isDown;
        } else if (keyCode == 3) { // DOWN
            this.keyDownArrow = isDown;
        } else if (keyCode == 2) { // RIGHT
            this.keyRightArrow = isDown;
        } else if (keyCode == 4) { // LEFT
            this.keyLeftArrow = isDown;
        }
    }

    public void sendMessage(NetworkMessage msg) {
        try {
            this.connection.send(msg.encode());
        } catch (WebsocketNotConnectedException e) {
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
