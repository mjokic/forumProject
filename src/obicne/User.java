package obicne;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private Date date;  // use SimpleDateFormat to display date
	private Role role;
	private boolean banned;
	private int numberPerPage;
	private String avatarName;
	private boolean active;
	
	

	public User(String username, String password, String name, String surname, String email, Role role,
			boolean banned) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.role = role;
		this.date = new Date();
		this.banned = banned;
		this.active = false;
	}



	public User(int id, String username, String password, String name, String surname, String email,
			Date date,Role role, boolean banned, boolean active) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.date = date;
		this.role = role;
		this.banned = banned;
		this.active = active;
		
	}

	
	public boolean isValidEmail(){
		boolean valid = false;
		
		// check if this mail is valid...
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		Pattern pattern = Pattern.compile(emailPattern);
		
		valid = pattern.matcher(this.email).matches();
		
		
		return valid;
	}
	
	
	public String genActivationKey(){
		String key;
		
		// timestamp
		String timestamp = String.valueOf(new Date().getTime());
		System.out.println(timestamp);
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = digest.digest(timestamp.getBytes());
		
		key = DatatypeConverter.printHexBinary(hash).substring(4, 28);
		System.out.println(key);
		
		return key;
	}
	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	
	public int getNumberPerPage() {
		return numberPerPage;
	}



	public void setNumberPerPage(int numberPerPage) {
		this.numberPerPage = numberPerPage;
	}

	


	public String getAvatarName() {
		return avatarName;
	}



	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}



	public boolean isBanned() {
		return banned;
	}


	public void setBanned(boolean banned) {
		this.banned = banned;
	}
	
	

	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", date=" + date + ", role=" + role + ", banned=" + banned + "]";
	}
	
	
	
	

}
