package com.pinzen.journey.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.logic.GameManager;

public class NMUpdateEntities extends NetworkMessage {

    public static final int ID = 2;

    private List<Entity> entities;

    public NMUpdateEntities() {
        super(ID);
        this.entities = new ArrayList<Entity>();
    }

    public boolean addEntity(Entity entity) {
        if (this.entitiesNumber() < (((65536 - 2) / 40) - 2)) {
            this.entities.add(entity);
            return true;
        }
        return false;
    }

    public int entitiesNumber() {
        return this.entities.size();
    }

    public void prepareEncode() {
        this.writeInt(this.entities.size());

        // Current size of one entity : 40 bytes
        for (Entity e : this.entities) {
            int[] position = e.getPosition();
            int[] speed = e.getSpeed();

            this.writeUuid(e.getUuid());
            this.writeInt(position[0]);
            this.writeInt(position[1]);
            this.writeInt(speed[0]);
            this.writeInt(speed[1]);
            this.writeInt(e.getLife());
            this.writeInt(e.getMaximumLife());
        }
    }

    @Override
    public void decode(UUID senderUid, GameManager gameManager) {
    }

}
