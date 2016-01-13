package multiplayer.pong.dao;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

public class GamesDAO extends DAO {
	
	public GamesDAO() {
		super("games");
	}
	
	public void initialize(String from, String to) {
		this.collection.insertOne(new Document("state", "pending")
				.append("initiator", from)
				.append("recipient", to)
				.append("scores", Arrays.asList(0, 0))
				.append("winner", "")
				.append("timestamp", null));
	}
	
	public boolean gameIsPending(String from, String to) {
		int count = (int) this.collection.count(new Document("state", "pending")
				.append("initiator", from)
				.append("recipient", to));
		return count > 0;
	}
	
	public void startGame(String from, String to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = sdf.format(new Date());
		this.collection.updateOne(new Document("state", "pending")
								.append("initiator", from)
								.append("recipient", to),
							new Document("$set", new Document("state", "ongoing")
							.append("timestamp", timestamp)));
	}
	
	public void cancelRequest(String from, String to) {
		this.collection.deleteOne(new Document("state", "pending")
				.append("initiator", from)
				.append("recipient", to));
	}
	
}
