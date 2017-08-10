package servleti;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

import dao.ConnectionManager;
import dao.ForumDAO;
import dao.ForumTypeDAO;
import dao.RoleDAO;
import dao.UserDAO;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import obicne.ForumType;
import obicne.Role;
import obicne.Forum;
import obicne.User;

/**
 * Application Lifecycle Listener implementation class InitListener
 *
 */
public class InitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
         // TODO Auto-generated method stub
    	ConnectionManager.destroy();
    	
    	
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
         // TODO Auto-generated method stub
    	ConnectionManager.init();
    	
    	ServletContext context = event.getServletContext();
    	
    	UserDAO.roles = RoleDAO.get();
    	ForumDAO.forumTypes = ForumTypeDAO.get();
    	
    	ArrayList<User> users = UserDAO.get();
    	
    	
    	context.setAttribute("users", users);
    	
    	
    }
	
}
