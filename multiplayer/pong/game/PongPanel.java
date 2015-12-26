package multiplayer.pong.game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private Pong game;
	private Ball ball;
	private Paddle player1, player2;
	private int score1, score2;
	private enum PlayerId {ONE, TWO};
	
	public PongPanel(Pong game) {
		setBackground(Color.WHITE);
		this.game = game;
		Timer timer = new Timer(5, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public Paddle getPlayer(PlayerId id) {
		if (id == PlayerId.ONE)
			return player1;
		else
			return player2;
	}
	
	public void increaseScoreFor(PlayerId id) {
		if (id == PlayerId.ONE)
			score1++;
		else
			score2++;
	}
	
	public int getScore(PlayerId id) {
		return id == PlayerId.ONE ? score1 : score2; 
	}
	
	private void update() {
		ball.update();
		player1.update();
		player2.update()
	}

	@Override
	public void keyTyped(KeyEvent e) {
		;		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
