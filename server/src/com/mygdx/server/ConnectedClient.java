package com.mygdx.server;

import java.util.ArrayList;
import java.util.List;

public class ConnectedClient {
    private String ipAddress;
    private String name;
    private int ID;

    public ConnectedClient(String ipAddress, String name, int ID) {
        this.ipAddress = ipAddress;
        this.name = name;
        this.ID = ID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getName() {
        return name;
    }
    public int getID() {
        return ID;
    }
}

