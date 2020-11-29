package com.pinzen.journey.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.entities.Player;
import com.pinzen.journey.network.NMUpdateEntityPosition;
import com.pinzen.journey.network.NetworkMessage;

public class GameMap {

    private UUID uuid;
    private List<Entity> entities;
    private List<Player> players;

    public GameMap() {
        this.uuid = UUID.randomUUID();
        this.entities = new ArrayList<Entity>();
        this.players = new ArrayList<Player>();
    }

    public void update(long delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
        // Send messages
        List<NetworkMessage> messagesToSend = new ArrayList<NetworkMessage>();
        for (Entity e : entities) {
            NMUpdateEntityPosition msg = new NMUpdateEntityPosition();
            msg.prepare(e);
            messagesToSend.add(msg);
        }
        for (Player player : players) {
            for (NetworkMessage msg : messagesToSend) {
                player.sendMessage(msg);
            }
        }
    }

    public void addEntity(Entity e) {
        this.entities.add(e);
        if (e instanceof Player) {
            this.players.add((Player) e);
        }
    }

    public void removeEntity(Entity e) {
        this.entities.remove(e);
        if (e instanceof Player) {
            this.players.remove((Player) e);
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
