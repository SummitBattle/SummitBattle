
package com.mygdx.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(String[].class);
        kryo.register(SomeResponse.class);
    }

    static public class SomeRequest {
        public String text;
    }

    static public class SomeResponse {
        public String text;
    }

}