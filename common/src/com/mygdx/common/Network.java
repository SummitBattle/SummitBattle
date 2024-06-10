
package com.mygdx.common;

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
        kryo.register(NotifyMessage.class);
        kryo.register(PlayerNumberReq.class);
        kryo.register(PlayerNumberSend.class);
        kryo.register(ConnectedClient.class);
        kryo.register(ServerInput.class);
        kryo.register(ClientInput.class);
    }

    static public class SendName {
        public String name;
    }

    static public class SomeResponse {
        public String text;
    }

    static public class MoveCharacter {
        public int x,y;
    }
    static public class UpdateCharacterg {
        public int id,x,y;
    }
    static public class NotifyMessage {
        public ConnectedClient connectedClient1;
        public ConnectedClient connectedClient2;
        public boolean isReady;
    }

    static public class PlayerNumberReq {
    }

    static public class PlayerNumberSend {
        public String Playernumber;
    }


    static public class ClientInput {
        public boolean A_Pressed;
        public boolean D_Pressed;
        public boolean Enter_Pressed;
        public boolean W_Pressed;
        public ConnectedClient connectedClient1;
        public ConnectedClient connectedClient2;
    }

    static public class ServerInput {
        public boolean A_Pressed;
        public boolean D_Pressed;
        public boolean Enter_Pressed;
        public boolean W_Pressed;
        public ConnectedClient connectedClient1;
        public ConnectedClient connectedClient2;

    }





}