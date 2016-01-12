package multiplayer.pong.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

public class FriendRequestsDAO extends DAO {
	private UsersDAO daoUsers;
	
	public FriendRequestsDAO() {
		super("requests");
		daoUsers = new UsersDAO();
	}
	
	// Returns a list with the names of people who are still waiting to get accepted
	public Vector<String> pendingRequests(String to) {
		FindIterable<Document> it = this.collection.find(new Document("to", to));
		Vector<String> res = new Vector<String>();
		it.forEach(new Block<Document>() {
			public void apply(final Document document) {
				res.add(document.getString("from"));
			}
		});
		return res;
	}
	
	// Checks if a friend request is waiting for approval
	public boolean isPending(String from, String to) {
		return pendingRequests(to).contains(from);
	}
	
	public void send(String from, String to) {
		if (isPending(from, to)) return;
		this.collection.insertOne(new Document("from", from).append("to", to));
	}
	
	public void accept(String from, String to) {
		daoUsers.addFriend(from, to);
		daoUsers.addFriend(to, from);
	}
	
	public void remove(String user1, String user2) {
		daoUsers.removeFriend(user1, user2);
		daoUsers.removeFriend(user2, user1);
	}

}
