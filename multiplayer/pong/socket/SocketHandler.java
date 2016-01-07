package multiplayer.pong.socket;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SocketHandler {
	private Socket socket;
	
	public SocketHandler() {
		try {
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
