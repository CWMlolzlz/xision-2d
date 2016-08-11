import xision.communication.messages.StringMessage;
import xision.communication.network.NetworkConnection;
import xision.game.XisionGame;

import java.io.IOException;

/**
 * Created by moodyconn on 11/08/16.
 */
public class ClientTest{

	public static void main(String[] args) throws IOException{
		XisionGame game = XisionGame.create("Client",300, 300);

		NetworkConnection client = ServerTestUtil.startClient(game,"localhost",5000);

		client.bind(StringMessage.class,(s,m) -> System.out.println(m.getString()));

		client.connect();

	}

}
