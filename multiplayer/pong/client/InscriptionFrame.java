/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.client;
import java.awt.Color;
import multiplayer.pong.dao.UsersDAO;

import multiplayer.pong.models.User;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JRootPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 *
 * @author Anonymus
 */
public class InscriptionFrame extends javax.swing.JFrame {
    
    UsersDAO daousers = new UsersDAO();

    /**
     * Creates new form InscriptionFrame
     */
    public InscriptionFrame() {
        initComponents();
        this.getContentPane().setBackground( Color.black );
        JRootPane rootPane = this.getRootPane();
    	rootPane.setDefaultButton(Btn);
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
        loginTxtField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        pwdTxtFeild = new javax.swing.JPasswordField();
        Btn = new javax.swing.JButton();
        loginError = new javax.swing.JLabel();
        pwdError = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Username");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");

        Btn.setBackground(new java.awt.Color(0, 51, 51));
        Btn.setForeground(new java.awt.Color(255, 255, 255));
        Btn.setText("S'enregistrer");
        Btn.setMaximumSize(new java.awt.Dimension(95, 23));
        Btn.setMinimumSize(new java.awt.Dimension(95, 23));
        Btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnActionPerformed(evt);
            }
        });

        loginError.setBackground(new java.awt.Color(0, 0, 0));
        loginError.setForeground(new java.awt.Color(255, 255, 255));

        pwdError.setBackground(new java.awt.Color(0, 0, 0));
        pwdError.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Stencil", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bienvenue ");

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Veuillez saisir un nom d'utilisateur et un mot de passe pour nous rejoindre :D");
        
        JButton btnRetour = new JButton("Retour");
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setBackground(new Color(0, 51, 51));
        btnRetour.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		btnRetourActionPerformed(evt);
        	}
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(187)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel1)
        					.addGap(32))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)))
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(loginTxtField, 190, 190, 190)
        				.addComponent(pwdTxtFeild, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
        				.addComponent(Btn, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(10)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(loginError, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        						.addComponent(pwdError, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))))
        			.addGap(282))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(225, Short.MAX_VALUE)
        			.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE)
        			.addGap(123))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(300, Short.MAX_VALUE)
        			.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 327, GroupLayout.PREFERRED_SIZE)
        			.addGap(173))
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(396, Short.MAX_VALUE)
        			.addComponent(btnRetour)
        			.addGap(339))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(99, Short.MAX_VALUE)
        			.addComponent(jLabel3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(jLabel4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
        			.addGap(38)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(loginTxtField, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(loginError, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(pwdTxtFeild, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        				.addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pwdError, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
        			.addGap(25)
        			.addComponent(Btn, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(btnRetour)
        			.addGap(53))
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnRetourActionPerformed(java.awt.event.ActionEvent evt) {
    	LoginFrame fr = new LoginFrame();
    	this.setVisible(false);
    	fr.setVisible(true);
    }

    private void BtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnActionPerformed
        // TODO add your handling code here:
        loginError.setText("");
        pwdError.setText("");
        
        String username = loginTxtField.getText();
        User user = daousers.findByUsername(username);
        if( !(user == null) ){
            loginError.setText("login existant");
        }
        else if(pwdTxtFeild.getText().length()==0){
            pwdError.setText("veuillez entrer un mot de pass");
        }
        else
        {
            daousers.insert(username, pwdTxtFeild.getText());
            LoginFrame l = new LoginFrame();
            this.setVisible(false);
            l.setVisible(true);
        }
    }//GEN-LAST:event_BtnActionPerformed

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
            java.util.logging.Logger.getLogger(InscriptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InscriptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InscriptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InscriptionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InscriptionFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel loginError;
    private javax.swing.JTextField loginTxtField;
    private javax.swing.JLabel pwdError;
    private javax.swing.JPasswordField pwdTxtFeild;
}
