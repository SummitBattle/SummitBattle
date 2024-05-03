package com.mygdx.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.server.Network;
import com.mygdx.server.Network.SendName;

import java.io.IOException;

public class ClientHandler {
     Client client;
     String ClientName;

    public ClientHandler(String PlayerName) {
        // Initialize the client
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                SendName sendName = new SendName();
                sendName.name = PlayerName;
                System.out.println("sending Name: " + sendName.name);

                client.sendTCP(sendName);
            }
        });

        try {
            client.connect(5000, "127.0.0.1", 33);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }};


