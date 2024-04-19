package com.mygdx.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.server.Network.SomeResponse;
import com.mygdx.server.Network.SomeRequest;

import java.io.IOException;

public class ClientHandler {
    Client client;


    public static void Clienthandler() {
        Client client = new Client();
        client.start();
        try {
            client.connect(5000, "127.0.0.1", 7778);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        SomeRequest request = new SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse)object;
                    System.out.println(response.text);
                }
            }
        });

    }}
