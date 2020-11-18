package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.logic.GameManager;
import com.pinzen.journey.logic.User;

public class NMUpdateEntityPosition extends NetworkMessage {

    public static final int ID = 2;

    private User user;

    public NMUpdateEntityPosition() {
        super(ID);
    }

    public void prepare(User user) {
        this.user = user;
    }

    @Override
    public byte[] encode() {
        ByteBuffer buffer = ByteBuffer.allocate(21);
        buffer.put((byte) ID);
        buffer.putLong(user.getUuid().getMostSignificantBits());
        buffer.putLong(user.getUuid().getLeastSignificantBits());
        buffer.put((byte) user.x);
        buffer.put((byte) user.y);
        buffer.put((byte) user.vx);
        buffer.put((byte) user.vy);
        buffer.flip();

        return buffer.array();
    }

    @Override
    public void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager) {

    }

}
