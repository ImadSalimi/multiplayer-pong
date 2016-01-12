package multiplayer.pong.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

public class Ball {
	private static final int SIZE = 30;
    private PongPanel panel;
    private int x, y, xa = 2, ya = 2;
    
    public Ball(PongPanel panel) {
    	this.panel = panel;
    	x = panel.game.getWidth() / 2;
        y = panel.game.getHeight() / 2;
    }
    
    public void update() {
        x += xa;
        y += ya;
        if (x <= 0) {
            panel.increaseScoreFor(1);
            xa = -xa;
        }
        else if (x >= panel.game.getWidth() - SIZE) {
            panel.increaseScoreFor(2);
            xa = -xa;
        }
        else if (y < 0 || y >= panel.game.getHeight() - SIZE)
            ya = -ya;
        checkCollision();
    }
    
    public void checkCollision() {
        if (panel.getPlayer(1).getBounds().intersects(getBounds()) || panel.getPlayer(2).getBounds().intersects(getBounds()))
            xa = -xa;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
    
    public void paint(Graphics2D g) {
        g.fillOval(x, y, SIZE, SIZE);
    }
}
