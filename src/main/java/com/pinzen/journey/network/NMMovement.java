package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.logic.GameManager;
import com.pinzen.journey.logic.User;

public class NMMovement extends NetworkMessage {

    public static final int ID = 1;

    public NMMovement() {
        super(ID);
    }

    @Override
    public byte[] encode() {
        return null;
    }

    @Override
    public void decode(UUID senderUuid, ByteBuffer msg, GameManager gameManager) {
        User sender = gameManager.getUser(senderUuid);
        int keyCode = msg.get();
        int keyState = msg.get();

        if (keyCode == 1) {
            sender.vy = keyState;
        } else if (keyCode == 3) {
            sender.vy = -keyState;
        } else if (keyCode == 2) {
            sender.vx = keyState;
        } else if (keyCode == 4) {
            sender.vx = -keyState;
        }

    }

}
