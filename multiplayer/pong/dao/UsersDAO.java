package multiplayer.pong.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

import multiplayer.pong.models.User;

public class UsersDAO extends DAO {

	public UsersDAO() {
		super("users");
	}
	
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		FindIterable<Document> it = this.collection.find();
		it.forEach(new Block<Document>() {
			public void apply(final Document document) {
				users.add(new User(document.getString("name"), document.getString("password")));
			}
		});
		return users;
	}

}
