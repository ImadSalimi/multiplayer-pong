/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.dao.FriendRequestsDAO;
import multiplayer.pong.dao.GamesDAO;
import multiplayer.pong.dao.UsersDAO;
import multiplayer.pong.exceptions.UnknownCommandException;
import multiplayer.pong.game.Pong;
import multiplayer.pong.models.JTableModel;
import multiplayer.pong.socket.SocketHandler;

/**
 *
 * @author Anonymus
 */
public class LobbyFrame extends javax.swing.JFrame {
	private Socket socket;
	private UsersDAO daoUsers = new UsersDAO();
	private GamesDAO daoGames = new GamesDAO();
	private FriendRequestsDAO daoReq = new FriendRequestsDAO();
	private Vector<String> connectedPlayers = new Vector<String>();
	private Vector<String> connectedFriends = new Vector<String>();
    /**
     * Creates new form LobbyFrame
     */
    public LobbyFrame() {
    	setTitle("Lobby - PongNOW!");
    	getContentPane().setPreferredSize(new Dimension(800, 600));
    	setResizable(false);
    	setMaximumSize(new Dimension(800, 600));
    	getContentPane().setMaximumSize(new Dimension(800, 600));
    	// Initialisations
    	getContentPane().setSize(new Dimension(800, 600));
    	getContentPane().setBackground(new Color(0, 0, 0));
        initComponents();
        JRootPane rootPane = this.getRootPane();
    	rootPane.setDefaultButton(commandBtn);
        usernamesT.setShowHorizontalLines(false);
        friendsT.setShowHorizontalLines(false);
        ta.setMargin(new Insets(10, 10, 10, 10));
        // Display initial text
        welcomeMessage();
        pendingRequests();
        // Listen to events
        socket = SocketHandler.getSocket();
        handleSockets();
        refresh();
    }
    
