package com.mygdx.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.server.Network.*;

public class ChatServer {
    Server server;
    ConnectedClientsManager clientsManager = new ConnectedClientsManager();
    int ClientID;

    int dcID;

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
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof SendName) {
                    SendName sendName = (SendName) object;
                    String clientName = sendName.name;
                    InetSocketAddress address = (InetSocketAddress) connection.getRemoteAddressTCP();
                    String ipAddress = address.getAddress().getHostAddress();
                    ClientID = connection.getID();

                    System.out.println("Received name '" + clientName + "' from client at IP: " + ipAddress + " with ID:" + ClientID);
                    clientsManager.addConnectedClient(ipAddress, clientName, ClientID);

                    List<ConnectedClient> connectedClients = clientsManager.getConnectedClients();
                    System.out.println("Connected Clients:");
                    for (ConnectedClient client : connectedClients) {
                        System.out.println("IP: " + client.getIpAddress() + ", Name: " + client.getName());
                }}
            }
        });
    }

    public void startServer(int port) throws IOException {
        // Start the server and bind it to a port
        server.start();
        server.bind(port);
        System.out.println("Server started and listening on port " + port);
    }

    public static void main(String[] args) {
        try {
            ChatServer chatServer = new ChatServer();
            chatServer.startServer(54555); // Choose a port number
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
