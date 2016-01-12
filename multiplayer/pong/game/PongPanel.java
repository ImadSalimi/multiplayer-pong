package multiplayer.pong.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	public Pong game;
	private Ball ball;
	private PaddlePlayer player1;
	private PaddleOpponent player2;
	private GameState state;
	
	public PongPanel(Pong game) {
		setBackground(Color.BLACK);
		this.game = game;
		state = GameState.GAME;
		ball = new Ball(this);
		player1 = new PaddlePlayer(this, 20);
		player2 = new PaddleOpponent(this, game.getWidth() - Paddle.WIDTH - 20);		
		Timer timer = new Timer(4, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public Paddle getPlayer(int id) {
		return id == 1 ? player1 : player2;
	}
	
	public void increaseScoreFor(int id) {
		if (id == 1) player1.increaseScore();
		else player2.increaseScore();
	}
	
	public int getScore(int id) {
		return id == 1 ? player1.getScore() : player2.getScore(); 
	}
	
	private void update() {
		if (state == GameState.GAME) {
			ball.update();
			player1.update();
			player2.update();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		;		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player1.pressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player1.released(e.getKeyCode());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, game.getWidth(), game.getHeight());
		g2d.setColor(Color.white);
		g2d.drawString(getScore(1) + " : " + getScore(2), game.getWidth() / 2, 20);
		
		ball.paint(g2d);
		player1.paint(g2d);
		player2.paint(g2d);
		g.drawImage(bufferedImage, 0, 0, null);
	}
	
}
