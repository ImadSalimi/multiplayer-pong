package multiplayer.pong.socket;

import java.net.URISyntaxException;
import java.util.Vector;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.client.LobbyFrame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SocketHandler {
	private static Socket socket;
	private static String host = "http://localhost:8080"; 
	
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
		}).on("connectedPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... arg0) {
                JSONArray players = (JSONArray) arg0[0];
                Vector<String> usernames = new Vector<String>();
                try {
                    for(int i=0; i<players.length(); i++){
                        usernames.add(players.getJSONObject(i).getString("username"));
                    }
                } catch(JSONException e) {

                }
                LobbyFrame.connectedPlayers = usernames;
            }
        });
	}
        
    public static void userConnected(String username){
        JSONObject data = new JSONObject();
        try {
            data.put("username",username);
            socket.emit("userConnected", data);
        } catch(JSONException e) {
            
        }
    }
}
