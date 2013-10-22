package sonet.findmytwins.processors;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import sonet.findmytwins.data.ProfileData;
import sonet.findmytwins.data.Twins;
import sonet.findmytwins.data.User;
import sonet.findmytwins.data.UserAnswers;


public class HibernateUtil {

	Logger log = Logger.getRootLogger();

	private static ThreadLocal<Session> sessions= new ThreadLocal<Session>();
	
	  private static  SessionFactory sessionFactory ;//=new AnnotationConfiguration().configure().buildSessionFactory();;
	 // private static  Ejb3Configuration ejb3Configuration;
	static{
	    try{
	      AnnotationConfiguration aconf = new AnnotationConfiguration()
	      .addAnnotatedClass(User.class)
	      .addAnnotatedClass(Twins.class)
	      .addAnnotatedClass(UserAnswers.class)
	      .addAnnotatedClass(ProfileData.class)
	      ;
	     // .configure("hibernate.cfg.xml");
	      aconf.configure("/hibernate.cfg.xml");
	      sessionFactory = aconf.buildSessionFactory();
//	      ejb3Configuration=new Ejb3Configuration()
//	      .addAnnotatedClass(Element.class)
//	      .addAnnotatedClass(Container.class)
//	      .configure("/hibernate.cfg.xml");
	    }catch(Exception e){
	    	e.printStackTrace();

	    }
	}


	private HibernateUtil(){
		
	}
	
	public static  SessionFactory getSessionFactory() {
			return sessionFactory;
	}

	public static Session getSession() {
		
		Session ses=sessions.get();
		if(ses==null){
			ses=sessionFactory.openSession();
			sessions.set(ses);
		}
		
		return ses;
	}

	public static void closeSession(Session ses) {
		if (ses != null&&ses.isOpen())  {
			ses.flush();
			ses.close();
		}
		ses=null;
		sessions.remove();
	}

	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	
		
	}

	

	
}
