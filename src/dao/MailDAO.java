package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import obicne.Mail;
import obicne.Role;
import obicne.User;

public class MailDAO {

	
	public static Mail getMail(){
		Mail mail = null;
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM mail_options";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			String host = resultSet.getString("host");
			String port = resultSet.getString("port");
			String username = resultSet.getString("username");
			String password = resultSet.getString("password");
			
			mail = new Mail(host, port, username, password);
			
			mail = getConfMessageDetails(mail);
			
			
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
		
		return mail;
	}

	
	public static boolean updateMail(Mail mail){
		boolean status = false;
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE mail_options SET host = ?, port = ?, username = ?, password = ?";
			
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, mail.getHost());
			statement.setString(index++, mail.getPort());
			statement.setString(index++, mail.getMailUsername());
			statement.setString(index++, mail.getMailPassword());
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
	
	
	
	public static Mail getConfMessageDetails(Mail mail){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "SELECT * FROM confirm_mail WHERE id = 1";
			
			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			System.out.println(statement);
			
			// izvrsavanje naredbe
			resultSet = statement.executeQuery();
			
			// prihvatanje rezultata
			resultSet.next();
			
			String subject = resultSet.getString("subject");
			String content = resultSet.getString("content");
			
			mail.setSubject(subject);
			mail.setContent(content);
			
			
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
		
		return mail;
	}

	
	public static boolean updateConfMessageDetails(String subject, String content){
		boolean status = false;
		
		
		PreparedStatement statement = null;
		Connection connection = null;
		
		try{
			// dobaljanje konekcije
			connection = ConnectionManager.getConnection();
			
			// kreirajne upita
			String sql = "UPDATE confirm_mail SET subject = ?, content = ? WHERE id = 1";

			// pripremanje naredbe
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, subject);
			statement.setString(index++, content);
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
