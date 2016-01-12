package multiplayer.pong.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import multiplayer.pong.models.User;

public class UsersDAO extends DAO {

	public UsersDAO() {
		super("users");
	}
	
	public Vector<User> findAll() {
		final Vector<User> users = new Vector<User>();
		FindIterable<Document> it = this.collection.find();
		it.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				users.add(new User(document.getString("name"), document.getString("password")));
			}
		});
		return users;
	}
	
	public User findByUsername(String username) {
		Vector<User> users = findAll();
		User res = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().equals(username)) {
				res = users.get(i);
				break;
			}
		}
		return res;
	}
	
	public Vector<String> getFriends(String username) {
		Vector<String> result = new Vector<String>();
		FindIterable<Document> it = this.collection.find(new Document("name", username));
		it.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				ArrayList<String> friends = (ArrayList<String>) document.get("friends");
				for (String friend : friends) {
					result.add(friend);
				}
			}
		});
		return result;
	}
        
	public void insert(String username, String password){
	    this.collection.insertOne(
	            new Document("name", username)
	            .append("password", password)
	            .append("friends", Arrays.asList())
	    );
	}
	
	public void addFriend(String user1, String user2) {
		this.collection.updateOne(new Document("name", user1),
				new Document("$addToSet", new Document("friends", user2)));
	}
	
	public void removeFriend(String user1, String user2) {
		this.collection.updateOne(new Document("name", user1),
				new Document("$pull", new Document("friends", user2)));
	}

}
