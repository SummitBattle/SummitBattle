package com.mygdx.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class ConnectedClientsManager {
    private final List<ConnectedClient> connectedClients = new CopyOnWriteArrayList<>();

    public void addConnectedClient(String ipAddress, String name, int id) {
        connectedClients.add(new ConnectedClient(ipAddress, name, id));
    }

    public void removeConnectedClientById(int id) {
        connectedClients.removeIf(client -> client.getID() == id);
    }

    public List<ConnectedClient> getConnectedClients() {
        return connectedClients;
    }
}

