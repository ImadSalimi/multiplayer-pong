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
    
    public Ball(PongPanel panel) {
    	this.panel = panel;
    	this.socket = SocketHandler.getSocket();
    	handleSockets();
    	reset();
    }
    
    public void handleSockets() {
    	socket.on("ballLaunched", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject) arg0[0];
				try {
					if (panel.game.location.equals("home")) xa = data.getInt("xa");
					else xa = - data.getInt("xa");
					ya = data.getInt("ya");
					panel.state = GameState.PLAYING;
				} catch (JSONException e) {}
			}
		});
    }
    
    public void update() {
    	x += xa;
    	y += ya;
        if (x + SIZE <= 5) {
            panel.increaseScoreFor(2);
            reset();
        } else if (x >= panel.game.getWidth() - 5) {
            panel.increaseScoreFor(1);
            reset();
        }
        else if (y < 0 || y >= panel.game.getHeight() - SIZE)
            ya = -ya;
        checkCollision();
    }
    
    private void reset() {
    	panel.state = GameState.PAUSED;
    	x = panel.game.getWidth() / 2;
    	y = panel.game.getHeight() / 2;
    	// Go again after one second
    	if (panel.game.location.equals("home")) {
    		Timer timer = new Timer();
    		timer.schedule(new TimerTask() {
    			public void run() {
    				socket.emit("ballLaunched");
    			}
    		}, 1000);
    	}
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
