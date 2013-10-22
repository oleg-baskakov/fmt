package sonet.findmytwins;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
public class SessionListener extends org.zkoss.zk.ui.http.HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent arg0) {
    	super.sessionCreated(arg0);
		SessionManager.getInstance().addSession(arg0.getSession());
   }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0) {
    	super.sessionDestroyed(arg0);
    	SessionManager.getInstance().removeSession(arg0.getSession());
    }
	
}
