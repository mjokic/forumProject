package servleti;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.ws.client.SenderException;

import dao.ReplyDAO;
import dao.TopicDAO;
import dao.UserDAO;
import obicne.Reply;
import obicne.User;

/**
 * Servlet implementation class Members
 */
public class Members extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Members() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");

		ServletContext context = getServletContext();
		ArrayList<User> users = (ArrayList<User>) context.getAttribute("users");
		
		int userId;
		ArrayList<obicne.Topic> topics = null;
		ArrayList<Reply> replies = null;
		
		try {
			userId = Integer.parseInt(id);
			topics = TopicDAO.getTopicByUserId(userId, users);
			replies = ReplyDAO.getReplyByUserId(userId, users);
			
		} catch (Exception e) {
			// TODO: handle exception
			String message = "Id mora biti broj!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("./error.jsp").forward(request, response);
			return;
		}
		
//		System.out.println(userId);
		
		User user = UserDAO.get(userId);
		
		if(user == null){
			String message = "Ne postoji user!";
			request.setAttribute("message", message);
			request.getRequestDispatcher("./error.jsp").forward(request, response);
			return;
		}
		
		
//		users.add(user);
//		request.setAttribute("users", users);
		request.setAttribute("member", user);
		request.setAttribute("topics", topics);
		request.setAttribute("replies", replies);
		request.getRequestDispatcher("./members.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
