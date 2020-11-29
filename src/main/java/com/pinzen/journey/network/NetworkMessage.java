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
    private ByteBuffer buffer;
    private byte[] byteArray;

    public NetworkMessage(int id, int messageByteNumber) {
        this.id = id;
        // Add 4 in order to send message ID
        this.buffer = ByteBuffer.allocate(messageByteNumber == 0 ? 0 : (messageByteNumber + 4));
    }

    public NetworkMessage(int id) {
        this(id, 0);
    }

    /**
     * Write an integer on buffer (4 bytes)
     * 
     * @param i : integer to write
     */
    protected void writeInt(int i) {
        this.buffer.putInt(i);
    }

    protected void writeByte(byte b) {
        this.buffer.put(b);
    }

    /**
     * Write an Uuid on buffer (16 bytes)
     * 
     * @param uuid : UUID to write
     */
    protected void writeUuid(UUID uuid) {
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
    }

    protected abstract void prepareEncode();

    public byte[] encode() {
        // Encode message only one time
        if (this.byteArray == null) {
            this.writeInt(2);
            this.prepareEncode();
            this.buffer.flip();
            this.byteArray = this.buffer.array();
        }

        return this.byteArray;
    }

    public abstract void decode(UUID senderUid, ByteBuffer msg, GameManager gameManager);

    public int getId() {
        return this.id;
    }
}
