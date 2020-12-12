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
        NETWORK_MESSAGES.put(NMUseAbility.ID, NMUseAbility.class);
    }

    private int id;
    private ByteBuffer buffer;
    private byte[] byteArray;

    public NetworkMessage(int id) {
        this.id = id;
        this.buffer = ByteBuffer.allocate(65536);
    }

    public NetworkMessage(int id, ByteBuffer buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    protected void writeByte(byte b) {
        this.buffer.put(b);
    }

    /**
     * Write an integer on buffer (4 bytes)
     * 
     * @param i : integer to write
     */
    protected void writeInt(int i) {
        this.buffer.putInt(i);
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

    protected byte readByte() {
        return this.buffer.get();
    }

    protected int readInt() {
        return this.buffer.getInt();
    }

    protected abstract void prepareEncode();

    public byte[] encode() {
        // Encode message only one time
        if (this.byteArray == null) {
            this.writeInt(this.id);
            this.prepareEncode();
            this.buffer.flip();
            this.byteArray = new byte[buffer.limit()];
            this.buffer.get(this.byteArray);
        }

        return this.byteArray;
    }

    public abstract void decode(UUID senderUid, GameManager gameManager);

    public int getId() {
        return this.id;
    }
}
