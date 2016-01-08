/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.client;

import java.awt.Color;
import java.awt.Graphics;
import multiplayer.pong.dao.UsersDAO;
import multiplayer.pong.models.User;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;
import multiplayer.pong.socket.SocketHandler;

/**
 *
 * @author Anonymus
 */
public class LoginFrame extends javax.swing.JFrame {
  
    UsersDAO daousers = new UsersDAO();

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
    	getContentPane().setSize(new Dimension(800, 600));
        initComponents();
        this.getContentPane().setBackground( Color.black );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        loginTxtFeild = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        loginBtn = new javax.swing.JButton();
        loginError = new javax.swing.JLabel();
        pwdError = new javax.swing.JLabel();
        pwdTxtFeild = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("password");

        loginBtn.setBackground(new java.awt.Color(0, 51, 51));
        loginBtn.setForeground(new java.awt.Color(255, 255, 255));
        loginBtn.setText("Se connecter");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        loginError.setBackground(new java.awt.Color(0, 0, 0));
        loginError.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        loginError.setForeground(new java.awt.Color(255, 255, 255));

        pwdError.setBackground(new java.awt.Color(0, 0, 0));
        pwdError.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        pwdError.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Noveau Compte ? ");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Stencil", 1, 100)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PONG !");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(322)
        					.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(188)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
        						.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        							.addComponent(pwdError, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        							.addComponent(pwdTxtFeild, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        							.addComponent(loginTxtFeild)
        							.addComponent(loginBtn, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
        						.addGroup(layout.createSequentialGroup()
        							.addGap(10)
        							.addComponent(loginError, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap(262, Short.MAX_VALUE))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(220, Short.MAX_VALUE)
        			.addComponent(jLabel3)
        			.addGap(175))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGap(46)
        			.addComponent(jLabel3)
        			.addPreferredGap(ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
        				.addComponent(loginTxtFeild, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(loginError, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(pwdTxtFeild, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel2, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pwdError, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(loginBtn, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
        			.addGap(12)
        			.addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
        			.addGap(47))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBtnActionPerformed
        // TODO add your handling code here:
        loginError.setText("");
        pwdError.setText("");
        
        String username = loginTxtFeild.getText();
        User user = daousers.findByUsername(username);
        if(user == null){
            loginError.setText("login inexistant !");
        }
        else if(!user.getPassword().equals(pwdTxtFeild.getText())){
            pwdError.setText("mot de pass errone !");
        }
        else {
            SocketHandler.userConnected(username);
            LobbyFrame l = new LobbyFrame();
            this.setVisible(false);
            l.setVisible(true);
            
        }
        
        
        
       
        
    }//GEN-LAST:event_loginBtnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        InscriptionFrame I = new InscriptionFrame();
        this.setVisible(false);
        I.setVisible(true);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
                
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton loginBtn;
    private javax.swing.JLabel loginError;
    private javax.swing.JTextField loginTxtFeild;
    private javax.swing.JLabel pwdError;
    private javax.swing.JPasswordField pwdTxtFeild;
    // End of variables declaration//GEN-END:variables
}
