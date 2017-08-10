package servleti;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.ForumDAO;
import dao.ReplyDAO;
import dao.TopicDAO;
import dao.UserDAO;
import obicne.Topic;
import obicne.User;

/**
 * Servlet implementation class GetJson
 */
public class GetJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		
		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		
		String action = request.getParameter("action");
		
		
		Map<String, Object> data = new LinkedHashMap<>();
		
		
		if("getForumDetails".equals(action)){
			if(user == null){
				return;
			}
			
			String tmp_id = request.getParameter("id");
			int id;
			try {
				id = Integer.parseInt(tmp_id);

				obicne.Forum forum = ForumDAO.get(id, users);
				
				data.put("ForumName", forum.getName());
				data.put("ForumDesc", forum.getDescription());
				data.put("status", "success");
				
			} catch (Exception e) {
				// TODO: handle exception
				
				data.put("status", "fail");
				
			}
			
		}else if("getMemberDetails".equals(action)){
			if(user == null){
				return;
			}
			
			String tmp_id = request.getParameter("id");
			int id;
			try {
				id = Integer.parseInt(tmp_id);

				User member = UserDAO.get(id);

				data.put("roleId", member.getRole().getId());
				data.put("email", member.getEmail());
				data.put("username", member.getUsername());
				data.put("fname", member.getName());
				data.put("lname", member.getSurname());
				data.put("banned", member.isBanned());
				data.put("status", "success");
				
			} catch (Exception e) {
				// TODO: handle exception
				
				data.put("status", "fail");
				
			}
			
		}else if("getTopicDetails".equals(action)){
			if(user == null){
				return;
			}
			
			String tmp_id = request.getParameter("id");
			int id;
			try {
				id = Integer.parseInt(tmp_id);

				Topic topic = TopicDAO.getTopic(id, users);
				
				data.put("title", topic.getName());
				data.put("desc", topic.getDescription());
				data.put("content", topic.getContent());
				data.put("status", "success");
				
			} catch (Exception e) {
				// TODO: handle exception
				
				data.put("status", "fail");
				
			}
			
		}else if("getReplyDetails".equals(action)){
			if(user == null){
				return;
			}
			
			String tmp_id = request.getParameter("id");
			int id;
			try {
				id = Integer.parseInt(tmp_id);

				String replyContent = ReplyDAO.getReplyContent(id);
				
				data.put("content", replyContent);
				data.put("status", "success");
				
			} catch (Exception e) {
				// TODO: handle exception
				
				data.put("status", "fail");
				
			}
			
		}else if("checkUsername".equals(action)){
			String username = request.getParameter("username");
			
			User user1 = UserDAO.searchByUsername(username);
			
			if(user1 == null){
				data.put("status", "success");
			}else{
				data.put("message", "Username already exists!");
				data.put("status", "fail");
			}
			
			
		}else if("checkEmail".equals(action)){
			String email = request.getParameter("email");
			
			User user1 = UserDAO.searchByEmail(email);
			
			if(user1 == null){
				data.put("status", "success");
			}else{
				data.put("message", "Email already exists!");
				data.put("status", "fail");
			}
		
			
		}else{
			data.put("status", "fail");
		}
		
		
		
		
		
		
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
		return;
	}

}
