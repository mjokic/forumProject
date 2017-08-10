package servleti;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import dao.ActivateDAO;
import dao.MailDAO;
import dao.UserDAO;
import obicne.Mail;
import obicne.Role;
import obicne.User;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		
		if(username.equals("") || email.equals("") || !password.equals(password2) || password.equals("") 
				|| password.length() < 8){
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", "Please check your inputs!");
			data.put("status", "fail");
			
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
			response.getWriter().write(jsonData);
			return;
		}
		
		// setting up Role object
		Role role = null;
		for (Role r : UserDAO.roles) {
			if(r.getId() == 3){
				role = r;
				break;
			}
		}
		
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		byte[] hash = digest.digest(password.getBytes());
		String hashedPassword = DatatypeConverter.printHexBinary(hash);
		
		
		// add user to db...
		User user = new User(username, hashedPassword, firstName, lastName, email, role, false);
		user.setNumberPerPage(5);
		int userId = UserDAO.insert(user);
		boolean status = false;
		
		System.out.println(userId);
		
		// sending mail
		if(userId != 0){
			
			// generise aktivacioni kljuc
			String activation_key = user.genActivationKey();
			if(ActivateDAO.insertKey(userId, activation_key)){
				// ako je sve ok salje mail
				// dodaj message i kljuc da se posalje...
				Mail mail = MailDAO.getMail();
				mail.setActivationKey(activation_key);
//				mail.setSubject("Please confirm your email");
//				mail.setContent("Click link bellow to confirm your email address " + 
//						"http://localhost:8080/Forum/Activate?key=" + activation_key);
				status = mail.sendMail(user.getEmail());
			}
			
		}
		
		if(status){

			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", "Success! Check your email!");
			data.put("status", "success");
			
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
			response.getWriter().write(jsonData);
			return;
			
		}else {
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", "Registration Failed!");
			data.put("status", "fail");
			
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
			response.getWriter().write(jsonData);
			return;
		}
		
		
		
	}

}
