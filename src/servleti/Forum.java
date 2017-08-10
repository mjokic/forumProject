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
import dao.TopicDAO;
import obicne.ForumType;
import obicne.Topic;
import obicne.User;


/**
 * Servlet implementation class Forum
 */
public class Forum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Forum() {
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
		ArrayList<obicne.Forum> subForums = null;
		ArrayList<Topic> topics = null;
		
		String id_tmp = request.getParameter("id");
		int id;
		obicne.Forum forum = null;
		
		
		try {
			
			id = Integer.parseInt(id_tmp);
			forum = ForumDAO.get(id, users);
			if(forum == null) throw new Exception();
			
			subForums = ForumDAO.getSubForums(forum.getId(), users);
			topics = TopicDAO.get(forum.getId(), users);
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			String message = "Forum ID mora biti validan broj!";
			
			request.setAttribute("message", message);
			request.getRequestDispatcher("error.jsp").forward(request, response);
			return;
			
		}
	
		request.setAttribute("topics", topics);
		request.setAttribute("subForums", subForums);
		request.setAttribute("forum", forum);
		request.getRequestDispatcher("forum.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		String method = request.getParameter("action");

		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		// baci neki error
		if(user == null || user.isBanned()) return;
		
		
		String message;;
		String status;
		
		Map<String, Object> data = new LinkedHashMap<>();
		if("add".equals(method)){
			boolean addForumStatus = addForum(request, user, users);
			
			
			if(addForumStatus == true){
				message = "New forum created successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			

		}else if("edit".equals(method)){
			boolean editForumStatus = editForum(request, user, users);
			
			
			if(editForumStatus == true){
				message = "Forum edited successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		
		}else if("del".equals(method)){
			boolean delForumStatus = deleteForum(request, user);
			
			
			if(delForumStatus == true){
				message = "Forum deleted successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
		}else if("lock".equals(method)){
			boolean lockForumStatus = lockForum(request, user, users);
			
			
			if(lockForumStatus == true){
				message = "Forum locked successfully!";
				status = "success";

			}else{
				message = "Something Broke! Try again!";
				status = "fail";
			}
			
			
		}else if("unlock".equals(method)){
			boolean unlockForumStatus = unlockForum(request, user);
			
			
			if(unlockForumStatus == true){
				message = "Forum unlocked successfully!";
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

	
	
	private boolean addForum(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		if(user == null || user.getRole().getId() != 1) return status;

		String name = request.getParameter("name");
		String desc = request.getParameter("description");
		String userId_tmp = request.getParameter("userId");
		String typeId_tmp = request.getParameter("typeId");
		String parentForumId_tmp = request.getParameter("parentId");
		
		if(name.isEmpty()) return status;
		
		int userId;
		int typeId;
		int parentForumId;
		try {
			userId = Integer.parseInt(userId_tmp);
			typeId = Integer.parseInt(typeId_tmp);
			parentForumId = Integer.parseInt(parentForumId_tmp);
			
		} catch (Exception e) {
			return status;

		}
		
		
		User owner = null;
		for (User u : users) {
			if(u.getId() == userId && user.getId() == userId){
				owner = u;
				break;
			}
			
		}
		
		ForumType type = null;
		for (ForumType ft : ForumDAO.forumTypes) {
			if(ft.getId() == typeId){
				type = ft;
				break;
			}
		}
		
		obicne.Forum forum = new obicne.Forum(name, desc, owner, type, parentForumId);
		if(ForumDAO.insert(forum) == true) status = true;
		
		
		return status;
	}
	
	private boolean editForum(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;

		if(user == null || user.getRole().getId() != 1) return status;

		
		String tmp_id = request.getParameter("id");
		String name = request.getParameter("name");
		String desc = request.getParameter("description");
		
		if(name.isEmpty()) return status;
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(ForumDAO.updateForumDetails(id, name, desc)) status = true;
		
		
		return status;
	}
	
	private boolean deleteForum(HttpServletRequest request, User user){
		boolean status = false;

		if(user == null || user.getRole().getId() != 1) return status;
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(ForumDAO.deleteForum(id)) status = true;
		
		
		return status;
	}
	
	private boolean lockForum(HttpServletRequest request, User user, ArrayList<User> users){
		boolean status = false;
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		

		
		if(lockAllFankshn(id, users)) status = true;
		
		return status;
	}
	
	private boolean unlockForum(HttpServletRequest request, User user){
		boolean status = false;
		
		if(user == null || user.getRole().getId() != 1){
			return status;
		}
		
		
		String tmp_id = request.getParameter("id");
		
		int id;
		try {
			id = Integer.parseInt(tmp_id);
		} catch (Exception e) {
			// TODO: handle exception
			return status;
		}
		
		
		if(ForumDAO.updateForumLock(id, false)) status = true;
		
		
		return status;
	}
	
	
	private boolean lockAllFankshn(int forumId, ArrayList<User> users){
		boolean status = false;
		
		if(!ForumDAO.updateForumLock(forumId, true)) return status;
		
		// uzima sve topics iz foruma
		ArrayList<Topic> topics = TopicDAO.get(forumId, users);
		
		// zakljucava se svaki topic
		for (Topic t : topics) {
			if(!TopicDAO.updateTopicLock(t.getId(), true)) return status;
			
		}

		
		// uzima sve subforume iz foruma
		ArrayList<obicne.Forum> subForums = ForumDAO.getSubForums(forumId, users);
		
		// za svaki forum ponovi ovaj fankshn
		for (obicne.Forum f : subForums) {
			if(!lockAllFankshn(f.getId(), users)) return status;

		}
		
		status = true;
		
		return status;
	}

}
