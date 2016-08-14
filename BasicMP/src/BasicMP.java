import messages.ActorMoveMessage;
import messages.NewPlayerMessage;
import messages.RequestPlayerIDMessage;
import messages.SetPlayerIDMessage;
import xision.communication.network.Server;
import xision.communication.network.TCPConnection;
import xision.game.XisionGame;
import xision.math.vector.Vec2;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Connor on 11/08/2016.
 */
public class BasicMP{

    private static final int port = 5000;
    static int nextPlayerId = 0;

    public static void main(String[] args) throws IOException{
        if(JOptionPane.showConfirmDialog(null, "Would you like to host?", "Startup", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            XisionGame game = XisionGame.create("Server", 100, 100);
            List<Integer> playerIds = new ArrayList<>();
            Server server = new Server(game, port, 4);
            server.bind(RequestPlayerIDMessage.class, (o, m) -> {

                        server.sendTo(new SetPlayerIDMessage(nextPlayerId), (Socket) o);

                        for(int pi : playerIds){
                            server.sendTo(new NewPlayerMessage(pi), (Socket) o);
                        }
                        server.sendExclude(new NewPlayerMessage(nextPlayerId), (Socket) o);

                        playerIds.add(nextPlayerId);

                        nextPlayerId++;
                    }
            );
            server.bind(ActorMoveMessage.class, (o, m) ->
                        server.sendExclude(m, (Socket) o)
            );

            server.start();

            //server.bind(messages.PlayerMoveMessage.class, );
        }else{
            XisionGame game = XisionGame.create("Client", 600, 600);

            ActorManager actorManager = new ActorManager();

            TCPConnection client = new TCPConnection(game, "localhost", port);
            client.bind(SetPlayerIDMessage.class, (s, m) -> {
                        LocalPlayer lp = new LocalPlayer(game, null, m.id);
                        lp.addListener("position", (Vec2 v) ->  client.send(new ActorMoveMessage(m.id,v.x,v.y)));
                        actorManager.addActor(lp);
                        game.addGameObject(lp);
                    }
            );
            client.bind(NewPlayerMessage.class, (s, m) -> {
                        Player np = new Player(game,null,m.id);
                        actorManager.addActor(np);
                        game.addGameObject(np);
                    }
            );

            client.bind(ActorMoveMessage.class, (s, m) ->
                    actorManager.getActor(m.actorID).setPosition(m.x, m.y)
            );

            client.send(new RequestPlayerIDMessage());
            client.start();
        }
    }

}
