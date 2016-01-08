/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.dao;

import com.mongodb.MongoException;
import java.net.UnknownHostException;
import java.util.Vector;
import multiplayer.pong.models.User;
import multiplayer.pong.models.stats;
import org.bson.Document;

/**
 *
 * @author pc
 */
public class StatsDAO extends DAO {

    /**
     *
     */
    public StatsDAO() {
        super("stats");
    }
    
//    public int partiesGagnes(User user){
//        
//    }
//    public int partiesPerdus(User user){
//        
//    }
//    public Vector<stats> historiqueParties(User user){
//        
//        
//    }
        public void ajouterStat(User usr1 ,User usr2 , int scr1 , int scr2)
    {
        this.collection.insertOne(new Document ("player1",usr1.getName())
                .append("player2", usr2.getName())
                .append("score1", scr1)
                .append("score2", scr2)
                );
        
    }
        
        //juste pour tester
        	public static void main(String[] args) {
                    try
                    {
                    StatsDAO A = new StatsDAO();
                    User usr = new User("a", "b");
                    User B = new User("c", "d");
                    A.ajouterStat(usr, B, 1, 10);
                    }
                    catch (MongoException e)
                    {
                         System.out.println("2"+e);
                    }
	}
}
