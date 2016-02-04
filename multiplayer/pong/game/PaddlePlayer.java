package multiplayer.pong.game;

import java.awt.event.KeyEvent;

import multiplayer.pong.socket.SocketHandler;

public class PaddlePlayer extends Paddle {
	
	public PaddlePlayer(PongPanel panel, int x) {
		super(panel, x);
	}
	
	public void pressed(int keyCode) {
        if (keyCode == KeyEvent.VK_UP)
            goingUp = true;
        else if (keyCode == KeyEvent.VK_DOWN)
            goingDown = true;
        SocketHandler.paddleMoved(y, goingUp, goingDown);
    }

    public void released(int keyCode) {
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
        	goingUp = goingDown = false;
        	SocketHandler.paddleMoved(y, goingUp, goingDown);
        }
    }
}
