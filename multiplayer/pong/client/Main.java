package multiplayer.pong.client;

import multiplayer.pong.socket.SocketHandler;

public class Main {

	public static void main(String[] args) {
		// Instantiate socket handler and listen to events
		SocketHandler.connectSocket();
		SocketHandler.configSocketEvents();
		// Show the login frame
		LoginFrame loginFrame = new LoginFrame();
		loginFrame.setVisible(true);
	}

}
