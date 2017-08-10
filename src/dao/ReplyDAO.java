package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import com.sun.org.apache.xpath.internal.operations.Bool;

import obicne.Reply;
import obicne.Topic;
import obicne.User;

public class ReplyDAO {


	public static ArrayList<Reply> get(int topicId, ArrayList<User> users){
		ArrayList<Reply> replies = new ArrayList<Reply>();
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies where topic_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, topicId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String content = resultSet.getString("content");
				int userId = resultSet.getInt("user_id");
				Date date = resultSet.getDate("creation_date");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				reply = new Reply(id, content, owner, date, topicId);
				replies.add(reply);
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replies;
	}

	
	public static ArrayList<Reply> getReplyByUserId(int userId, ArrayList<User> users){
		ArrayList<Reply> replies = new ArrayList<Reply>();
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies where user_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, userId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String content = resultSet.getString("content");
				int topicId = resultSet.getInt("user_id");
				Date date = resultSet.getDate("creation_date");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				reply = new Reply(id, content, owner, date, topicId);
				replies.add(reply);
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replies;
	}

	
	public static Reply getReplyById(int id, ArrayList<User> users){
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies where id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, id);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				String content = resultSet.getString("content");
				int userId = resultSet.getInt("user_id");
				int topicId = resultSet.getInt("topic_id");
				Date date = resultSet.getDate("creation_date");

				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				
				reply = new Reply(id, content, owner, date, topicId);
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return reply;
	}


	public static boolean insert(Reply reply){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "INSERT INTO replies(content, user_id, creation_date, topic_id) "
					+ "values(?, ?, ?, ?)";
			
			
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, reply.getContent());
			statement.setInt(index++, reply.getOwner().getId());
			statement.setDate(index++, new java.sql.Date(reply.getDate().getTime()));
			statement.setInt(index++, reply.getTopicId());
			System.out.println(statement);
			
			// izvrsavanje naredbe
			success = statement.executeUpdate() == 1;
			
//			
//			// izvrsavanje druge naredbe...
//			sql = "SELECT LAST_INSERT_ID()";
//			statement = connection.prepareStatement(sql);
//			resultSet = statement.executeQuery();
//			
//			// uzimanje user id-ja
//			resultSet.next();
//			int id = resultSet.getInt("id");
//			
//			user.setId(id);
			
			
			
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		}

		return success;
	}
	

	public static int getReplyNumber(int topicId){
		// Number of topics from selected forum
		int replyNumber = 0;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "select count(*) as numberOfReplies from replies where topic_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, topicId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				replyNumber = resultSet.getInt("numberOfReplies");
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replyNumber;
	}

	
	public static String getReplyContent(int replyId){
		String content = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT content FROM replies WHERE id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, replyId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			content = resultSet.getString("content");
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return content;
	}


	public static boolean update(Reply reply){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE replies SET content = ? WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, reply.getContent());
			statement.setInt(index++, reply.getId());
			System.out.println(statement);
			
			
			// izvrsavanje naredbe
			success = statement.executeUpdate() == 1;
			
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		}

		return success;
	}
	
	
	public static boolean deleteReply(int replyId){
		boolean success = false;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "DELETE FROM replies WHERE id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, replyId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			success = statement.executeUpdate() == 1;

		
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return success;
	}


	public static ArrayList<Reply> search(String replyContent, ArrayList<User> users){
		ArrayList<Reply> replies = new ArrayList<Reply>();
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies WHERE content LIKE ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, "%"+replyContent+"%");
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			/// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String content = resultSet.getString("content");
				int topicId = resultSet.getInt("topic_id");
				int userId = resultSet.getInt("user_id");
				Date date = resultSet.getDate("creation_date");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				reply = new Reply(id, content, owner, date, topicId);
				replies.add(reply);
				
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replies;
	}


	public static ArrayList<Reply> searchByUsername(int uId, ArrayList<User> users){
		ArrayList<Reply> replies = new ArrayList<Reply>();
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies WHERE user_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, uId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			/// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String content = resultSet.getString("content");
				int topicId = resultSet.getInt("topic_id");
				int userId = resultSet.getInt("user_id");
				Date date = resultSet.getDate("creation_date");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				reply = new Reply(id, content, owner, date, topicId);
				replies.add(reply);
				
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replies;
	}


	public static ArrayList<Reply> searchByDate(String sDate, String fDate, ArrayList<User> users){
		ArrayList<Reply> replies = new ArrayList<Reply>();
		Reply reply = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM replies WHERE creation_date > ? and creation_date < ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, sDate);
			statement.setString(index++, fDate);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			/// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String content = resultSet.getString("content");
				int topicId = resultSet.getInt("topic_id");
				int userId = resultSet.getInt("user_id");
				Date date = resultSet.getDate("creation_date");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				reply = new Reply(id, content, owner, date, topicId);
				replies.add(reply);
				
				

			}
						
			
			
		} catch (Exception ex){
			ex.printStackTrace();
			
		} finally {
			
			try{
				resultSet.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				statement.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			try{
				connection.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		return replies;
	}


	
}
