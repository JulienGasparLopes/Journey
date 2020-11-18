package com.pinzen.journey.network;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HTMLServer implements HttpHandler {

    private static File error404 = new File("Journey/www/error404.html");

    public void handle(HttpExchange he) throws IOException {
        String requestString = he.getRequestURI().getPath();
        if (requestString.equals("") || requestString.equals("/")) {
            requestString = "index.html";
        }
        File file = new File("Journey/www/" + requestString);
        int code = 200;
        if (!file.exists() || file.isDirectory()) {
            file = error404;
            code = 404;
        }
        if (requestString.endsWith(".js"))
            he.getResponseHeaders().set("Content-Type", "text/javascript; charset=UTF-8");
        else
            he.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");

        he.sendResponseHeaders(code, file.length());
        try (OutputStream os = he.getResponseBody()) {
            Files.copy(file.toPath(), os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}