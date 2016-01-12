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
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
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

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import multiplayer.pong.dao.UsersDAO;
import multiplayer.pong.models.JTableModel;
import multiplayer.pong.socket.SocketHandler;

/**
 *
 * @author Anonymus
 */
public class LobbyFrame extends javax.swing.JFrame {
	private Socket socket;
	private UsersDAO daoUsers = new UsersDAO();
	private Vector<String> connectedPlayers = new Vector<String>();
	private Vector<String> connectedFriends = new Vector<String>();
    /**
     * Creates new form LobbyFrame
     */
    public LobbyFrame() {
    	getContentPane().setPreferredSize(new Dimension(800, 600));
    	setResizable(false);
    	setMaximumSize(new Dimension(800, 600));
    	getContentPane().setMaximumSize(new Dimension(800, 600));
    	// Initialisations
    	getContentPane().setSize(new Dimension(800, 600));
    	getContentPane().setBackground(new Color(0, 0, 0));
        initComponents();
        usernamesT.setShowHorizontalLines(false);
        friendsT.setShowHorizontalLines(false);
        ta.setMargin(new Insets(10, 10, 10, 10));
        // Listen to events
        welcomeMessage();
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
        }).on("userDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... arg0) {
				String username = (String) arg0[0];
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
    
    private void refresh(){
        usernamesT.setModel(new JTableModel(connectedPlayers, usernamesT.getColumnName(0)));
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
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(81, 20, 237));
    	appendMessage("Bienvenue à bord!\n"
    			+ "  >> Pour afficher l'aide, tapez '/help' dans la zone de texte en bas.\n", set);
    }
    
    private void displayHelp() {
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, true);
    	StyleConstants.setForeground(set, new Color(48, 140, 38));
    	appendMessage("Instructions:\n"
    			+ "/help : Afficher ce menu\n", set);
    }
    
    private void commandBtnActionPerformed(ActionEvent e) {
    	String input = cmdPrompt.getText();
    	handleCommand(input);
    	cmdPrompt.setText("");
    }
    
    private void handleCommand(String input) {
    	String regex = "^\\s*\\/(\\w+) ?(\\w+)? ?(\\w+)?\\s*";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(input);
    	if (matcher.matches()) {
    		String command = matcher.group(1);
    		switch (command) {
    		case "help":
    			displayHelp();
    			break;
    		}
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
        usernamesT = new javax.swing.JTable();

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
        
        lblAmisConnects = new JLabel("Amis connect\u00E9s");
        lblAmisConnects.setForeground(Color.WHITE);
        lblAmisConnects.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        
        cmdPrompt = new JTextField();
        cmdPrompt.setColumns(10);
        
        scrollPane_1 = new JScrollPane();
        
        lblWelcome = new JLabel("Bienvenue, " + SocketHandler.username);
        lblWelcome.setHorizontalAlignment(SwingConstants.RIGHT);
        lblWelcome.setFont(new Font("Georgia", Font.PLAIN, 18));
        lblWelcome.setForeground(Color.WHITE);

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
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblUtilisateursEnligne, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(scrollPane, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(lblAmisConnects, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
        				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(commandBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(jScrollPane2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblLobbyPrincipal, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblWelcome, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblAmisConnects, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
        					.addGap(1)
        					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(lblUtilisateursEnligne, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))
        			.addGap(9)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cmdPrompt, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        				.addComponent(commandBtn))
        			.addGap(22))
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
    private JLabel lblAmisConnects;
    private JTextField cmdPrompt;
    private JScrollPane scrollPane_1;
    private JLabel lblWelcome;
    private JTextPane ta;
}
