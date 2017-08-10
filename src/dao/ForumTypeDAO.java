package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import obicne.ForumType;

public class ForumTypeDAO {

	
	public static ArrayList<ForumType> get(){
		ArrayList<ForumType> forumTypes = new ArrayList<ForumType>();
		ForumType forumType = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM forum_types";
			
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
				

				forumType = new ForumType(id, name, description);
				forumTypes.add(forumType);

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
		
		return forumTypes;
	}

	
}
