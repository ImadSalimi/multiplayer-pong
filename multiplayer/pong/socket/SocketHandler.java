package multiplayer.pong.socket;

import java.net.URISyntaxException;

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
	public static void sendMessage(String to, String message) {
		JSONObject data = new JSONObject();
		try {
			data.put("from", username);
			data.put("to", to);
			data.put("message", message);
		} catch (JSONException e) {}
		socket.emit("sendMessage", data);
	}
	
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
	
	public static void challenge(String opponent) {
		JSONObject data = new JSONObject();
		try {
			data.put("initiator", username);
			data.put("recipient", opponent);
		} catch (JSONException e) {}
		socket.emit("challenge", data);
	}
	
	public static void challengeAck(String opponent, boolean accepted) {
		JSONObject data = new JSONObject();
		try {
			data.put("initiator", opponent);
			data.put("recipient", username);
			data.put("accepted", accepted);
		} catch (JSONException e) {}
		socket.emit("challengeAck", data);
	}
	
	public static void startGame(String opponent) {
		JSONObject data = new JSONObject();
		try {
			data.put("player1", username);
			data.put("player2", opponent);
		} catch (JSONException e) {}
		socket.emit("startGame", data);
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
	
	public static void playerScored(int num) {
		JSONObject data = new JSONObject();
		try {
			data.put("num", num);
		} catch (JSONException e) {}
		socket.emit("playerScored", data);
	}

	public static void moveBall(int xa, int ya) {
		JSONObject data = new JSONObject();
		try {
			data.put("xa", xa);
			data.put("ya", ya);
		} catch (JSONException e) {}
		socket.emit("moveBall", data);
	}
}
