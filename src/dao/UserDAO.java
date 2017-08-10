package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.StatementEvent;

import obicne.Role;
import obicne.User;

public class UserDAO {

	public static ArrayList<Role> roles;
	
	
	public static ArrayList<User> get(){
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM users";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				Date date = resultSet.getDate("register_date");
				int roleId = resultSet.getInt("role_id");
				boolean banned = resultSet.getBoolean("banned");
				String avatarName = resultSet.getString("avatar_name");
				boolean active = resultSet.getBoolean("active");
				
				Role role = null;
				
				for (Role r : roles) {
					if(r.getId() == roleId){
						role = r;
					}
				}

				user = new User(id, username, password, name, surname, email, date, role, banned, active);
				user.setDate(date);
				user.setAvatarName(avatarName);
				users.add(user);

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
		
		return users;
	}

	
	public static User get(int id){
		User user = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM users WHERE id = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, id);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			String username = resultSet.getString("username");
			String password = resultSet.getString("password");
			String name = resultSet.getString("name");
			String surname = resultSet.getString("surname");
			String email = resultSet.getString("email");
			Date date = resultSet.getDate("register_date");
			int roleId = resultSet.getInt("role_id");
			boolean banned = resultSet.getBoolean("banned");
			int numberPerPage = resultSet.getInt("numberPerPage");
			String avatarName = resultSet.getString("avatar_name");
			boolean active = resultSet.getBoolean("active");
			
			Role role = null;
			
			for (Role r : roles) {
				if(r.getId() == roleId){
					role = r;
				}
			}

			user = new User(id, username, password, name, surname, email, date, role, banned, active);
			user.setDate(date);
			user.setAvatarName(avatarName);
			user.setNumberPerPage(numberPerPage);
			
			System.out.println(user);
			
			
			
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
		
