package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import obicne.Forum;
import obicne.ForumType;
import obicne.Topic;
import obicne.User;

public class ForumDAO {
	
	public static ArrayList<ForumType> forumTypes;


	public static ArrayList<Forum> get(ArrayList<User> users){
		ArrayList<Forum> forums = new ArrayList<Forum>();
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums";
//			String sql = 
//			"SELECT A.id, A.name, A.description, A.creation_date, A.locked, C.name, A.forum_id, "+
//			"B.id, B.username, B.password, B.name, B.surname, B.email, B.register_date, B.role_id, B.banned, "+
//			"C.id "+
//			"from forums A LEFT OUTER JOIN users B on A.user_id = B.id LEFT OUTER JOIN types C "+
//			"ON A.type_id = C.id";
//			

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(id, name, description, owner, creationDate, locked, ftype, parentForumId);
				forums.add(forum);

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
		
		return forums;
	}

	
	public static Forum get(int id, ArrayList<User> users){
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums where id = ?";

			
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
				
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(id, name, description, owner, creationDate, locked, ftype, parentForumId);

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
		
		
		
		return forum;
	}
	

	
	public static ArrayList<Forum> getSubForums(int id, ArrayList<User> users){
		ArrayList<Forum> subForums = new ArrayList<Forum>();
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums where forum_id = ?";

			
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
				int subId = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(subId, name, description, owner, creationDate, locked, ftype, parentForumId);
				subForums.add(forum);

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
		
		return subForums;
	}

	
	
	public static boolean insert(Forum forum){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql;
			if(forum.getParentForumId() == 0){
				sql = "insert into forums(name, description, user_id, creation_date, locked, type_id) "
						+ "values(?, ?, ?, ?, ?, ?)";
			}else{
				sql = "insert into forums(name, description, user_id, creation_date, locked, type_id, forum_id) "
						+ "values(?, ?, ?, ?, ?, ?, ?)";
			}
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, forum.getName());
			statement.setString(index++, forum.getDescription());
			statement.setInt(index++, forum.getOwner().getId());
			statement.setDate(index++, new java.sql.Date(forum.getCreationDate().getTime()));
			statement.setBoolean(index++, forum.isLocked());
			statement.setInt(index++, forum.getType().getId());
			if(forum.getParentForumId() != 0) statement.setInt(index++, forum.getParentForumId());
			
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
	


	public static boolean updateForumDetails(int forumId, String forumName, String forumDesc){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE forums SET name = ?, description = ?"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, forumName);
			statement.setString(index++, forumDesc);
			statement.setInt(index++, forumId);
			
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
	
	
	public static boolean updateForumLock(int forumId, boolean lockStatus){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE forums SET locked = ?"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setBoolean(index++, lockStatus);
			statement.setInt(index++, forumId);
			
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
	
	

	public static boolean deleteForum(int forumId){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "DELETE FROM forums"
					+ " WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, forumId);
			
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
	
	

	public static ArrayList<Forum> search(String forumName, ArrayList<User> users){
		ArrayList<Forum> forums = new ArrayList<Forum>();
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums WHERE name LIKE ?";

			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, "%"+ forumName +"%");
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				int subId = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(subId, name, description, owner, creationDate, locked, ftype, parentForumId);
				forums.add(forum);
				

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
		
		return forums;
				
	}


	public static ArrayList<Forum> searchByUsername(int uId, ArrayList<User> users){
		ArrayList<Forum> forums = new ArrayList<Forum>();
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums WHERE user_id = ?";

			
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
				int subId = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(subId, name, description, owner, creationDate, locked, ftype, parentForumId);
				forums.add(forum);
				

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
		
		return forums;
				
	}

	
	public static ArrayList<Forum> searchByDate(String sDate, String fDate, ArrayList<User> users){
		ArrayList<Forum> forums = new ArrayList<Forum>();
		Forum forum = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forums WHERE creation_date > ? and creation_date < ?";
//			String sql = "SELECT * FROM forums WHERE creation_date > '2016-12-1' and creation_date < '2017-3-1'";

			
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
				int subId = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				Date creationDate = resultSet.getDate("creation_date");
				boolean locked = resultSet.getBoolean("locked");
				int userId = resultSet.getInt("user_id");
				int typeId = resultSet.getInt("type_id");
				int parentForumId = resultSet.getInt("forum_id");
				
				User owner = null;
				for (User user : users) {
					if (user.getId() == userId) {
						owner = user;
						break;
					}
				}
				
				ForumType ftype = null;
				for (ForumType ft : forumTypes) {
					if (ft.getId() == typeId) {
						ftype = ft;
						break;
					}
				}
				
				forum = new Forum(subId, name, description, owner, creationDate, locked, ftype, parentForumId);
				forums.add(forum);
				

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
		
		return forums;
				
	}

	
}
	
	
	
	
	
	