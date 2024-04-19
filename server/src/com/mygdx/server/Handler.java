package com.mygdx.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.server.Network.SomeRequest;
import com.mygdx.server.Network.SomeResponse;
import java.io.IOException;

public class Handler {
    Server server;


public void Serverstart() {
    Server server = new Server();
    Network.register(server);

    server.addListener(new Listener() {
        public void received (Connection connection, Object object) {
            if (object instanceof SomeRequest) {
                SomeRequest request = (SomeRequest)object;
                System.out.println(request.text);

                Network.SomeResponse response = new SomeResponse();
                response.text = "Thanks";
                connection.sendTCP(response);
            }
        }
    });

    server.start();
    try {
        server.bind(7778);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}};


