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
import obicne.Forum;
import obicne.ForumType;
import obicne.Reply;
import obicne.User;

/**
 * Servlet implementation class Topic
 */
public class Topic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Topic() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		String id_tmp = request.getParameter("id");
		String highlight = request.getParameter("highlight");
		int id;
		obicne.Topic topic = null;
		Forum forum = null;
		ArrayList<Reply> replies = new ArrayList<Reply>();
		
		try {
			
			id = Integer.parseInt(id_tmp);
			topic = TopicDAO.getTopic(id, users);
			if(topic == null) throw new Exception();
			
			forum = ForumDAO.get(topic.getForumId(), users);
			
			replies = ReplyDAO.get(topic.getId(), users);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			String message = "Topic ID mora biti validan broj!";
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
			
		}
		
		//adding highlist feature
		if(highlight != null){
			
			if(topic.getContent().contains(highlight)){
				topic.setContent(topic.getContent().replace(highlight, 
						"<span class=\"highlight\">"+highlight+"</span>"));
			}
			
			for (Reply reply : replies) {
				if(reply.getContent().contains(highlight)){
					reply.setContent(reply.getContent().replace(highlight, 
							"<span class=\"highlight\">"+highlight+"</span>"));
					
				}
			}
			
		}
		

		request.setAttribute("forum", forum);
		request.setAttribute("topic", topic);
		request.setAttribute("replies", replies);
		request.getRequestDispatcher("topic.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		User user = (User) session.getAttribute("user");
		
		// baci neki error
		if(user == null || user.isBanned()) return;
		
		String message;
		String status;
		
		Map<String, Object> data = new LinkedHashMap<>();
		if("add".equals(action)){
			boolean addTopicStatus = addTopic(request, user, users);
			
			
			if(addTopicStatus == true){
				message = "New topic created successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("edit".equals(action)){
			boolean editTopicStatus = editTopic(request, user, users);
			
			
			if(editTopicStatus == true){
				message = "Topic edited successfully!";
				status = "success";

			}else{
				message = "Something broke! Try again!";
				status = "fail";
			}
			
		}else if("lock".equals(action)){
			boolean lockTopicStatus = lockTopic(request, user, users);
			
			
			if(lockTopicStatus == true){
				message = "Topic locked successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
			
		}else if("unlock".equals(action)){
			boolean unlockTopicStatus = unlockTopic(request, user);
			
			
			if(unlockTopicStatus == true){
				message = "Topic unlocked successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
		
		}else if("del".equals(action)){
			boolean delTopicStatus = delTopic(request, user);
			
			
			if(delTopicStatus == true){
				message = "Topic deleted successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
		
			
		}else if("bookmark".equals(action)){
			boolean bookTopicStatus = bookTopic(request, user);
			
			
			if(bookTopicStatus == true){
				message = "Topic bookmarked successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("unbookmark".equals(action)){
			boolean unbookTopicStatus = unbookTopic(request, user);
			
			
			if(unbookTopicStatus == true){
				message = "Topic unbookmarked successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		
		}else{
			message = "Server is confused! Try again!";
			status = "fail";
		}
		
		data.put("message", message);
		data.put("status", status);
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);
		response.getWriter().write(jsonData);
		
	}
	

	
	private boolean addTopic(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		String name = request.getParameter("name");
		String desc = request.getParameter("description");
		String content = request.getParameter("content");
		String userId_tmp = request.getParameter("userId");
		String forumId_tmp = request.getParameter("forumId");
		
		int userId;
		int forumId;
		User owner = null;
		try{
			userId = Integer.parseInt(userId_tmp);
			forumId = Integer.parseInt(forumId_tmp);
			
			if(name.isEmpty() || content.isEmpty()){
				throw new Exception();
			}
			
			
			for (User u : users) {
				if(u.getId() == userId && user.getId() == userId){
					owner = u;
					break;
				}
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			
			return status;
			
		}
		
		obicne.Topic topic = new obicne.Topic(name, desc, content, owner, false, false, forumId);
		if(TopicDAO.insert(topic) == true) status = true;
		
		
		return status;

	}

	
	private boolean editTopic(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		
		String tmp_id = request.getParameter("id");
		String title = request.getParameter("title");
		String desc = request.getParameter("description");
		String content = request.getParameter("content");
		
		if(title.isEmpty() || content.isEmpty() || content.length() < 15) return status;
		
		int id;
		try {
			
			id = Integer.parseInt(tmp_id);
			
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		obicne.Topic topic = TopicDAO.getTopic(id, users);
		
		if(topic.getOwner().getId() == user.getId() || 
				(user.getRole().getId() == 1 || user.getRole().getId() == 2)){
		
			if(TopicDAO.updateTopicDetails(id, title, desc, content)) status = true;
			
		}
		
		
		
		
		return status;

	}

	
	private boolean lockTopic(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		obicne.Topic topic = TopicDAO.getTopic(id, users);
		
		if(topic.getOwner().getId() == user.getId() || 
				(user.getRole().getId() == 1 || user.getRole().getId() == 2)){
		
			if(TopicDAO.updateTopicLock(id, true)) status = true;
		
		}
		
		return status;

	}

	
	private boolean unlockTopic(HttpServletRequest request, User user){
		boolean status = false;
		
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		

		if(user.getRole().getId() == 1 || user.getRole().getId() == 2){
		
			if(TopicDAO.updateTopicLock(id, false)) status = true;
		
		}
		
		
		return status;

	}


	private boolean delTopic(HttpServletRequest request, User user){
		boolean status = false;
		
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(user.getRole().getId() == 1 || user.getRole().getId() == 2){
		
			if(TopicDAO.deleteTopic(id)) status = true;
		}
		
		
		return status;

	}

	
	private boolean bookTopic(HttpServletRequest request, User user){
		boolean status = false;

		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(user.getRole().getId() == 1 || user.getRole().getId() == 2){
		
			if(TopicDAO.updateTopicBookmark(id, true)) status = true;
		}
		
		
		return status;

	}

	
	private boolean unbookTopic(HttpServletRequest request, User user){
		boolean status = false;
	
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(user.getRole().getId() == 1 || user.getRole().getId() == 2){
		
			if(TopicDAO.updateTopicBookmark(id, false)) status = true;
		}
		
		
		return status;

	}

	
}
