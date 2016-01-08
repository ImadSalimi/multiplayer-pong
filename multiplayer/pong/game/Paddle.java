package multiplayer.pong.game;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Paddle {
    public static final int WIDTH = 20, HEIGHT = 120;
    
    private Pong game;
    private int up, down;
    private int x;
    // ya is velocity in the y-axis
    private int y, ya = 2;
    private boolean goingUp = false, goingDown = false;

    
    public Paddle(Pong game, int up, int down, int x) {
        this.game = game;
        this.x = x;
        y = game.getHeight() / 2;
        this.up = up;
        this.down = down;
    }

    public void update() {
        if (goingUp && y > 0) y -= ya;
        else if (goingDown && y + HEIGHT < game.getHeight()) y += ya;
    }
    
    public void pressed(int keyCode) {
        if (keyCode == up)
            goingUp = true;
        else if (keyCode == down)
            goingDown = true;
    }

    public void released(int keyCode) {
        if (keyCode == up || keyCode == down) {
        	goingUp = goingDown = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

}
