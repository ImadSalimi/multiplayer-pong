/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.dao;

import com.mongodb.MongoException;
import java.net.UnknownHostException;
import static java.util.Arrays.asList;
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
        super("statistiques");
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
        public void ajouterStat(String usr1 ,String usr2 , int scr1 , int scr2)
    {
        this.collection.insertOne(new Document ("player1",usr1)
                .append("player2", usr2)
                .append("score1", scr1)
                .append("score2", scr2)
                );
        
    }
        public int nbrePartiesGagnees(String username)
        {
          return  (int) this.collection.count(new Document("$or",asList(new Document("player1",username).append("score1",5) 
                    , new Document("player2",username).append("score2",5 ))));
        }
    	public static void main(String[] args) {

            StatsDAO A = new StatsDAO();
            A.ajouterStat("naoufal", "imad", 5, 2);
            A.ajouterStat("naoufal", "imad", 4, 5);
            A.ajouterStat("imad", "naoufal", 5, 2);
            A.ajouterStat("imad", "naoufal", 2, 5);
            System.out.println(A.nbrePartiesGagnees("naoufal"));
            
	}    
}
