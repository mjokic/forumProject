package obicne;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class Mail {

	private String host;
	private String port;
	private String mailUsername;
	private String mailPassword;
	
	private String subject;
	private String content;
	
	private String activationKey;
	
	public Mail(String host, String port, String user, String pass){
		this.host = host;
		this.port = port;
		this.mailUsername = user;
		this.mailPassword = pass;
	}
	
	
	
	public boolean sendMail(String to){
		boolean status = false;
		
		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", this.host);
		props.setProperty("mail.smtp.port", this.port);
		props.setProperty("mail.imap.ssl.enable", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");

		
		Session session = Session.getDefaultInstance(props);
		
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(this.mailUsername);
			message.addRecipients(Message.RecipientType.TO, to);
			message.setSubject(this.subject);
			
			String content = this.content;
			
			if(this.content.contains("%confirm_key%") && (!this.activationKey.isEmpty() && this.activationKey != null)){
				System.out.println(this.activationKey);
				content = this.content.replace("%confirm_key%", this.activationKey);
			}
			
			message.setContent(content, "text/html");
			
			
			
			if(this.mailUsername.isEmpty() || this.mailPassword.isEmpty()){
				Transport.send(message);
				
			}else{
				Transport.send(message, this.mailUsername, this.mailPassword);
			}
			
			
			status = true;
			
		}catch (Exception ex){
			ex.printStackTrace();
			status = false;
		}
		
		
		return status;
		
	}



	
	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getMailUsername() {
		return mailUsername;
	}



	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}



	public String getMailPassword() {
		return mailPassword;
	}



	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}



	public String getSubject() {
		return subject;
	}
	



	public void setSubject(String subject) {
		this.subject = subject;
	}
	



	public String getContent() {
		return content;
	}
	



	public void setContent(String content) {
		this.content = content;
	}



	public String getActivationKey() {
		return activationKey;
	}



	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}
	
	
	
	
	
}
