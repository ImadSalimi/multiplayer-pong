package multiplayer.pong.client;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class Main {
	MongoClient mongoClient;
	MongoDatabase db;
	
	public Main() {
		this.mongoClient = new MongoClient();
		this.db = mongoClient.getDatabase("test");
	}

	public static void main(String[] args) {
		Main main = new Main();
		MongoCollection<Document> users = main.db.getCollection("users");
		FindIterable<Document> iterable = users.find(new Document("name", "Iyad"));
		System.out.println(iterable.first());
		
	}

}
