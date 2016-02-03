package multiplayer.pong.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.socket.SocketHandler;


public class Ball {
	private Socket socket;
	private static final int SIZE = 20;
    private PongPanel panel;
    private int x, y, xa, ya;
    private boolean gameStarted = false;
    
    public Ball(PongPanel panel) {
    	this.panel = panel;
    	x = panel.game.getWidth() / 2;
    	y = panel.game.getHeight() / 2;
    	this.socket = SocketHandler.getSocket();
    	handleSockets();
    	int num = panel.game.location == "home" ? 1 : 2;
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (!gameStarted)
					socket.emit("initializeGame", num);
				else
					timer.cancel();
			}
		}, 0, 45);
    }
    
    public void handleSockets() {
    	socket.on("reset", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				panel.resetPaddles();
				int ya = (int) arg0[0];
				reset(0, ya);
			}
		}).on("initializeGame", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject) arg0[0];
				try {
					gameStarted = true;
					int xv = data.getInt("xa");
					int xa = panel.game.location == "home" ? xv : -xv;
					int ya = data.getInt("ya");
					reset(xa, ya);
				} catch (JSONException e) {}
			}
		}).on("pause", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				panel.state = GameState.PAUSED;
			}
		});
    }
    
    public void update() {
    	x += xa;
    	y += ya;
        if (x + SIZE <= 5) {
            panel.increaseScoreFor(2);
            SocketHandler.playerScored(2);
        } else if (x >= panel.game.getWidth() - 5) {
            panel.increaseScoreFor(1);
            SocketHandler.playerScored(1);
        }
        else if (y < 0 || y >= panel.game.getHeight() - SIZE)
            ya = -ya;
        checkCollision();
    }
    
    private void reset(int xv, int yv) {
    	x = panel.game.getWidth() / 2;
    	y = panel.game.getHeight() / 2;
    	xa = xv == 0 ? -xa : xv;
    	ya = yv;
    	// Go again after one second
    	Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				panel.state = GameState.PLAYING;
			}
		}, 1000);
    }
    
    private void checkCollision() {
    	if (panel.getPlayer(1).getBounds().intersects(getBounds()) || panel.getPlayer(2).getBounds().intersects(getBounds())) {
	        if (panel.getPlayer(1).getBoundsRight().intersects(getBounds())) {
	        	xa = -xa;
	        	x++;
	        } else if (panel.getPlayer(2).getBoundsLeft().intersects(getBounds())) {
	        	xa = -xa;
	        	x--;
	        }
	        if (panel.getPlayer(1).getBoundsTop().intersects(getBounds()) || panel.getPlayer(2).getBoundsTop().intersects(getBounds())) {
	        	ya = Math.min(ya, -ya);
	        	y--;
	        } else if (panel.getPlayer(2).getBoundsBottom().intersects(getBounds()) || panel.getPlayer(2).getBoundsBottom().intersects(getBounds())) {
	        	ya = Math.max(ya, -ya);
	        	y++;
	        }
    	}
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
    
    public void paint(Graphics2D g) {
        g.fillOval(x, y, SIZE, SIZE);
    }
}
