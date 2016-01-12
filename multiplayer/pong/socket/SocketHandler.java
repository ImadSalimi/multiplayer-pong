package multiplayer.pong.socket;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketHandler {
	private static Socket socket;
	private static String host = "http://localhost:1337/";
    public static String username;
    
    public static Socket getSocket() {
    	return socket;
    }
	
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
				System.out.println("Connected to SocketIO server. ID: " + socket.id());
			}
		});
	}
	
	// Lobby emitters
	public static void friendRequest(String to) {
		JSONObject data = new JSONObject();
		try {
			data.put("from", username);
			data.put("to", to);
		} catch (JSONException e) {}
		socket.emit("friendRequest", data);
	}
	
	public static void friendRequestAck(String to) {
		JSONObject data = new JSONObject();
		try {
			data.put("from", username);
			data.put("to", to);
		} catch (JSONException e) {}
		socket.emit("friendRequestAck", data);
	}
	
	// Game emitters
	public static void paddleMoved(boolean goingUp, boolean goingDown) {
		JSONObject data = new JSONObject();
		try {
			data.put("goingUp", goingUp);
			data.put("goingDown", goingDown);
		} catch (JSONException e) {}
		socket.emit("paddleMoved", data);
	}
}
