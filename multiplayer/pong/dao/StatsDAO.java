/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiplayer.pong.dao;

import com.mongodb.Block;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import java.net.UnknownHostException;
import static java.util.Arrays.asList;
import java.util.Vector;
import multiplayer.pong.models.Stats;
import multiplayer.pong.models.User;
import org.bson.Document;
import java.io.*;
import java.util.Collections;
import multiplayer.pong.models.WinningComparator;

/**
 *
 * @author pc
 */
public class StatsDAO extends DAO {
    UsersDAO user = new UsersDAO();
    /**
     *
     */
    public StatsDAO() {
        super("statistiques");
    }
    
        public Vector<String> classement(Boolean test)//true pour croissant false pour decroissant
        {
            Vector<User> list = user.findAll();
            Vector<String> result = new Vector<String>();

            Collections.sort(list , new WinningComparator());
            if(test) Collections.reverse(list);
          for (User u : list)
          {
              result.add(u.getName()+" : "+nbrePartiesGagnees(u.getName())+"W");
          }
          return result ;
            
            
        }
        
        public Vector<Stats> historique(String username)
        {
            final Vector<Stats> historique = new Vector<Stats>();
            
            FindIterable<Document> iterable = this.collection.find(new Document("$or",asList(new Document("player1",username)
                    ,new Document("player2",username))));

            iterable.forEach(new Block<Document>(){
                @Override
                public void apply(final Document document) {
                        historique.add(new Stats(document.getString("player1"), document.getString("player2")
                        , document.getInteger("score1"), document.getInteger("score2")));
                     }
            });
            
            return historique;
        }

         public Vector<Stats> historiqueGlobal()
        {
            final Vector<Stats> historique=new Vector<Stats>();
            
            FindIterable<Document> iterable = this.collection.find();

            iterable.forEach(new Block<Document>(){
                @Override
                public void apply(final Document document) {
                        historique.add(new Stats(document.getString("player1"), document.getString("player2")
                        , document.getInteger("score1"), document.getInteger("score2")));
                     }
            });
            
            return historique;
        }
        
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
        
        public int nbrePartiesPerdues(String username)
        {
          return  (int) this.collection.count(new Document("$or",asList(new Document("player1",username).append("score1",new Document("$lt",5)) 
                    , new Document("player2",username).append("score2",new Document("$lt",5) ))));
        }

    	public static void main(String[] args) {
            //TEST unitaire , poke ISTIA Angers :v
            StatsDAO A = new StatsDAO();
           /* A.ajouterStat("Iyad", "imad", 5, 2);
            A.ajouterStat("naoufal", "imad", 4, 5);
            A.ajouterStat("Iyad", "naoufal", 4, 5);
            A.ajouterStat("imad", "Iyad", 2, 5);
            System.out.println("a gagne "+A.nbrePartiesGagnees("naoufal"));
            System.out.println("a perdu "+A.nbrePartiesPerdues("naoufal"));
            Vector<Stats> vect = A.historiqueGlobal() ;
            System.out.println(vect.size());
            for (Stats B : vect){
                System.out.println(B);
            }*/
           
           
            
            
	}    


}
