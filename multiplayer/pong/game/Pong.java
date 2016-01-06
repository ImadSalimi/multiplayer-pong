package multiplayer.pong.game;

import java.awt.Color;

import javax.swing.JFrame;

public class Pong extends JFrame {
	private final static int W_WIDTH = 800, W_HEIGHT = 600;
	private PongPanel panel;
	
	public Pong() {
		setSize(W_WIDTH, W_HEIGHT);
		setTitle("Pong Online");
		setBackground(Color.white);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void start() {
		panel = new PongPanel(this);
		add(panel);
	}
	
	public PongPanel getPanel() {
		return panel;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pong game = new Pong();
		game.start();
	}

}
