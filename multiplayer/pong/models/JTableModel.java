/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.models;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Anonymus
 */
public class JTableModel extends AbstractTableModel{
    
    private int nbL ;
    private int nbC ;
    private String Titre ;
    private Vector<String> Meslignes = new Vector<String>();

    public JTableModel(Vector<String> table) {
        Meslignes = table ;
        nbL = table.size();
        nbC = 1 ;
        Titre = "username";
               
    }
    
    public int getRowCount() {
        return nbL;
    }

    @Override
    public int getColumnCount() {
        return nbC;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return Meslignes.get(rowIndex);
    }
    
    public String getColumnName(int c){
        return Titre;
    }
    
    
    
    
}