		return user;
	}

	
	public static User get(String username, String password){
		
		User user = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND active = 1";
//			String sql = "SELECT A.*, ifnull(B.numberPerPage, 5) as 'numberPerPage' FROM "
//					+ "users A, user_options B WHERE "
//					+ "A.username = ? and A.password = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, username);
			statement.setString(index++, password);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			int id = resultSet.getInt("id");
			username = resultSet.getString("username");
			password = resultSet.getString("password");
			String name = resultSet.getString("name");
			String surname = resultSet.getString("surname");
			String email = resultSet.getString("email");
			Date date = resultSet.getDate("register_date");
			int roleId = resultSet.getInt("role_id");
			boolean banned = resultSet.getBoolean("banned");
			int numberPerPage = resultSet.getInt("numberPerPage");
			String avatarName = resultSet.getString("avatar_name");
			boolean active = resultSet.getBoolean("active");
			
			Role role = null;
			
			for (Role r : roles) {
				if(r.getId() == roleId){
					role = r;
				}
			}

			user = new User(id, username, password, name, surname, email, date, role, banned, active);
			user.setDate(date);
			user.setNumberPerPage(numberPerPage);
			user.setAvatarName(avatarName);
			
			System.out.println(user);
			
			
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
		
		
		return user; 	
	}
	

	public static int insert(User user){
		int userId = 0;
		ResultSet resultSet = null;
		
		if(!user.isValidEmail()) return userId;
		
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "INSERT INTO users(username, password, name, surname, email, register_date, role_id, banned, numberPerPage, active) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			String name = user.getName();
			if(name.equals("")) name = null;
			
			String surname = user.getSurname();
			if(surname.equals("")) surname = null;
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, user.getUsername());
			statement.setString(index++, user.getPassword());
			statement.setString(index++, name);
			statement.setString(index++, surname);
			statement.setString(index++, user.getEmail());
			statement.setDate(index++, new java.sql.Date(user.getDate().getTime()));
			statement.setInt(index++, user.getRole().getId());
			statement.setBoolean(index++, user.isBanned());
			statement.setInt(index++, user.getNumberPerPage());
			statement.setBoolean(index++, user.isActive());
			System.out.println(statement);
			
			// izvrsavanje naredbe
			if(!(statement.executeUpdate() == 1)) return userId;
			
			// izvrsavanje druge naredbe...
			sql = "SELECT LAST_INSERT_ID() as id";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			
			// uzimanje user id-ja
			resultSet.next();
			userId = resultSet.getInt("id");
			
			
			
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

		return userId;
	}
	
	
	public static boolean update(User user){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE users SET username = ?, password = ?, name = ?, surname = ?, email = ?,"
					+ " role_id = ?, banned = ?, numberPerPage = ?, avatar_name = ?"
					+ " WHERE id = ?";
			
			String name = user.getName();
			if("".equals(name)) name = null;
			
			String surname = user.getSurname();
			if("".equals(surname)) surname = null;
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, user.getUsername());
			statement.setString(index++, user.getPassword());
			statement.setString(index++, name);
			statement.setString(index++, surname);
			statement.setString(index++, user.getEmail());
			statement.setInt(index++, user.getRole().getId());
			statement.setBoolean(index++, user.isBanned());
			statement.setInt(index++, user.getNumberPerPage());
			statement.setString(index++, user.getAvatarName());
			statement.setInt(index++, user.getId());
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
	

	public static boolean updateBanMember(int id, boolean banned){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE users SET banned = ?"
					+ " WHERE id = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setBoolean(index++, banned);
			statement.setInt(index++, id);
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
	

	public static boolean updateActMember(int id, boolean active){
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE users SET active = ?"
					+ " WHERE id = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setBoolean(index++, active);
			statement.setInt(index++, id);
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
	
	
	public static boolean updateMemberInfo(int id, int roleId, String email, String username, String fname, String lname){
		boolean success = false;
		
		
		// provera da li je roleId validan
		int x = 0;
		for (Role role : roles) {
			if(role.getId() == roleId){
				x += 1;
				break;
			}
		}
		if(x == 0) return success;
		//.
		
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE users SET username = ?, name = ?, surname = ?, email = ?, role_id = ?"
					+ " WHERE id = ?";
			
			if(fname.equals("")) fname = null;
			if(lname.equals("")) lname = null;
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, username);
			statement.setString(index++, fname);
			statement.setString(index++, lname);
			statement.setString(index++, email);
			statement.setInt(index++, roleId);
			statement.setInt(index++, id);
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
	
	
	public static boolean deleteMember(int id){
		// prvo brise iz tabele za mail ako nije potvrdjen mail
		ActivateDAO.deleteKey(id);
		PassResetDAO.deleteKey(id);

		
		boolean success = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "DELETE FROM users WHERE id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, id);
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

	
	public static User searchByUsername(String username){
		User user = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM users WHERE username = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, username);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String email = resultSet.getString("email");
				Date date = resultSet.getDate("register_date");
				int roleId = resultSet.getInt("role_id");
				boolean banned = resultSet.getBoolean("banned");
				String avatarName = resultSet.getString("avatar_name");
				boolean active = resultSet.getBoolean("active");
				
				Role role = null;
				
				for (Role r : roles) {
					if(r.getId() == roleId){
						role = r;
					}
				}

				user = new User(id, username, password, name, surname, email, date, role, banned, active);
				user.setDate(date);
				user.setAvatarName(avatarName);

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
		
		return user;
	}
	

	public static User searchByEmail(String email){
		User user = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM users WHERE email = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, email);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String password = resultSet.getString("password");
				String name = resultSet.getString("name");
				String surname = resultSet.getString("surname");
				String username = resultSet.getString("username");
				Date date = resultSet.getDate("register_date");
				int roleId = resultSet.getInt("role_id");
				boolean banned = resultSet.getBoolean("banned");
				String avatarName = resultSet.getString("avatar_name");
				boolean active = resultSet.getBoolean("active");
				
				Role role = null;
				
				for (Role r : roles) {
					if(r.getId() == roleId){
						role = r;
					}
				}

				user = new User(id, username, password, name, surname, email, date, role, banned, active);
				user.setDate(date);
				user.setAvatarName(avatarName);

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
		
		return user;
	}

	
}
