package sonet.findmytwins;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sonet.findmytwins.data.ProfileData;
import sonet.findmytwins.processors.HibernateUtil;

public class DBUtils {

	private static Pattern pat = Pattern.compile("(\\d+)\\s*(m|s|h|d)");
	
	public DBUtils() {
	}


	
	public static Object getObjectByName(Class obj, String name) {
		// List<YPItem> items = new ArrayList<YPItem>();
		Session ses = HibernateUtil.getSessionFactory().openSession();
		Object result = null;
		try {

			ses.beginTransaction();

			Criteria crit = ses.createCriteria(obj.getClass()).add(
					Restrictions.eq("name", name)).setMaxResults(1);

			result = crit.uniqueResult();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	
	
	public static Object getObject(Class obj, long id) {
		// List<YPItem> items = new ArrayList<YPItem>();
		Session ses = HibernateUtil.getSessionFactory().openSession();
		Object result = null;
		try {

			ses.beginTransaction();

			result =ses.get(obj, id);
			Hibernate.initialize(result);
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}
	
	
	public static synchronized List getObjects(Class obj) {
		List result = new ArrayList();
		Session ses = HibernateUtil.getSessionFactory().openSession();
		try {

			ses.beginTransaction();
			Criteria crit = ses.createCriteria(obj);// .setFetchMode("phones",
			// FetchMode.JOIN)

			result = crit.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static void updateObject(Object obj) {

		Session ses = HibernateUtil.getSessionFactory().openSession();
		try {

			synchronized (obj) {
				ses.beginTransaction();
				ses.saveOrUpdate(obj);
				ses.flush();
				ses.getTransaction().commit();

			}
		} catch (Exception e) {
//			ses.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			HibernateUtil.closeSession(ses);
		}

	}

	public static void deleteObject(Object obj) {

		Session ses = HibernateUtil.getSessionFactory().openSession();
		try {

			synchronized (obj) {
				ses.beginTransaction();
				ses.delete(obj);
				ses.flush();
				ses.getTransaction().commit();

			}
		} catch (Exception e) {
//			ses.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			HibernateUtil.closeSession(ses);
		}

	}
	
	public static void deleteObjects(ArrayList objs, String tableName) {

		Session ses = HibernateUtil.getSessionFactory().openSession();
		try {

			synchronized (objs) {
			
				ses.beginTransaction();
				Query query = ses.createQuery("delete from "+tableName+" obj where obj.id in (:objs)")
				.setParameterList("objs", objs);
				query.executeUpdate();
				
				ses.flush();
				ses.getTransaction().commit();

			}
		} catch (Exception e) {
//			ses.getTransaction().rollback();
			e.printStackTrace();

		} finally {
			HibernateUtil.closeSession(ses);
		}

	}
	
	public static void main(String[] args) {
		
	}

	public static void setProp(long userId, String param, String val) {
		Session ses = null;
		try {

			ProfileData task = getProp(userId, param);
			if (task != null) {
				task.setValue(val);
			}else{
				task=new ProfileData();
				task.setUserId(userId);
				task.setParam(param);
				task.setValue(val);
			}
			ses = HibernateUtil.getSession();
			ses.beginTransaction();

			ses.saveOrUpdate(task);
			ses.flush();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (ses.getTransaction().isActive())
				ses.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession(ses);
		}

	}

	

	
	public static void deleteProp(ProfileData prop) {

		Session ses = null;
		try {
			ses = HibernateUtil.getSession();
			ses.beginTransaction();
			ses.delete(prop);
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (ses!=null&&ses.getTransaction().isActive())
				ses.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession(ses);
		}

	}
	
	public static ProfileData  getProp(long userId, String param) {

		Session ses = null;
	
		try {

			ses = HibernateUtil.getSession();
			ses.beginTransaction();
			Criteria crit = ses.createCriteria(ProfileData.class)
			.add(Restrictions.eq("userId", userId))
			.add(Restrictions.eq("param", param))
					
			.setMaxResults(1);

			ProfileData task = (ProfileData) crit.uniqueResult();
			
			if (task != null) {
				return task;
			}

			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (ses.getTransaction().isActive())
				ses.getTransaction().rollback();
		}finally{
			HibernateUtil.closeSession(ses);
		}
		return null;

	}
	/*
	public static void addProps(String key, ArrayList<String> vals, String type) {

		Session ses = null;
		try {

			ProfileData task ;
			for (String val : vals) {
				ses = HibernateUtil.getSession();
				ses.beginTransaction();
				task = new PropRecord();
				task.setSkey(key);
				task.setType(type);
				task.setValue(val);

				ses.save(task);
				ses.flush();
				ses.getTransaction().commit();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (ses.getTransaction().isActive())
				ses.getTransaction().rollback();
		}finally{
			if(ses!=null&&ses.isOpen())
				ses.close();
		}

	}
*/
	/*
	public static ArrayList<PropRecord>  getProps(String key) {
		
		ArrayList<PropRecord> result=new ArrayList<PropRecord>();

		Session ses = null;
		try {

			ses = HibernateUtil.getSession();
			ses.beginTransaction();
			Criteria crit = ses.createCriteria(PropRecord.class)
			.add(Restrictions.eq("skey", key)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			result=(ArrayList<PropRecord>) crit.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (ses.getTransaction().isActive())
				ses.getTransaction().rollback();
		}finally{
			if(ses!=null&&ses.isOpen())
				ses.close();
		}
		return result;

	}
*/
	
	
	
}
