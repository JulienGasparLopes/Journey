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
    protected void prepareEncode() {
        // TODO Auto-generated method stub
    }

    @Override
    public void decode(UUID senderUuid, ByteBuffer msg, GameManager gameManager) {
        User sender = gameManager.getUser(senderUuid);
        int keyCode = msg.get();
        int keyState = msg.get();

        sender.updateKey(keyCode, keyState == 1);
    }

}
