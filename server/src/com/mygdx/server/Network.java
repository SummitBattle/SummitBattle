
package com.mygdx.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(SendName.class);
        kryo.register(String[].class);
        kryo.register(SomeResponse.class);
    }

    static public class SendName {
        public static String name;
    }

    static public class SomeResponse {
        public static String text;
    }



}