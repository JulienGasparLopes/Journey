package com.pinzen.journey.network;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pinzen.journey.logic.GameManager;

public abstract class NetworkMessage {

    public static final Map<Integer, Class<? extends NetworkMessage>> NETWORK_MESSAGES;
    static {
        NETWORK_MESSAGES = new HashMap<Integer, Class<? extends NetworkMessage>>();
        NETWORK_MESSAGES.put(NMMovement.ID, NMMovement.class);
    }

    private int id;

    public NetworkMessage(int id) {
        this.id = id;
    }

    public abstract byte[] encode();

    public abstract void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager);

    public int getId() {
        return this.id;
    }
}
