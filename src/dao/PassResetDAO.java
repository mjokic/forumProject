package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PassResetDAO {

	
	public static int verifyKey(String key){
		int userId = 0;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT user_id FROM pass_reset WHERE conf_key = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, key);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			userId = resultSet.getInt("user_id");
			
			
			
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
		
		return userId;
	}
	

	public static String getKey(int id){
		String key = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT conf_key FROM pass_reset WHERE user_id = ?";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, id);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			key = resultSet.getString("conf_key");
			
			
			
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
		
		return key;
	}
	
	
	public static boolean insertKey(int userId, String key){
		boolean status = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "INSERT INTO pass_reset(user_id, conf_key) VALUES(?, ?)";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, userId);
			statement.setString(index++, key);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			status = statement.executeUpdate() == 1;
			
			
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
		
		return status;
	}
	
	
	public static boolean deleteKey(int userId){
		boolean status = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "DELETE FROM pass_reset WHERE user_id = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setInt(index++, userId);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			status = statement.executeUpdate() == 1;

			
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

		
		
		return status;
	}
	
	
}
