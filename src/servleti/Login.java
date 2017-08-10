package servleti;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UserDAO;
import obicne.User;


/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Nothing special here");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		
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
		System.out.println(hashedPassword);
		
//		User user = UserDAO.get(username, password);
		User user = UserDAO.get(username, hashedPassword);
		
		
		if (user == null) {
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("userId", 0);
			data.put("message", "Login Failed!");
			data.put("status", "fail");
			
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);
			response.getWriter().write(jsonData);
			return;
			
		}
		
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		// ako sessija nije dodana, dodaj
		Map<String, HttpSession> sessions = SessionListener.getSessions();
		if(!sessions.containsKey(session.getId())){
			SessionListener.addSession(session);
		}
		
		
		// ubacuje sve users u context, jer mi treba
		// kad se dodaje novi forum/topic/reply
		// pa da zna ko je owner
		ServletContext context = getServletContext();
		context.setAttribute("users", UserDAO.get());
		
		
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("userId", user.getId());
		data.put("message", "Uspesna prijava!!!");
		data.put("status", "success");
		
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
		return;
		
		
	}

}
