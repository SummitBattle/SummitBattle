package com.mygdx.server;

import java.util.List;

import com.esotericsoftware.kryonet.Server;
import com.mygdx.common.ConnectedClient;
import com.mygdx.common.Network.NotifyMessage;


public class MatchmakingManager {

    public void Matchmaking(List connectedClients, ConnectedClientsManager connectedClientsManager, Server server) {

        if (connectedClients.size() >= 2) {
            // Match players in pairs (1 and 2, 3 and 4, ...)
            for (int i = 0; i < connectedClients.size(); i += 2) {
                System.out.println("matchmaking");
                ConnectedClient player1 = (ConnectedClient) connectedClients.get(i);
                ConnectedClient player2 = (ConnectedClient) connectedClients.get(i + 1);

                // Create a game session for the matched players
                NotifyPlayers(player1, player2, server);
                // Remove matched players from the waiting list
                connectedClientsManager.removeConnectedClientById(player1.getID());
                connectedClientsManager.removeConnectedClientById(player2.getID());
            }}}

    public void NotifyPlayers(ConnectedClient player1, ConnectedClient player2, Server server) {
        NotifyMessage notifyMessage = new NotifyMessage();
        notifyMessage.connectedClient1 = player1;
        notifyMessage.connectedClient2 = player2;
        notifyMessage.isReady = true;
        System.out.println("sending CLIENT1" + player1 + " and CLIENT2" + player2);
        server.sendToTCP(player1.getID(),notifyMessage);
        server.sendToTCP(player2.getID(),notifyMessage);





    }


}