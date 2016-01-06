package multiplayer.pong.client;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import multiplayer.pong.dao.UsersDAO;

import static com.mongodb.client.model.Filters.*;

public class Main {

	public static void main(String[] args) {
		UsersDAO daoUsers = new UsersDAO();
		System.out.println(daoUsers.findByUsername("Iyad"));
	}

}
