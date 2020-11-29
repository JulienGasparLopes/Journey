package com.pinzen.journey.network;

import static com.pinzen.journey.network.NetworkMessage.NETWORK_MESSAGES;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.pinzen.journey.logic.GameManager;
import com.pinzen.journey.logic.User;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

//TODO : set user inactive for Xms, then disconnect him

public class WSServer extends WebSocketServer {

    private GameManager gameManager;

    public WSServer(int port, GameManager gameManager) throws UnknownHostException {
        super(new InetSocketAddress(port));
        this.gameManager = gameManager;
    }

    @Override
    public void onStart() {
        setConnectionLostTimeout(100);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        UUID uuid = this.gameManager.addUser(conn);
        conn.setAttachment(uuid);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        int msgId = message.get();
        Class<? extends NetworkMessage> msgClass = NETWORK_MESSAGES.get(msgId);
        try {
            NetworkMessage networkMsg = msgClass.getConstructor().newInstance();
            networkMsg.decode((UUID) conn.getAttachment(), message, this.gameManager);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        UUID uuid = (UUID) conn.getAttachment();
        User userRemoved = gameManager.removeUser(uuid);
        NMUserDisconnection msg = new NMUserDisconnection();
        msg.prepare(userRemoved.player.getUuid());
        this.broadcast(msg);
    }

    private void broadcast(NetworkMessage msg) {
        for (User user : gameManager.getUsers()) {
            user.sendMessage(msg);
        }
    }
}