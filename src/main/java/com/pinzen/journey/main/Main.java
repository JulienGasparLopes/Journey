package com.pinzen.journey.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * ----- ----- TODO LIST ----- -----
 * 
 * - send int for position/speed
 * 
 * - create function to easily put int, float, long, string and UUID in
 * NetworkMessage
 * 
 * - remove users on disconnection
 * 
 **/

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting Application ...");

        Application app = new Application();
        boolean running = app.start();

        BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
        String in = "";
        while (running) {
            try {
                in = sysin.readLine();
            } catch (Exception e) {
                in = "exit";
            }
            if (in.equals("exit") || in.equals("e")) {
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
