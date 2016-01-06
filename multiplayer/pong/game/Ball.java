package multiplayer.pong.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JOptionPane;

public class Ball {
	private static final int WIDTH = 30, HEIGHT = 30;
    private Pong game;
    private int x, y, xa = 4, ya = 4;
    
    public Ball(Pong game) {
    	this.game = game;
    	x = game.getWidth() / 2;
        y = game.getHeight() / 2;
    }
    
    public void update() {
        x += xa;
        y += ya;
        if (x <= 0) {
            game.getPanel().increaseScoreFor(1);
            xa = -xa;
        }
        else if (x >= game.getWidth() - WIDTH) {
            game.getPanel().increaseScoreFor(2);
            xa = -xa;
        }
        else if (y < 0 || y >= game.getHeight() - HEIGHT)
            ya = -ya;
        checkCollision();
    }
    
    public void checkCollision() {
        if (game.getPanel().getPlayer(1).getBounds().intersects(getBounds()) || game.getPanel().getPlayer(2).getBounds().intersects(getBounds()))
            xa = -xa;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void paint(Graphics2D g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}
