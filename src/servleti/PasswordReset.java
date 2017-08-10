package servleti;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.MailDAO;
import dao.PassResetDAO;
import dao.UserDAO;
import obicne.Mail;
import obicne.User;

/**
 * Servlet implementation class PasswordReset
 */
public class PasswordReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static String host;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordReset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		
		String key = request.getParameter("key");
		
		if(key == null || key.isEmpty()) return;
		
		User user = UserDAO.get(PassResetDAO.verifyKey(key));
		
		if(user != null){
			request.setAttribute("user", user);
			request.getRequestDispatcher("passwordreset.jsp").forward(request, response);
			
		}else{
			return;
		}

		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String uri = request.getRequestURI();
//		String[] tmp = uri.split("/");
//		this.host = request.getServerName() + ":" + request.getServerPort() + "/" + tmp[1];
		
		boolean status = false;
		String message = "Something went wrong!";
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = new LinkedHashMap<>();
		
		
		String action = request.getParameter("action");
		
		if("reset".equals(action)){
			// sending reset mail..
			
			String username = request.getParameter("username");
			if(!username.isEmpty()){
//				User user = UserDAO.getByUsername(username);
				User user = UserDAO.searchByUsername(username);
				

				if(sendReset(user)){
					status = true;
					message = "Reset link sent to your email!";
				}
			
			}
			
		}else{
			// actual pass reset..
			String userId = request.getParameter("id");
			String pass1 = request.getParameter("password1");
			String pass2 = request.getParameter("password2");

			
			int id;
			try {
				if(userId.isEmpty() || pass1.isEmpty() || pass2.isEmpty()) throw new Exception();
				
				id = Integer.parseInt(userId);
				
				if(!pass1.equals(pass2)) throw new Exception();
				
			} catch (Exception e) {
				// TODO: handle exception
				return;
			}
			
			
			User user = UserDAO.get(id);
			if(user == null) return;
//			user.setNumberPerPage(5);
			
			
			MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance("SHA-512");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			byte[] hash = digest.digest(pass1.getBytes());
			String hashedPassword = DatatypeConverter.printHexBinary(hash);
			
			
			user.setPassword(hashedPassword);
			
			
			if(UserDAO.update(user)){
				PassResetDAO.deleteKey(user.getId());
				status = true;
				message = "Password changed successfully!";
			}
			
		}
	
		
		
		data.put("message", message);
		data.put("status", status);
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
		return;
	
	}
	
	
	public static boolean sendReset(User user){
		boolean status = false;
		
//		String username = request.getParameter("username");
//
//		User user = UserDAO.getByUsername(username);
		
		if(user == null) return status;
		
		
		// sending mail
		Mail mail = MailDAO.getMail();
		mail.setSubject("Password reset!");
		mail.setContent("<center>"
				+ "You've requested password reset, click link bellow and follow further instructions:</br>"
				+ "<p>http://" + Index.host + "/PasswordReset?key=" + genKey(user.getId()) + "</p>"
				+ "</center>");
		
		if(mail.sendMail(user.getEmail())) status = true;
		
		
		return status;
	}
	
	
	public static String genKey(int userId){
		String key;
		
		// generating key
		String timestamp = String.valueOf(new Date().getTime());
		System.out.println(timestamp);
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hash = digest.digest(timestamp.getBytes());
		
		key = DatatypeConverter.printHexBinary(hash).substring(2, 23);
		System.out.println(key);
		
		if(!PassResetDAO.insertKey(userId, key)){
			key = PassResetDAO.getKey(userId);
		}
		
		return key;
	}

}
