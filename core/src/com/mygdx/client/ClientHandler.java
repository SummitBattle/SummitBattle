package com.mygdx.client;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.common.Network;
import com.mygdx.common.Network.SendName;
import com.mygdx.common.ConnectedClient;
import com.mygdx.common.Network.NotifyMessage;
import com.mygdx.common.Network.PlayerNumberReq;
import com.mygdx.common.Network.PlayerNumberSend;
import com.mygdx.common.Network.ClientInput;
import com.mygdx.common.Network.GameState;

import java.io.IOException;

public class ClientHandler {
    private static final int TIMEOUT = 5000;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000; // Updated to match server port


    private Client client;
    private String clientName;
    ConnectedClient connectedClient1;



    String PlayerNumber;
    boolean isReady = false;


    public boolean EnemyA;
    public boolean EnemyD;
    public boolean EnemyEnter;
    public boolean EnemyW;



    ConnectedClient connectedClient2;

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


                client.sendTCP(sendName);



            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected from the server.");
            }

                @Override
                public void received(Connection connection, Object object) {
                    if (object instanceof NotifyMessage) {
                        connectedClient1 = ((NotifyMessage) object).connectedClient1;
                        connectedClient2 = ((NotifyMessage)object).connectedClient2;
                        isReady = ((NotifyMessage)object).isReady;



                        PlayerNumberReq playerNumberReq = new PlayerNumberReq();
                        System.out.println("Sending PlayerNumberReq");
                        client.sendTCP(playerNumberReq);

                }
                if (object instanceof PlayerNumberSend) {
                    PlayerNumber = ((PlayerNumberSend)object).Playernumber;


                }

                if (object instanceof GameState){
                    SetInputs(((GameState) object).A_Pressed, ((GameState) object).D_Pressed, ((GameState) object).Enter_Pressed,  ((GameState) object).W_Pressed);
                }

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

    public ConnectedClient getConnectedClient1() { return connectedClient1;}
    public ConnectedClient getConnectedClient2() {
        return connectedClient2;
    }

    public String getClientName() {
        return clientName;
    }
    public String getPlayerNumber() {
        return PlayerNumber;
    }
    public boolean getIsReady() {
        return isReady;
    }




    public void SendInputs(boolean A, boolean D, boolean Enter, boolean W, ConnectedClient SENTCLIENT, ConnectedClient REQCLIENT) {
        ClientInput clientInput = new ClientInput();
        clientInput.A_Pressed = A;
        clientInput.D_Pressed = D;
        clientInput.Enter_Pressed = Enter;
        clientInput.W_Pressed = W;
        clientInput.SENTCLIENT = SENTCLIENT;
        clientInput.REQUESTCLIENT = REQCLIENT;

        client.sendTCP(clientInput);

    }

    public void SetInputs(boolean A, boolean D, boolean Enter, boolean W){
        EnemyA = A;
        EnemyD = D;
        EnemyEnter = Enter;
        EnemyW = W;

    }


    public void DisconnectClient() {
        client.close();
    }





}









