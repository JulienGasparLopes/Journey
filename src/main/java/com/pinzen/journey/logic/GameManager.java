package com.pinzen.journey.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.java_websocket.WebSocket;

public class GameManager extends Thread {

    public static int UPDATE_PER_SECONDS = 100;

    private boolean running;

    private Map<UUID, User> users;
    private Map<UUID, GameMap> maps; // TODO add array with active maps uuid

    private UUID defaultMapUuid;

    public GameManager() {
        this.users = new HashMap<UUID, User>();
        this.maps = new HashMap<UUID, GameMap>();
        this.running = false;

        GameMap map = new GameMap();
        this.defaultMapUuid = map.getUuid();
        this.maps.put(map.getUuid(), map);
    }

    public void start() {
        this.running = true;
        super.start();
    }

    public void run() {
        long lastUpdate = System.currentTimeMillis();
        long delta;
        while (this.running) {
            delta = System.currentTimeMillis() - lastUpdate;
            if (delta > (1000 / UPDATE_PER_SECONDS)) {
                for (GameMap map : maps.values()) {
                    map.update(delta);
                }
                lastUpdate = System.currentTimeMillis();
            }
        }
    }

    public void stopManager() {
        this.running = false;
    }

    public UUID addUser(WebSocket conn) {
        User newUser = new User(conn);
        users.put(newUser.getUuid(), newUser);
        GameMap map = this.maps.get(this.defaultMapUuid);
        map.addEntity(newUser.player);
        newUser.currentMapUuid = map.getUuid();
        return newUser.getUuid();
    }

    public User removeUser(UUID uuid) {
        User userRemoved = users.remove(uuid);
        if (userRemoved != null) {
            this.maps.get(userRemoved.currentMapUuid).removeEntity(userRemoved.player);
        }
        return userRemoved;
    }

    public User getUser(UUID userUuid) {
        return users.get(userUuid);
    }

    public Collection<User> getUsers() {
        return this.users.values();
    }
}
