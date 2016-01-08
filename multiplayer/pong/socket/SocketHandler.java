package multiplayer.pong.socket;

import java.net.URISyntaxException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.client.LobbyFrame;

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
			System.out.println(socket);
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
        
    public static void userConnected(String username){
        SocketHandler.username = username;
        JSONObject data = new JSONObject();
        try {
            data.put("username", username);
            socket.emit("userConnected", data);
        } catch(JSONException e) {
            
        }
    }
}
