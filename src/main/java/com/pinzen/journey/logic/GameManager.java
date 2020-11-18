package com.pinzen.journey.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pinzen.journey.network.NMUpdateEntityPosition;

import org.java_websocket.WebSocket;

public class GameManager extends Thread {

    private boolean running;
    private Map<UUID, User> users;

    public GameManager() {
        this.users = new HashMap<UUID, User>();
        this.running = false;
    }

    public void start() {
        this.running = true;
        super.start();
    }

    public void run() {
        while (this.running) {
            try {
                for (User user : users.values()) {
                    user.x += user.vx;
                    user.y += user.vy;
                    NMUpdateEntityPosition msg = new NMUpdateEntityPosition();
                    msg.prepare(user);
                    for (User userToSend : users.values()) {
                        userToSend.sendMessage(msg);
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    public void stopManager() {
        this.running = false;
    }

    public UUID addUser(WebSocket conn) {
        User newUser = new User(conn);
        users.put(newUser.getUuid(), newUser);
        return newUser.getUuid();
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public User getUser(UUID userUuid) {
        return users.get(userUuid);
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }
}
