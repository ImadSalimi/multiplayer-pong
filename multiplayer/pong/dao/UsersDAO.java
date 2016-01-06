package multiplayer.pong.dao;

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
		Vector<User> users = new Vector<User>();
		FindIterable<Document> it = this.collection.find();
		it.forEach(new Block<Document>() {
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

}
