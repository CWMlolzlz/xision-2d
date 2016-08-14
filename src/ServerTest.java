import xision.communication.network.Server;
import xision.game.XisionGame;

import java.io.IOException;

/**
 * Created by moodyconn on 11/08/16.
 */
public class ServerTest{

	public static void main(String[] args) throws IOException{
		XisionGame game = XisionGame.create("Server",300, 300);

		Server server = ServerTestUtil.startServer(game,5000);
		server.start();

	}
}
