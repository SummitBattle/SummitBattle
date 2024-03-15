

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        Server kryoServer = new Server();
        try {
            kryoServer.bind(123);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        kryoServer.start();


    }
}
