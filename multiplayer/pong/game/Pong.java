package multiplayer.pong.game;

import java.awt.Color;

import javax.swing.JFrame;

import multiplayer.pong.socket.SocketHandler;

public class Pong extends JFrame {
	private final static int W_WIDTH = 800, W_HEIGHT = 600;
	private PongPanel panel;
	public String location;
	
	public Pong(String location, String opponent) {
		this.location = location;
		setSize(W_WIDTH, W_HEIGHT);
		setTitle("Pong Online");
		setBackground(Color.white);
		setResizable(false);
		panel = new PongPanel(this, opponent);
		add(panel);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public PongPanel getPanel() {
		return panel;
	}
	
	public static void main(String[] args) {
		SocketHandler.connectSocket();
		Pong game = new Pong("Imad", "home");
	}

}
