package servleti;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ForumDAO;
import dao.ReplyDAO;
import dao.TopicDAO;
import dao.UserDAO;
import obicne.Forum;
import obicne.Reply;
import obicne.Topic;
import obicne.User;

/**
 * Servlet implementation class Search
 */
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("./Index");
		return;
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		
		
		String searchTerm = request.getParameter("keyword");
		String forumName = request.getParameter("forum");
		String topicTitle = request.getParameter("title");
		String content = request.getParameter("content");
		String user = request.getParameter("user");
		String date = request.getParameter("date");
		
		
		if(searchTerm.isEmpty() && date == null) return;
		
		
		ArrayList<Forum> forumsTmp = new ArrayList<Forum>();
		ArrayList<Topic> topicsTmp = new ArrayList<Topic>();
		ArrayList<Reply> repliesTmp = new ArrayList<Reply>();
		ArrayList<Forum> forums = new ArrayList<Forum>();
		ArrayList<Topic> topics = new ArrayList<Topic>();;
		ArrayList<Reply> replies = new ArrayList<Reply>();
		
		User srcUser = null;


		if("on".equals(forumName)){
			forumsTmp = ForumDAO.search(searchTerm, users);
		}
		
		if("on".equals(topicTitle)){
			topicsTmp = TopicDAO.search(searchTerm, searchTerm, users);
		}
		
		if("on".equals(content)){
			repliesTmp = ReplyDAO.search(searchTerm, users);
		}
		
		if("on".equals(user)){
			srcUser = UserDAO.searchByUsername(searchTerm);
			
			if(srcUser != null){
//				forumsTmp = ForumDAO.searchByUsername(srcUser.getId(), users);
				topicsTmp = TopicDAO.searchByUsername(srcUser.getId(), users);
				repliesTmp = ReplyDAO.searchByUsername(srcUser.getId(), users);
				
			}
		}

		if("on".equals(date)){
			String date1 = request.getParameter("date1");
			String date2 = request.getParameter("date2");
			
			forumsTmp = ForumDAO.searchByDate(date1, date2, users);
			topicsTmp = TopicDAO.searchByDate(date1, date2, users);
			repliesTmp = ReplyDAO.searchByDate(date1, date2, users);
			
		}
		
		
		
		
		if(loggedInUser == null)
		{
			for (Forum f : forumsTmp) {
				if(f.getType().getId() == 1) forums.add(f);
			}
		
		}else if(loggedInUser.getRole().getId() == 1 || loggedInUser.getRole().getId() == 2){
			forums = forumsTmp;
			topics = topicsTmp;
			replies = repliesTmp;
			
		}else if(loggedInUser.getRole().getId() == 3){
			
			for (Forum f : forumsTmp) {
				if(f.getType().getId() != 3) forums.add(f);
			}
			
			for (Topic t : topicsTmp) {
				Forum ff = ForumDAO.get(t.getForumId(), users);
				if(ff.getType().getId() != 3) topics.add(t);
			}

			for (Reply r : repliesTmp){
				Topic tt = TopicDAO.getTopic(r.getTopicId(), users);
				Forum fff = ForumDAO.get(tt.getForumId(), users);
				if(fff.getType().getId() != 3) replies.add(r);
			}
			
			
		}
		
		
		request.setAttribute("term", searchTerm);
		request.setAttribute("forums", forums);
		request.setAttribute("topics", topics);
		request.setAttribute("replies", replies);
		request.getRequestDispatcher("search.jsp").forward(request, response);
		return;
		
		
	}

}
