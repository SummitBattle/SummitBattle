package com.mygdx.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.server.Network;
import com.mygdx.server.Network.SomeResponse;
import com.mygdx.server.Network.SendName;

import java.io.IOException;
import com.mygdx.client.StartScreen;


public class ClientHandler {
    // Initialize the client field
    private static Client client = new Client();
    private static String ClientName;

    // Static initialization block to configure and start the client
    public static void startClient() {
        if (client.isConnected()) {
            System.out.println("Client is already connected.");
            return; // Exit the method if already connected
        } else {
            client.start();
            Network.register(client);

            try {
                client.connect(5000, "127.0.0.1", 54555);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    // Method to send a request to the server
    public static void ReadyClient(String PlayerName) {

        SendName sendname = new SendName();
        sendname.name = PlayerName;

        client.sendTCP(sendname);
    }

    // Method to add a listener to handle server responses
    public static void addListener() {



    }
}
