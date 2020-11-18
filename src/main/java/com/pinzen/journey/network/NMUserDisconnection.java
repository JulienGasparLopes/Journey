package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.logic.GameManager;

public class NMUserDisconnection extends NetworkMessage {

    public static final int ID = 3;

    private UUID userUuid;

    public NMUserDisconnection() {
        super(ID);
    }

    public void prepare(UUID userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    public byte[] encode() {
        ByteBuffer buffer = ByteBuffer.allocate(17);
        buffer.put((byte) ID);
        buffer.putLong(userUuid.getMostSignificantBits());
        buffer.putLong(userUuid.getLeastSignificantBits());
        buffer.flip();

        return buffer.array();
    }

    @Override
    public void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager) {

    }
}