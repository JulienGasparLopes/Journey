package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.logic.GameManager;

public class NMUpdateEntityPosition extends NetworkMessage {

    public static final int ID = 2;

    private Entity entity;

    public NMUpdateEntityPosition() {
        super(ID, 32);
    }

    public void prepare(Entity entity) {
        this.entity = entity;
    }

    public void prepareEncode() {
        int[] position = entity.getPosition();
        int[] speed = entity.getSpeed();

        this.writeUuid(entity.getUuid());
        this.writeInt(position[0]);
        this.writeInt(position[1]);
        this.writeInt(speed[0]);
        this.writeInt(speed[1]);
    }

    @Override
    public void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager) {

    }

}
