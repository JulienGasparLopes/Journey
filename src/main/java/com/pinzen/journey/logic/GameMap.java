package com.pinzen.journey.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.entities.Player;
import com.pinzen.journey.network.NMRemoveEntities;
import com.pinzen.journey.network.NMUpdateEntities;
import com.pinzen.journey.network.NetworkMessage;

public class GameMap {

    private UUID uuid;
    private List<Entity> entities, entitiesToAdd, entitiesToRemove; // TODO : add ThreadLock
    private List<Player> players, playersToAdd, playersToRemove;

    public GameMap() {
        this.uuid = UUID.randomUUID();
        this.entities = new ArrayList<Entity>();
        this.entitiesToAdd = new ArrayList<Entity>();
        this.entitiesToRemove = new ArrayList<Entity>();
        this.players = new ArrayList<Player>();
        this.playersToAdd = new ArrayList<Player>();
        this.playersToRemove = new ArrayList<Player>();
    }

    public void update(long delta) {
        for (Entity e : entities) {
            e.update(delta, entities);
        }
        // Update entities and remove dead ones
        List<NetworkMessage> messagesToSend = new ArrayList<NetworkMessage>();
        NMUpdateEntities currentNMUpdateEntities = new NMUpdateEntities();
        NMRemoveEntities currentNMRemoveEntities = new NMRemoveEntities();
        for (Entity e : entities) {
            if (e.isDead()) {
                entitiesToRemove.add(e);
            } else {
                if (!currentNMUpdateEntities.addEntity(e)) {
                    messagesToSend.add((currentNMUpdateEntities));
                    currentNMUpdateEntities = new NMUpdateEntities();
                    currentNMUpdateEntities.addEntity(e);
                }
            }
        }
        if (currentNMUpdateEntities.entitiesNumber() > 0) {
            messagesToSend.add(currentNMUpdateEntities);
        }
        for (Entity e : entitiesToRemove) {
            if (!currentNMRemoveEntities.addEntity(e)) {
                messagesToSend.add((currentNMRemoveEntities));
                currentNMRemoveEntities = new NMRemoveEntities();
                currentNMRemoveEntities.addEntity(e);
            }
        }
        if (currentNMRemoveEntities.entitiesNumber() > 0) {
            messagesToSend.add(currentNMRemoveEntities);
        }
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();
        // Send messages
        for (Player player : players) {
            for (NetworkMessage msg : messagesToSend) {
                player.sendMessage(msg);
            }
        }
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();

        players.addAll(playersToAdd);
        playersToAdd.clear();
        players.removeAll(playersToRemove);
        playersToRemove.clear();
    }

    public void addEntity(Entity e) {
        this.entitiesToAdd.add(e);
        if (e instanceof Player) {
            this.playersToAdd.add((Player) e);
        }
    }

    public void removeEntity(Entity e) {
        this.entitiesToRemove.add(e);
        if (e instanceof Player) {
            this.playersToRemove.add((Player) e);
        }
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
