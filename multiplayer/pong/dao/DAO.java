package multiplayer.pong.dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class DAO {
	MongoClient mongoClient;
	MongoDatabase db;
	MongoCollection<Document> collection;
	private String host = "localhost";
	private int port = 27017;
	
	public DAO(String collection) {
		this.mongoClient = new MongoClient(host, port);
		this.db = mongoClient.getDatabase("test");
		this.collection = db.getCollection(collection);
	}
}
