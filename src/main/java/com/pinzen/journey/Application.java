package com.pinzen.journey;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class Application {
    public static int PORT_HTML_SERVER = 8002;
    public static int PORT_WEBSOCKET_SERVER = 8004;

    private WSServer webSocketServer;
    private HttpServer httpServer;

    public Application() {
        this.webSocketServer = null;
        this.httpServer = null;
    }

    public boolean startServers() {
        try {
            this.webSocketServer = new WSServer(PORT_WEBSOCKET_SERVER);
            this.webSocketServer.start();
            System.out.println("WebSocket server listening on port <" + PORT_WEBSOCKET_SERVER + ">");
        } catch (Exception e) {
            System.err.println("Unable to start WebSocket server on port <" + PORT_WEBSOCKET_SERVER + ">");
            return false;
        }

        try {
            this.httpServer = HttpServer.create(new InetSocketAddress(PORT_HTML_SERVER), 0);
            this.httpServer.createContext("/", new RootHandler());
            this.httpServer.setExecutor(null);
            this.httpServer.start();
            System.out.println("HTML server listening on port <" + PORT_HTML_SERVER + ">");
        } catch (Exception e) {
            System.err.println("Unable to start HTML server on port <" + PORT_HTML_SERVER + ">");
            stopServers();
            return false;
        }

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
    }
}
