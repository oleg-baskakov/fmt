package sonet.findmytwins;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sonet.findmytwins.processors.AnswersService;

/**
 * Application Lifecycle Listener implementation class Initiator
 *
 */
public class Initiator implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public Initiator() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
		String path=arg0.getServletContext().getRealPath("grid/1");
		AnswersService.getInstance().initPics(path);

    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
