package com.mygdx.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.server.Network;
import com.mygdx.server.Network.SendName;

import java.io.IOException;

public class ClientHandler {
    private static final int TIMEOUT = 5000;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000; // Updated to match server port

    private Client client;
    private String clientName;

    public ClientHandler(String playerName) {
        this.clientName = playerName;

        // Initialize the client
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                SendName sendName = new SendName();
                sendName.name = clientName;
                System.out.println("Sending name: " + sendName.name);

                client.sendTCP(sendName);
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected from the server.");
            }

            @Override
            public void received(Connection connection, Object object) {
                // Handle received objects if needed
            }
        });

        try {
            client.connect(TIMEOUT, HOST, PORT);
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() {
        if (client != null) {
            client.stop();
        }
    }

    public Client getClient() {
        return client;
    }

    public String getClientName() {
        return clientName;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java ClientHandler <playerName>");
            return;
        }
        String playerName = args[0];
        ClientHandler clientHandler = new ClientHandler(playerName);

        // To keep the client running, add a shutdown hook to stop it on exit
        Runtime.getRuntime().addShutdownHook(new Thread(clientHandler::stop));
    }
}
