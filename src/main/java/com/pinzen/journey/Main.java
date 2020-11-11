package com.pinzen.journey;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Application ...");

        Application app = new Application();
        boolean running = app.startServers();

        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (running) {
            try {
                in = sysin.readLine();
            } catch (Exception e) {
                in = "exit";
            }
            if (in.equals("exit")) {
                app.stopServers();
                running = false;
                break;
            }
        }

        try {
            sysin.close();
        } catch (Exception e) {
        }

        System.out.println("Application stopped");
    }
}
