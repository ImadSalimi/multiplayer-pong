package multiplayer.pong.game;

public class Paddle {
    
    private static final int WIDTH = 10, HEIGHT = 60;
    private Pong game;
    private int up, down;
    private int x;
    private int y, ya;

    
    public Paddle(Pong game, int up, int down, int x) {
        this.game = game;
        this.x = x;
        y = game.getHeight() / 2;
        this.up = up;
        this.down = down;
    }

    public void update() {
        if (y > 0 && y < game.getHeight() - HEIGHT - 29)
            y += ya;
        else if (y == 0)
            y++;
        else if (y == game.getHeight() - HEIGHT - 29)
            y--;
    }

}
