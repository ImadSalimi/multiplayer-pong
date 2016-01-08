/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.models;

import java.util.Comparator;
import multiplayer.pong.dao.StatsDAO;

/**
 *
 * @author pc
 */
public class WinningComparator implements Comparator {
    StatsDAO stat = new StatsDAO();
    public int compare(Object o1 , Object o2)
    {
        User s1 = (User)o1;
        User s2 = (User)o2;
        if (stat.nbrePartiesGagnees(s1.getName())==stat.nbrePartiesGagnees(s1.getName()))
            return 0 ;
        else if (stat.nbrePartiesGagnees(s1.getName())>stat.nbrePartiesGagnees(s1.getName()))
            return 1 ;
        else return 1;
    }


}
