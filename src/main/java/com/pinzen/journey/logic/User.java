package com.pinzen.journey.logic;

import java.util.UUID;

import com.pinzen.journey.network.NetworkMessage;

import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;

public class User {

    private UUID uuid;
    private WebSocket connection;

    // TODO: user a class Player to store these
    public int x = 20, y = 20, vx = 0, vy = 0;

    public User(WebSocket conn) {
        this.uuid = UUID.randomUUID();
        this.connection = conn;
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