    private void handleSockets() {
    	socket.on("userConnected", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String username = (String) arg0[0];
				getConnectedFriends();
				if (connectedFriends.contains(username))
					appendMessage("Votre ami " + username + " vient de se connecter!\n", null);
				refresh();
			}
		}).on("connectedPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... arg0) {
                JSONArray players = (JSONArray) arg0[0];
                Vector<String> online = new Vector<String>();
                try {
                    for(int i=0; i<players.length(); i++){
                        online.add(players.getJSONObject(i).getString("username"));
                    }
                } catch(JSONException e) {}
                connectedPlayers = online;
                getConnectedFriends();
                refresh();
            }
        }).on("friendRequest", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String from = (String) arg0[0];
				displayNotification("Vouz avez une demande d'ajout de " + from + "\n");
				displayHelp("  >> Utilisez la commande '/accepterAmi "+from+"' pour confirmer la demande.\n");
			}
		}).on("friendRequestAck", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String username = (String) arg0[0];
				displayNotification(username + " est maintenant votre ami.\n");
				displayHelp("Invitez le à une partie en tapant: '/challenge " + username + "'\n");
			}
		}).on("challenge", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String username = (String) arg0[0];
				displayWarning(username + " vous invite à une partie de Pong\n");
				displayHelp("Tapez '/accepter " + username + "' pour joueur contre lui\n"
						+ "ou '/refuser " + username + "' pour refuser\n");
			}
		}).on("challengeAck", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject) arg0[0];
				try {
					String opponent = data.getString("opponent");
					if (!data.getBoolean("accepted")) {
						displayWarning(opponent + " a refusé votre défi.\n");
					} else {
						// Start the game
						daoGames.startGame(SocketHandler.username, opponent);
						SocketHandler.startGame(opponent);
					}
				} catch (JSONException e) {}
			}
		}).on("startGame", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				JSONObject data = (JSONObject) arg0[0];
				try {
					String player1 = data.getString("player1");
					String player2 = data.getString("player2");
					if (SocketHandler.username.equals(player1) || SocketHandler.username.equals(player2)) {
						socket.emit("joinRoom", player1);
						displayWarning("La partie va commencer dans 5 secondes...\n");
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							String opponent = SocketHandler.username.equals(player1) ? player2 : player1;
							String location = SocketHandler.username.equals(player1) ? "home" : "away";
							public void run() {
								Pong game = new Pong(location, opponent);
							}
						}, 5000);
					} else {
						displayNotification("Un défi a commencé: " + player1 + " vs " + player2 + "\n");
					}
				} catch (JSONException e) {}
			}
		}).on("userDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String username = (String) arg0[0];
				daoGames.cancelRequest(username, null);
				if (connectedFriends.contains(username))
					appendMessage("Votre ami " + username + " vient de se déconnecter!\n", null);
				connectedPlayers.remove(username);
				getConnectedFriends();
				refresh();
			}
		});
    }
    
    private void getConnectedFriends() {
    	 // get connected friends
        Vector<String> friends = daoUsers.getFriends(SocketHandler.username);
        Vector<String> res = new Vector<String>();
        for (String friend : friends) {
        	if (connectedPlayers.contains(friend))
        		res.add(friend);
        }
        connectedFriends = res;
    }
    
    private void refresh() {
    	// List of connected players (without connected friends)
    	Vector<String> online = new Vector<String>();
    	for (String username : connectedPlayers) {
    		if (!connectedFriends.contains(username))
    			online.add(username);
    	}
        usernamesT.setModel(new JTableModel(online, usernamesT.getColumnName(0)));
        friendsT.setModel(new JTableModel(connectedFriends, friendsT.getColumnName(0)));
    }
    
    private void appendMessage(String message, SimpleAttributeSet set) {
    	// If no set of styles was passed, use the default
    	if (set == null)
    		set = new SimpleAttributeSet();
    	Document doc = ta.getStyledDocument();
    	try {
			doc.insertString(doc.getLength(), message, set);
		} catch (BadLocationException e) {}
    }
    
    private void welcomeMessage() {
    	displayNotification("Bienvenue à  bord!\n"
    			+ "  >> Pour afficher l'aide, tapez '/aide' dans la zone de texte en bas.\n"
    			+ "  >> Vous pouvez aussi envoyer des messages publiques depuis cette zone.\n");
    }
    
    private void pendingRequests() {
    	Vector<String> req = daoReq.pendingRequests(SocketHandler.username);
    	int count = req.size();
    	if (count == 0) return;
    	displayWarning("Vous avez " + count + " demande" + (count != 1 ? "s" : "") + " d'ajout de: " + String.join(", ", req) + "\n");
    	displayWarning("Utilisez la commande '/accepterAmi [nom]' pour accepter une demande.\n");
    }
    
    private void displayHelp(String message) {
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(48, 140, 38));
    	if (message == null)
	    	appendMessage("Instructions:\n"
	    			+ "/aide : Affiche ce menu\n"
	    			+ "/ajouter [nom] : Envoie une demande d'ajout à un joueur\n"
	    			+ "/accepterAmi [nom] : Accepte une demande d'ajout reçue\n"
	    			+ "/m [nom] : envoie un message privé à un ami connecté\n"
	    			+ "/challenge [nom] : Invite un ami à une partie de Pong\n"
	    			+ "/supprimer [nom] : Supprime le joueur de votre liste d'amis\n", set);
    	else
    		appendMessage(message, set);
    }
    
    private void displayError(String error) {
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(245, 10, 10));
    	appendMessage(error, set);
    }
    
    private void displayWarning(String message) {
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(255, 178, 46));
    	appendMessage(message, set);
    }
    
    private void displayNotification(String message) {
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(81, 20, 237));
    	appendMessage(message, set);
    }
    
    private void commandBtnActionPerformed(ActionEvent e) {
    	String input = cmdPrompt.getText();
    	try {
    		handleCommand(input);
    	} catch (UnknownCommandException ex) {
    		displayError(ex.getMessage());
    	}
    	cmdPrompt.setText("");
    }
    
    private void handleCommand(String input) throws UnknownCommandException {
    	String regex = "^\\s*\\/(\\w+) ?(\\w+)? ?(\\w+)?\\s*";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(input);
    	if (matcher.matches()) {
    		String command = matcher.group(1);
    		String arg1 = matcher.group(2);
    		String arg2 = matcher.group(3);
    		switch (command) {
    		case "aide":
    			displayHelp(null);
    			break;
    		case "m":
    			if (arg1 == null || arg2 == null)
    				throw new UnknownCommandException("Utilisation: /m [nom] [message]\n");
    			if (!connectedPlayers.contains(arg1)) {
    				displayWarning("Ce joueur n'est pas connecté en ce moment!\n");
    			} else if (!connectedPlayers.contains(arg1)) {
    				displayWarning("Vous ne pouvez envoyer des messages qu'à  vos amis\n");
    			} else {
    				
    				SocketHandler.sendMessage(arg1, arg2);
    			}
    			break;
    		case "ajouter":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /ajouter [nom]\n");
    			if (daoUsers.findByUsername(arg1) == null) {
    				displayWarning("Utilisateur inexistant!\n");
    			} else if (daoUsers.getFriends(SocketHandler.username).contains(arg1)) {
    				displayWarning("Ce joueur existe déjà  dans votre liste d'amis!\n");
    			} else {
    				daoReq.send(SocketHandler.username, arg1);
    				SocketHandler.friendRequest(arg1);
    				displayNotification("Une demande d'ajout a été envoyée.\n");
    			}
    			break;
    		case "accepterAmi":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /accepterAmi [nom]\n");
    			if (daoReq.isPending(arg1, SocketHandler.username)) {
    				daoReq.accept(arg1, SocketHandler.username);
    				SocketHandler.friendRequestAck(arg1);
    				displayNotification("Vous avez accepté la demande d'ajout.\n");
    			} else {
    				displayError("Ce joueur ne vous a pas envoyé une demande d'ajout.\n");
    			}
    			break;
    		case "supprimer":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /accepterAmi [nom]\n");
    			if (!daoUsers.getFriends(SocketHandler.username).contains(arg1)) {
    				displayError("Ce joueur n'est pas dans votre liste d'amis.\n");
    			} else {
    				daoReq.remove(SocketHandler.username, arg1);
    				displayNotification("Joueur supprimé de votre liste d'amis.\n");
    				socket.emit("getConnectedPlayers");
    			}
    			break;
    		case "challenge":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /challenge [nom]\n");
    			if (daoUsers.findByUsername(arg1) == null) {
    				displayWarning("Utilisateur inexistant!\n");
    			} else if (!connectedPlayers.contains(arg1)) {
    				displayWarning("Ce joueur n'est pas connecté en ce moment!\n");
    			} else if (daoGames.gameIsPending(SocketHandler.username, arg1)) {
    				displayError("Vous avez déjà  invité ce joueur à  une partie.\n");
    			} else {
    				displayNotification("Une invitation a été envoyée.\n");
    				daoGames.initialize(SocketHandler.username, arg1);
    				SocketHandler.challenge(arg1);
    			}
    			break;
    		case "accepter":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /challenge [nom]\n");
    			if (daoUsers.findByUsername(arg1) == null) {
    				displayWarning("Utilisateur inexistant!\n");
    			} else if (!daoGames.gameIsPending(arg1, SocketHandler.username)) {
    				displayWarning("Ce joueur ne vous a pas invité à une partie!\n");
    			} else {
    				SocketHandler.challengeAck(arg1, true);
    			}
    			break;
    		case "refuser":
    			if (arg1 == null)
    				throw new UnknownCommandException("Utilisation: /challenge [nom]\n");
    			if (daoUsers.findByUsername(arg1) == null) {
    				displayWarning("Utilisateur inexistant!\n");
    			} else if (!daoGames.gameIsPending(arg1, SocketHandler.username)) {
    				displayWarning("Ce joueur ne vous a pas invité à une partie!\n");
    			} else {
    				displayNotification("Vous avez refusé l'invitation.\n");
    				daoGames.cancelRequest(arg1, SocketHandler.username);
    				SocketHandler.challengeAck(arg1, false);
    			}
    			break;
    		default:
    			throw new UnknownCommandException("Commande inconnue, veuillez revoir le menu '/aide'.\n");
    		}
    	} else {
    		// Public message
    	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane2.setBackground(Color.WHITE);
        usernamesT = new javax.swing.JTable();
        usernamesT.setBackground(Color.WHITE);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        usernamesT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Username"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        usernamesT.setGridColor(new java.awt.Color(0, 0, 0));
        usernamesT.setInheritsPopupMenu(true);
        jScrollPane2.setViewportView(usernamesT);
        if (usernamesT.getColumnModel().getColumnCount() > 0) {
            usernamesT.getColumnModel().getColumn(0).setResizable(false);
        }
        
        JLabel lblUtilisateursEnligne = new JLabel("Utilisateurs en-ligne");
        lblUtilisateursEnligne.setForeground(new Color(255, 255, 255));
        lblUtilisateursEnligne.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        
        commandBtn = new JButton("Envoyer");
        commandBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		commandBtnActionPerformed(e);
        	}
        });
        
        JLabel lblLobbyPrincipal = new JLabel("Lobby Principal");
        lblLobbyPrincipal.setFont(new Font("Georgia", Font.PLAIN, 70));
        lblLobbyPrincipal.setForeground(new Color(255, 255, 255));
        lblLobbyPrincipal.setBackground(new Color(0, 0, 0));
        
        scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        
        cmdPrompt = new JTextField();
        cmdPrompt.setColumns(10);
        cmdPrompt.grabFocus();
        
        scrollPane_1 = new JScrollPane();
        
        lblWelcome = new JLabel("Bienvenue, " + SocketHandler.username);
        lblWelcome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblWelcome.setFont(new Font("Georgia", Font.PLAIN, 18));
        lblWelcome.setForeground(Color.WHITE);
        
        JLabel label = new JLabel("Amis connect\u00E9s");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 605, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cmdPrompt, 605, 605, 605))
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblLobbyPrincipal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGap(120)))
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblUtilisateursEnligne, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
        				.addComponent(commandBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(label, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE))
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
        				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblLobbyPrincipal, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblUtilisateursEnligne, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
        					.addGap(9)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cmdPrompt, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        						.addComponent(commandBtn))
        					.addGap(22))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
        					.addGap(492))))
        );
        
        ta = new JTextPane();
        ta.setEditable(false);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        scrollPane_1.setViewportView(ta);
        
        friendsT = new JTable();
        friendsT.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Username"
        	}
        ));
        scrollPane.setViewportView(friendsT);
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LobbyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LobbyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LobbyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LobbyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LobbyFrame().setVisible(true);
            }
        });
    }
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable usernamesT;
    private JButton commandBtn;
    private JScrollPane scrollPane;
    private JTable friendsT;
    private JTextField cmdPrompt;
    private JScrollPane scrollPane_1;
    private JLabel lblWelcome;
    private JTextPane ta;
}
