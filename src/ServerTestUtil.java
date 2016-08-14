import xision.communication.network.TCPConnection;
import xision.game.XisionGame;
import xision.communication.network.Server;

import java.io.IOException;

/**
 * Created by Connor on 8/08/2016.
 */
public class ServerTestUtil{

    public static Server startServer(XisionGame game, int port) throws IOException{
        return new Server(game,port, 16);
    }

    public static TCPConnection startClient(XisionGame game, String host, int port) throws IOException{
        TCPConnection client = new TCPConnection(game,host, port);
        return client;
    }

}
