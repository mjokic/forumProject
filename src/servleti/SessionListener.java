package servleti;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import obicne.User;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
public class SessionListener implements HttpSessionListener {

	private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
	
    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    	System.out.println("Kad se ovo poziva?!");
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    	HttpSession session = event.getSession();
    	sessions.put(session.getId(), session);
    	
    	System.out.println("session added...");
    }
    
    
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
    	sessions.remove(event.getSession().getId());
    	System.out.println("session destroyed...!");
    }
    
    
    public static Map<String, HttpSession> getSessions(){
    	return sessions;
    }
    
    
    public static void addSession(HttpSession session){
    	sessions.put(session.getId(), session);
    	System.out.println("session added1...");
    }
	
}
