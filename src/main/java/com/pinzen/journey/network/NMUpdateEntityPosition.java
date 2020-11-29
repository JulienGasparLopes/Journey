package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.entities.Entity;
import com.pinzen.journey.logic.GameManager;

public class NMUpdateEntityPosition extends NetworkMessage {

    public static final int ID = 2;

    private Entity entity;

    public NMUpdateEntityPosition() {
        super(ID);
    }

    public void prepare(Entity entity) {
        this.entity = entity;
    }

    @Override
    public byte[] encode() {
        int[] position = entity.getPosition();
        int[] speed = entity.getSpeed();

        ByteBuffer buffer = ByteBuffer.allocate(21);
        buffer.put((byte) ID);
        buffer.putLong(entity.getUuid().getMostSignificantBits());
        buffer.putLong(entity.getUuid().getLeastSignificantBits());
        buffer.put((byte) position[0]);
        buffer.put((byte) position[1]);
        buffer.put((byte) speed[0]);
        buffer.put((byte) speed[1]);
        buffer.flip();

        return buffer.array();
    }

    @Override
    public void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager) {

    }

}
