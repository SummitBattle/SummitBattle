package com.mygdx.common;

public class ConnectedClient {
    private final String ipAddress;
    private final String name;
    private final int id;

    public ConnectedClient(String ipAddress, String name, int id) {
        this.ipAddress = ipAddress;
        this.name = name;
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
}