package com.mygdx.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.mygdx.common.ConnectedClient;
import com.mygdx.common.Network;
import com.mygdx.common.Network.PlayerNumberReq;
import com.mygdx.common.Network.PlayerNumberSend;
import com.mygdx.common.Network.SendName;
import com.mygdx.common.PlayerInput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;


public class GameServer {
    private Server server;
    private ConnectedClientsManager clientsManager = new ConnectedClientsManager();
    int ClientID;
    boolean A_PRESSED;
    boolean D_PRESSED;
    boolean W_PRESSED;
    boolean ENTER_PRESSED;
    ConnectedClient SENTCLIENT;
    ConnectedClient REQUESTCLIENT;





    public GameServer() throws IOException {
        server = new Server();
        Network.register(server);
        MatchmakingManager matchmakingManager = new MatchmakingManager();

        // Add a listener to handle incoming connections and messages
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected: " + connection.getID());

            }

            @Override
            public void disconnected(Connection connection) {
                int dcID = connection.getID();
                clientsManager.removeConnectedClientById(dcID);
                System.out.println("Client with ID: " + dcID + " disconnected");
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof SendName) {
                    String name = ((SendName) object).name;
                    System.out.println("NAME IS: " + name);

                    if (name == null || name.trim().isEmpty()) {
                        return;
                    }

                    name = name.trim();
                    String clientName = name;

                    InetSocketAddress address = connection.getRemoteAddressTCP();
                    String ipAddress = address.getAddress().getHostAddress();
                    ClientID = connection.getID();

                    System.out.println("Received name '" + clientName + "' from client at IP: " + ipAddress + " with ID: " + ClientID);
                    clientsManager.addConnectedClient(ipAddress, clientName, ClientID);

                    List<ConnectedClient> ConnectedClients = clientsManager.getConnectedClients();
                    System.out.println("Connected Clients:");
                    for (ConnectedClient client : ConnectedClients) {
                        System.out.println("IP: " + client.getIpAddress() + ", Name: " + client.getName() + ", ID: " + client.getID());
                    }
                    matchmakingManager.matchmaking(clientsManager.getConnectedClients(),clientsManager,server);





                }
                if (object instanceof PlayerNumberReq) {
                    PlayerNumberSend playerNumberSend = new PlayerNumberSend();
                    if ( connection.getID() % 2 == 1) {
                        playerNumberSend.Playernumber = "Player 1";
                    } else if (connection.getID() % 2 == 0) {
                        playerNumberSend.Playernumber = "Player 2";
                    }
                    server.sendToTCP(connection.getID(), playerNumberSend);
                }

                if (object instanceof PlayerInput) {

                    PlayerInput input = (PlayerInput) object;
                    int clientId = connection.getID();
                    Integer pairedClientId = matchmakingManager.getPairedClientId(clientId);
                    if (pairedClientId != null) {
                        server.sendToTCP(pairedClientId, input);

                        System.out.println("sending enemy input to: " + pairedClientId);
                    }






                }
            }
        });

        // Choose a higher port number, e.g., 5000
        int port = 5000;
        int UDPPort = 4999;
        server.bind(port,UDPPort);
        server.start();

        System.out.println("Server started and listening on port " + port);
    }

    public static void main(String[] args) {
        try {
            Log.set(Log.LEVEL_DEBUG);
            new GameServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
