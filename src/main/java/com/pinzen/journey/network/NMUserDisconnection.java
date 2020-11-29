package com.pinzen.journey.network;

import java.util.UUID;

import com.pinzen.journey.logic.GameManager;

public class NMUserDisconnection extends NetworkMessage {

    public static final int ID = 3;

    private UUID userUuid;

    public NMUserDisconnection() {
        super(ID, 16);
    }

    public void prepare(UUID userUuid) {
        this.userUuid = userUuid;
    }

    @Override
    protected void prepareEncode() {
        this.writeUuid(userUuid);
    }

    @Override
    public void decode(UUID senderUid, GameManager gameManager) {

    }
}