package com.mygdx.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.server.Network.*;

public class ChatServer {
    Server server;
    ConnectedClientsManager clientsManager = new ConnectedClientsManager();
    int ClientID;

    int dcID;
    String clientName;

    public ChatServer() throws IOException {
        server = new Server();
        Network.register(server);

        // Add a listener to handle incoming connections and messages
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
            }

            @Override
            public void disconnected(Connection connection) {
                dcID = connection.getID();
                clientsManager.removeConnectedClientById(dcID);
                System.out.println("Client with ID: " + dcID + " disconnected");
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof SendName) {

                    String name = ((SendName) object).name;
                    System.out.println("NAME IS:" + name);

                    if (name == null)
                        return;
                    name = name.trim();
                    if (name.length() == 0 )
                        return;

                    clientName = name;


                    InetSocketAddress address = connection.getRemoteAddressTCP();
                    String ipAddress = address.getAddress().getHostAddress();
                    ClientID = connection.getID();

                    System.out.println("Received name '" + clientName + "' from client at IP: " + ipAddress + " with ID:" + ClientID);
                    clientsManager.addConnectedClient(ipAddress, clientName, ClientID);

                    List<ConnectedClient> connectedClients = clientsManager.getConnectedClients();
                    System.out.println("Connected Clients:");
                    for (ConnectedClient client : connectedClients) {
                        System.out.println("IP: " + client.getIpAddress() + ", Name: " + client.getName() + "ID:" + client.getID());
                }}
            }
        });

        server.bind(33);
        server.start();

        System.out.println("Server started and listening on port " + "33");
    }

    public static void main(String[] args) {
        try {
            Log.set(Log.LEVEL_DEBUG);
            new ChatServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
