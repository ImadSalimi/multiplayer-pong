package multiplayer.pong.socket;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketHandler {
	private static Socket socket;
	private static String host = "http://localhost:8000"; 
	
	public static void connectSocket() {
		try {
			socket = IO.socket(host);
			socket.connect();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void configSocketEvents() {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				System.out.println("Connected to SocketIO");
			}
		});
	}
}
