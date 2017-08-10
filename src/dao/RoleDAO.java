package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import obicne.Role;

public class RoleDAO {
	

	public static ArrayList<Role> get(){
		ArrayList<Role> roles = new ArrayList<Role>();
		Role role = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM role";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			while(resultSet.next())
			{
				
				int id = resultSet.getInt("id");
				String name = resultSet.getString("role_name");
				String description = resultSet.getString("role_desc");
				
				role = new Role(id, name, description);
				roles.add(role);

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
		
		return roles;
	}


}
