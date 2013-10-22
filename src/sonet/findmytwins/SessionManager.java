package sonet.findmytwins;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.python.modules.synchronize;

public class SessionManager {

	
	private HashMap<String,HttpSession> sessions;
	private static SessionManager instance=new SessionManager();
	
	private SessionManager(){
		sessions= new HashMap<String, HttpSession>();
	}
	
	public static SessionManager getInstance(){
		return instance;
	}
	
	public void addSession(HttpSession session){
		synchronized(sessions){
			sessions.put(session.getId(), session);
		}
	}
	
	public HttpSession getSession(String key){
		return sessions.get(key);
	}
	
	public int getSessionsCount(){
		return sessions.size();
	}

	public void removeSession(HttpSession session) {
		synchronized(sessions){
			sessions.remove(session.getId());
		}
	}
	
}
