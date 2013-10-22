package sonet.findmytwins.processors;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import sonet.findmytwins.DBUtils;
import sonet.findmytwins.data.Twins;
import sonet.findmytwins.data.User;
import sonet.findmytwins.utils.Profiler;

public class DBProcessor {

	private static final int _MAX_TWINS = 200;

	
	
	
	public DBProcessor() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	
		
	}

	/*
	 * public static ArrayList<User> getUsersWithResultIn(User user) { int
	 * key=Profiler.start("getUsersWithResultIn "+user.getName());
	 * 
	 * ArrayList<User> result = new ArrayList<User>(); Session ses =
	 * HibernateUtil.getSession(); try {
	 * 
	 * ses.beginTransaction(); // Query query =ses.createQuery(
	 * "from User where (result/:res)%2=1 or (result2/:res2)%2=1 or (result3/:res3)%2=1 or (result4/:res4)%2=1"
	 * ) Query query = ses.createQuery(
	 * "from FMT_USER where bitwise_and(result,:res)<>0 or bitwise_and(result2,:res2)<>0 or bitwise_and(result3,:res3)<>0 or bitwise_and(result4,:res4)<>0 "
	 * ) .setInteger("res", user.getResult()) .setInteger("res2",
	 * user.getResult2()) .setInteger("res3", user.getResult3())
	 * .setInteger("res4", user.getResult4()) ; // Criteria crit =
	 * ses.createCriteria(User.class);// .setFetchMode("phones", //
	 * FetchMode.JOIN)
	 * 
	 * result = (ArrayList<User>) query.list(); ses.getTransaction().commit(); }
	 * catch (Exception e) { e.printStackTrace();
	 * ses.getTransaction().rollback();
	 * 
	 * } finally {
	 * 
	 * if (ses != null&&ses.isOpen()) { ses.flush(); ses.close(); } }
	 * Profiler.fin(key); return result;
	 * 
	 * 
	 * }
	 */
	
	public static int getMaxSMPower(User user){
		int result=0;
		/*
		 * 
		 select max(num), count(num) from ( 
	select count(ua.number) as num
	from UserAnswers ua,  FMT_USER u 
	where number in(4,34,56,12,43,51,11,65,37,21) and ua.useranswer_id=u.id 
	group by u.id
	) a where num<10 group by num
		 * */
		Session ses=null;
		try {
			ArrayList<Byte> answers = getUserAnswers(user);
			ses = HibernateUtil.getSession();
			ses.beginTransaction();
			Query query = ses
					.createSQLQuery(
							" select max(num) from " +
							"   (select count(ua.number) as num" +
							"	  from UserAnswers ua,  FMT_USER u	" +
							"     where number in(:answers) and ua.useranswer_id=u.id and ua.useranswer_id<>:uid" +
							"	group by u.id	) a ")
					.setParameterList("answers", answers).setLong("uid", user.getId());
			

			result =  ((BigInteger) query.uniqueResult()).intValue();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}
	
	public static ArrayList<User> getTwinsWithPower(User user, int power) {

		ArrayList<User> result = new ArrayList<User>();
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses
					.createQuery(
							" select tw.twin from Twins tw where tw.user=:user and tw.twinPower=:power")
					.setInteger("power", power).setEntity("user", user);
			// /Criteria crit = ses.createCriteria(User.class);//
			// .setFetchMode("phones",
			// FetchMode.JOIN)

			result = (ArrayList<User>) query.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static ArrayList<Byte> getUserAnswers(User user) {
		ArrayList<Byte> result = new ArrayList<Byte>();
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(
					"select number from UserAnswers where user=:user").setEntity("user",
					user);
			// Criteria crit = ses.createCriteria(User.class);//
			// .setFetchMode("phones",
			// FetchMode.JOIN)

			result = (ArrayList<Byte>) query.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	
	public static void deleteUserAnswers(User user) {
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(
					"delete  from UserAnswers where user=:user").setEntity("user",
					user);
			// Criteria crit = ses.createCriteria(User.class);//
			// .setFetchMode("phones",
			// FetchMode.JOIN)

		query.executeUpdate();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		
	}
	
	public static ArrayList<Byte> getTwinsMatches(User user, User twin) {
		ArrayList<Byte> result = new ArrayList<Byte>();
		Session ses = HibernateUtil.getSession();
		try {

			/*
			 * select u.id, count(ua.number) as num1 from UserAnswers ua,
			 * FMT_User u where number in(10,33,24,99) and ua.useranswer_id=u.id
			 * group by u.id having count(ua.number)>1
			 */

			ses.beginTransaction();

			Query query = ses
					.createQuery(
							"select ua.number from UserAnswers ua,UserAnswers ta"
									+ "  where ua.user=:user and ta.user=:twin and ta.number=ua.number")
					.setEntity("user", user).setEntity("twin", twin);
			// Criteria crit = ses.createCriteria(User.class);//
			// .setFetchMode("phones",
			// FetchMode.JOIN)

			result = (ArrayList<Byte>) query.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static ArrayList<Object> getTwins(ArrayList answers, int power) {
		ArrayList<Object> result = new ArrayList<Object>();
		Session ses = HibernateUtil.getSession();
		try {

			/*
			 * select u.id, count(ua.number) as num1 from UserAnswers ua,
			 * FMT_User u where number in(10,33,24,99) and ua.useranswer_id=u.id
			 * group by u.id having count(ua.number)>1
			 */

			ses.beginTransaction();
			Query query = ses.createQuery(
					"select u.id from UserAnswers ua,  FMT_USER u "
							+ "where number in(:nums) and ua.user=u "
							+ "group by u having count(ua.number)=:power")
					.setParameterList("nums", answers).setInteger("power", power)
					.setMaxResults(_MAX_TWINS);

			//			
			// Query query = ses.createQuery(
			// "select u.id, count(ua.number) as num1 from UserAnswers ua,  FMT_USER u "
			// +
			// "where number in(:nums) and ua.user=u" +
			// " group by u having count(ua.number)>=:power")
			// .setParameterList("nums", answers )
			// .setInteger("power", power);
			// Query query =
			// ses.createQuery("select ua.number from UserAnswers ua,UserAnswers ta"
			// +
			// "  where ua.user=:user and ta.user=:twin and ta.number=ua.number")
			// .setEntity("user", user)
			// .setEntity("twin", twin)
			// ;
			// Criteria crit = ses.createCriteria(User.class);//
			// .setFetchMode("phones",
			// FetchMode.JOIN)

			result = (ArrayList<Object>) query.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static ArrayList<Object> getTwins(ArrayList answers, int power, int start,
			int size) {
		ArrayList<Object> result = new ArrayList<Object>();
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(
					"select u.id from UserAnswers ua,  FMT_USER u "
							+ "where number in(:nums) and ua.user=u "
							+ "group by u having count(ua.number)=:power")
					.setParameterList("nums", answers).setInteger("power", power)
					.setFetchSize(size).setFirstResult(start);

			result = (ArrayList<Object>) query.list();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static int getTwinsTotal(ArrayList answers, int power) {
		int result = 0;
		synchronized (answers) {

			Session ses = HibernateUtil.getSession();
			try {

				ses.beginTransaction();
				Query query = ses.createQuery(
						"select count(u.id) from UserAnswers ua,  FMT_USER u "
								+ "where number in(:nums) and ua.user=u "
								+ "group by u having count(ua.number)=:power")
						.setParameterList("nums", answers).setInteger("power", power);

				result = (Integer) query.uniqueResult();
				ses.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				ses.getTransaction().rollback();

			} finally {

				HibernateUtil.closeSession(ses);
			}
		}
		return result;
	}

	public static int getTotalTestedUsers() {
		int result = 0;

		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery("select count(u.id) from FMT_USER u "
					+ "where u.testDate is not null ");

			result = ((Long) query.uniqueResult()).intValue();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
//			ses.getTransaction().rollback();
		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static User getUser(String login, String hashPwd) {

		User result = null;
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(
					" from FMT_USER where login=:login and pwd=:pwd").setString("pwd",
					hashPwd).setString("login", login);

			result = (User) query.uniqueResult();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {
			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static User getUser(String login) {

		User result = null;
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(" from FMT_USER where login=:login").setString(
					"login", login);

			result = (User) query.uniqueResult();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}

	public static User getUser(String sonetUserId, int sonetId) {

		User result = null;
		Session ses = HibernateUtil.getSession();
		try {

			ses.beginTransaction();
			Query query = ses.createQuery(" from FMT_USER where sonetUserId=:snuid and profileType=:snid")
			.setString(	"snuid", sonetUserId)
			.setInteger("snid", sonetId);

			result = (User) query.uniqueResult();
			ses.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			//ses.getTransaction().rollback();

		} finally {

			HibernateUtil.closeSession(ses);
		}
		return result;
	}
	
}
