package dao;

import java.sql.Connection;

import org.apache.commons.dbcp2.BasicDataSource;

public class ConnectionManager {

	private static final String DATABASE = "localhost:3306/db_forum";
	private static final String USERNAME = "root";
	private static final String PASSWROD = "toor";
	
	private static BasicDataSource CONNECTION_POOL;
	
	public static void init(){
		if (CONNECTION_POOL != null) destroy();
		
		System.out.println("Initializing connection pool...");
		try {
			CONNECTION_POOL = new BasicDataSource();
			CONNECTION_POOL.setDriverClassName("com.mysql.jdbc.Driver");
			CONNECTION_POOL.setUrl("jdbc:mysql://" + DATABASE);
			CONNECTION_POOL.setUsername(USERNAME);
			CONNECTION_POOL.setPassword(PASSWROD);
			System.out.println("Done!");
		
		} catch (Exception ex){
			System.out.println("Failed!");

//			ex.printStackTrace();
			
		}
	}
	
	
	public static void destroy(){
		System.out.println("Closing connection pool...");
		try {
			CONNECTION_POOL.close();
			System.out.println("Done!");
		
		} catch (Exception ex){
			System.out.println("Failed!");

//			ex.printStackTrace();
			
		}
	}
	
	
	public static Connection getConnection() throws Exception {
		if (CONNECTION_POOL == null) init();
		return CONNECTION_POOL.getConnection();
	}
	
	
}
