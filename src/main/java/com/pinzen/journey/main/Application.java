package com.pinzen.journey.main;

import java.net.InetSocketAddress;

import com.pinzen.journey.logic.GameManager;
import com.pinzen.journey.network.HTMLServer;
import com.pinzen.journey.network.WSServer;
import com.sun.net.httpserver.HttpServer;

public class Application {
    public static int PORT_HTML_SERVER = 8002;
    public static int PORT_WEBSOCKET_SERVER = 8004;

    private GameManager gameManager;
    private WSServer webSocketServer;
    private HttpServer httpServer;

    public Application() {
        this.webSocketServer = null;
        this.httpServer = null;

        this.gameManager = new GameManager();
    }

    public boolean start() {
        try {
            this.webSocketServer = new WSServer(PORT_WEBSOCKET_SERVER, gameManager);
            this.webSocketServer.start();
            System.out.println("WebSocket server listening on port <" + PORT_WEBSOCKET_SERVER + ">");
        } catch (Exception e) {
            System.err.println("Unable to start WebSocket server on port <" + PORT_WEBSOCKET_SERVER + ">");
            return false;
        }

        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(PORT_HTML_SERVER), 0);
            this.httpServer.createContext("/", new HTMLServer());
            this.httpServer.setExecutor(null);
            this.httpServer.start();
            System.out.println("HTML server listening on port <" + PORT_HTML_SERVER + ">");
        } catch (Exception e) {
            System.err.println("Unable to start HTML server on port <" + PORT_HTML_SERVER + ">");
            stopServers();
            return false;
        }

        this.gameManager.start();

        return true;
    }

    public void stopServers() {
        try {
            webSocketServer.stop(1000);
            System.out.println("WebSocket server stopped");
        } catch (Exception e) {
        }
        try {
            httpServer.stop(1);
            System.out.println("HTML server stopped");
        } catch (Exception e) {
        }
        this.gameManager.stopManager();
    }
}
