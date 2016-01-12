package multiplayer.pong.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;


public class Ball {
	private static final int SIZE = 20;
    private PongPanel panel;
    private int x, y, xa, ya;
    
    public Ball(PongPanel panel) {
    	this.panel = panel;
    	reset();
    }
    
    public void update() {
        x += xa;
        y += ya;
        if (x + SIZE <= 5) {
            panel.increaseScoreFor(1);
            reset();
        } else if (x >= panel.game.getWidth() - 5) {
            panel.increaseScoreFor(2);
            reset();
        }
        else if (y < 0 || y >= panel.game.getHeight() - SIZE)
            ya = -ya;
        checkCollision();
    }
    
    private void reset() {
    	Random r = new Random();
    	x = panel.game.getWidth() / 2;
    	y = panel.game.getHeight() / 2;
    	xa = 2;
    	ya = 2;
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
