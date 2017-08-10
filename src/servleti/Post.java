package servleti;

import java.io.IOException;
import java.security.MessageDigest;
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
import obicne.Reply;
import obicne.User;

/**
 * Servlet implementation class Post
 */
public class Post extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Post() {
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
		
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		User user = (User) session.getAttribute("user");
		
		
		Map<String, Object> data = new LinkedHashMap<>();
		String action = request.getParameter("action");

		
		// baci neki error
		if(user == null || user.isBanned()) action = "sklj";

		
		if("add".equals(action)){
			// uradi dodavanje replija ovde...
			if(addReply(request, user)) {
				data.put("message", "Successfully posted!");
				data.put("status", "success");	
			}else{
				data.put("message", "Something broke!");
				data.put("status", "fail");
			}
			
			
		}else if("edit".equals(action)){
			// uradi edit replija ovde...
			if(editReply(request, user, users)){
				data.put("message", "Reply successfully edited!");
				data.put("status", "success");	
			}else{
				data.put("message", "Something broke!");
				data.put("status", "fail");
			}
			
			
		}else if("del".equals(action)){
			// uradi brisanje replija ovde...
			if(delReply(request, user, users)){
				data.put("message", "Reply successfully deleted!");
				data.put("status", "success");	
			}else{
				data.put("message", "Something broke!");
				data.put("status", "fail");
			}
			
			
		}else{
			data.put("message", "Server is confused!");
			data.put("status", "fail");
			
		}
		

		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
	}

	
	
	private boolean addReply(HttpServletRequest request, User user){
		boolean status = false;
		
		String content = request.getParameter("content");
		String topicId_tmp = request.getParameter("topicId");
		
		int topicId;
		
		try {
			
			topicId = Integer.parseInt(topicId_tmp);
			
			
			if(content.equals("") || content.length() <= 15){
				throw new Exception("Post message has to be at least 15 chars!");
			}
			
			
			
		} catch (Exception e) {
			return status;
			
		}
		
		
		if(!TopicDAO.getTopicLock(topicId)){

			Reply reply = new Reply(content, user, topicId);
			if(ReplyDAO.insert(reply)) status = true;
				
		}
		
		
		return status;
	}
	
	
	private boolean editReply(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		String tmp_replyId = request.getParameter("id");
		String content = request.getParameter("content");
		
		
		int id;
		try {
			id = Integer.parseInt(tmp_replyId);
			
			if(content.length() < 15) throw new Exception();
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		Reply reply = ReplyDAO.getReplyById(id, users);
		reply.setContent(content);
		
		if((user.getRole().getId() == 1 || user.getRole().getId() == 2)
				|| user.getId() == reply.getOwner().getId()){
			
			if(ReplyDAO.update(reply)) status = true;
			
		}
			
		
		
		return status;
	}
	
	
	private boolean delReply(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		String tmp_replyId = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_replyId);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		Reply reply = ReplyDAO.getReplyById(id, users);
		
		if((user.getRole().getId() == 1 || user.getRole().getId() == 2)
				|| user.getId() == reply.getOwner().getId()){
			
			
			if(ReplyDAO.deleteReply(id)) status = true;
			
		}
			
		
		return status;
	}
	
	
}
