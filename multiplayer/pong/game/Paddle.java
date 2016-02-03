package multiplayer.pong.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Paddle {
    public static final int WIDTH = 20, HEIGHT = 120;
    
    protected PongPanel panel;
    protected int score;
    protected int x, initialX;
    // ya is velocity in the y-axis
    protected int y, ya = 4;
    protected boolean goingUp = false, goingDown = false;

    
    public Paddle(PongPanel panel, int x) {
        this.panel = panel;
        this.initialX = x;
        this.x = x;
        y = panel.game.getHeight() / 2;
        this.score = 0;
    }

    public void update() {
        if (goingUp && y > 0) y -= ya;
        else if (goingDown && y + HEIGHT < panel.getHeight()) y += ya;
    }
    
    public int getScore() {
    	return score;
    }
    
    public Paddle increaseScore() {
    	score++;
    	return this;
    }
    
    public void reset() {
    	x = initialX;
    	y = panel.game.getHeight() / 2;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public Rectangle getBoundsTop() {
    	return new Rectangle(x, y, WIDTH, 1);
    }
    
    public Rectangle getBoundsRight() {
    	return new Rectangle(x + WIDTH, y, 1, HEIGHT);
    }
    
    public Rectangle getBoundsBottom() {
    	return new Rectangle(x, y + HEIGHT, WIDTH, 1);
    }
    
    public Rectangle getBoundsLeft() {
    	return new Rectangle(x, y, 1, HEIGHT);
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

}
