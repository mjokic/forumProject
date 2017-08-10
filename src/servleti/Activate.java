package servleti;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement;

import dao.ActivateDAO;
import dao.UserDAO;

/**
 * Servlet implementation class Activate
 */
public class Activate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Activate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String key = request.getParameter("key");
		// check if it's valid key
		
		String message = null;
		
		if(!key.isEmpty()){
			int userId = ActivateDAO.verifyKey(key);
			
			if(userId != 0){
				message = "Successfully verified!";
				
				if(UserDAO.updateActMember(userId, true)){
					ActivateDAO.deleteKey(userId);
				}
				
			}else{
				message = "FAILED at verification!";
			}
		}
		

		request.setAttribute("message", message);
		request.getRequestDispatcher("activation.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
