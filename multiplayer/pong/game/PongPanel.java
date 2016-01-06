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
	private Pong game;
	private Ball ball;
	private Paddle player1, player2;
	private int score1, score2;
	
	public PongPanel(Pong game) {
		setBackground(Color.WHITE);
		this.game = game;
		ball = new Ball(game);
		player1 = new Paddle(game, KeyEvent.VK_UP, KeyEvent.VK_DOWN, game.getWidth() - Paddle.WIDTH - 20);
		player2 = new Paddle(game, KeyEvent.VK_Z, KeyEvent.VK_S, 20);		
		Timer timer = new Timer(5, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public Paddle getPlayer(int id) {
		return id == 1 ? player1 : player2;
	}
	
	public void increaseScoreFor(int id) {
		if (id == 1) score1++;
		else score2++;
	}
	
	public int getScore(int id) {
		return id == 1 ? score1 : score2; 
	}
	
	private void update() {
		ball.update();
		player1.update();
		player2.update();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		;		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		player1.pressed(e.getKeyCode());
		player2.pressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player1.released(e.getKeyCode());
		player2.released(e.getKeyCode());
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
		g2d.drawString(game.getPanel().getScore(1) + " : " + game.getPanel().getScore(2), game.getWidth() / 2, 20);
		
		ball.paint(g2d);
		player1.paint(g2d);
		player2.paint(g2d);
		g.drawImage(bufferedImage, 0, 0, null);
	}
	
}
