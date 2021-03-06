package multiplayer.pong.dao;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class DAO {
	private MongoClient mongoClient;
	private MongoDatabase db;
	private MongoClientURI uri;
	protected MongoCollection<Document> collection;
	
	public DAO(String collection) {
		this.uri = new MongoClientURI("mongodb://imad:1928@ds045465.mongolab.com:45465/pong");
		this.mongoClient = new MongoClient(uri);
		this.db = mongoClient.getDatabase(uri.getDatabase());
		this.collection = db.getCollection(collection);
	}
}
