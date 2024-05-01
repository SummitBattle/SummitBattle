package com.mygdx.server;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClientsManager {
    private List<ConnectedClient> connectedClients;

    public ConnectedClientsManager() {
        connectedClients = new ArrayList<>();
    }

    public void addConnectedClient(String ipAddress, String name, int ID) {
        ConnectedClient client = new ConnectedClient(ipAddress, name, ID);
        connectedClients.add(client);
    }

    public List<ConnectedClient> getConnectedClients() {
        return connectedClients;
    }

    // Other methods to manipulate the list of connected clients as needed

    public void removeConnectedClientById(int ID) {
        // Iterate through the list of connected clients
        for (int i = 0; i < connectedClients.size(); i++) {
            ConnectedClient client = connectedClients.get(i);
            // Check if the client's ID matches the provided ID
            if (client.getID() == ID) {
                // Remove the client from the list
                connectedClients.remove(i);
                // Exit the loop after removing the client
                break;
            }
        }
    }
}
