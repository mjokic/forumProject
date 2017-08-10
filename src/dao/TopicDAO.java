package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import obicne.Forum;
import obicne.Topic;
import obicne.User;

public class TopicDAO {


	public static ArrayList<Topic> get(int forumId, ArrayList<User> users){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics where forum_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, forumId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(id, name, description, content, owner, creationDate, important, locked, forumId);
				topics.add(topic);
				

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
		
		return topics;
	}


	public static ArrayList<Topic> getTopicByUserId(int userId, ArrayList<User> users){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics where user_id = ?";

			
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
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int forumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(id, name, description, content, owner, creationDate, important, locked, forumId);
				topics.add(topic);
				

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
		
		return topics;
	}

	
	public static Topic getTopic(int topicId, ArrayList<User> users){
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics where id = ?";

			
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
				
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int forumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(topicId, name, description, content, owner, creationDate, important, locked, forumId);
				

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
		
		return topic;
	}

	
	public static boolean getTopicLock(int topicId){
		boolean locked = true;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT locked FROM topics where id = ?";

			
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
				locked = resultSet.getBoolean("locked");
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
		
		return locked;
	}
	

	public static int getTopicNumber(int forumId){
		// Number of topics from selected forum
		int topicNumber = 0;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "select count(*) as numberOfTopics from topics where forum_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, forumId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				topicNumber = resultSet.getInt("numberOfTopics");
				

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
		
		return topicNumber;
	}


	public static boolean insert(Topic topic){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql;
			sql = "insert into topics(name, description, content, user_id, creation_date, "
					+ "important, locked, forum_id) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, topic.getName());
			statement.setString(index++, topic.getDescription());
			statement.setString(index++, topic.getContent());
			statement.setInt(index++, topic.getOwner().getId());
			statement.setDate(index++, new java.sql.Date(topic.getDate().getTime()));
			statement.setBoolean(index++, topic.isImportant());
			statement.setBoolean(index++, topic.isLocked());
			statement.setInt(index++, topic.getForumId());
			
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
	

	
	public static boolean updateTopicLock(int topicId, boolean locked){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE topics SET locked = ?"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setBoolean(index++, locked);
			statement.setInt(index++, topicId);
			
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
	
	
	public static boolean updateTopicBookmark(int topicId, boolean bookmarked){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE topics SET important = ?"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setBoolean(index++, bookmarked);
			statement.setInt(index++, topicId);
			
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
	
	
	public static boolean updateTopicDetails(int topicId, String title, String desc, String content){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE topics SET name = ?, description = ?, content = ?"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, title);
			statement.setString(index++, desc);
			statement.setString(index++, content);
			statement.setInt(index++, topicId);
			
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
	
	
	public static boolean deleteTopic(int topicId){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "DELETE FROM topics"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, topicId);
			
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
	
	
	public static ArrayList<Topic> search(String topicContent, String topicTitle, ArrayList<User> users){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics WHERE content LIKE ? OR name like ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, "%"+topicContent+"%");
			statement.setString(index++, "%"+topicTitle+"%");
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int forumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(id, name, description, content, owner, creationDate, important, locked, forumId);
				topics.add(topic);
				

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
		
		return topics;
	}

	
	public static ArrayList<Topic> searchByUsername(int uId, ArrayList<User> users){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics WHERE user_id = ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, uId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int forumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(id, name, description, content, owner, creationDate, important, locked, forumId);
				topics.add(topic);
				

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
		
		return topics;
	}


	public static ArrayList<Topic> searchByDate(String sDate, String fDate, ArrayList<User> users){
		ArrayList<Topic> topics = new ArrayList<Topic>();
		Topic topic = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM topics WHERE creation_date > ? and creation_date < ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, sDate);
			statement.setString(index++, fDate);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String content = resultSet.getString("content");
				Date creationDate = resultSet.getDate("creation_date");
				boolean important = resultSet.getBoolean("important");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int forumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				topic = new Topic(id, name, description, content, owner, creationDate, important, locked, forumId);
				topics.add(topic);
				

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
		
		return topics;
	}

	
}
