package com.pinzen.journey.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.logic.GameManager;

public class NMRemoveEntities extends NetworkMessage {

    public static final int ID = 4;

    private List<Entity> entities;

    public NMRemoveEntities() {
        super(ID);
        this.entities = new ArrayList<Entity>();
    }

    public boolean addEntity(Entity entity) {
        if (this.entitiesNumber() < (((65536 - 2) / 16) - 2)) {
            this.entities.add(entity);
            return true;
        }
        return false;
    }

    public int entitiesNumber() {
        return this.entities.size();
    }

    @Override
    protected void prepareEncode() {
        this.writeInt(this.entities.size());

        // Current size of one entity : 16 bytes
        for (Entity e : this.entities) {
            this.writeUuid(e.getUuid());
        }
    }

    @Override
    public void decode(UUID senderUuid, GameManager gameManager) {

    }

}
