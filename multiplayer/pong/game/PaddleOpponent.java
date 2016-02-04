package multiplayer.pong.game;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.socket.SocketHandler;

public class PaddleOpponent extends Paddle {

	private Socket socket;
	
	public PaddleOpponent(PongPanel panel, int x) {
		super(panel, x);
		this.socket = SocketHandler.getSocket();
		handleSockets();
	}
	
	private void handleSockets() {
		socket.on("paddleMoved", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject) arg0[0];
				try {
					goingUp = data.getBoolean("goingUp");
					goingDown = data.getBoolean("goingDown");
					y = data.getInt("y");
				} catch(JSONException e) {}
			}
		});
	}

}
