package sonet.findmytwins.processors;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Format;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.persistence.Entity;

//import org.python.modules.synchronize;
import org.zkoss.image.AImage;

import sonet.findmytwins.DBUtils;
import sonet.findmytwins.SessionManager;
import sonet.findmytwins.data.ProfileData;
import sonet.findmytwins.data.Twins;
import sonet.findmytwins.data.User;
import sonet.findmytwins.data.UserAnswers;
import sonet.findmytwins.utils.Profiler;
import sun.misc.BASE64Encoder;

public class AnswersService {

	public static final int NUM_SELECTED_PIC = 9;
	private static final String SECRET_STRING = "WHERE_ARE_WILD_ROSES_GROW";
	static ArrayList<Byte> userAnswersList;
	static byte[] numBits = new byte[255];
	private volatile static AnswersService instance;
	private HashMap imgData;

	private AnswersService() {
		// initTables();
	}

	public static AnswersService getInstance() {
		if (instance == null)
			instance = new AnswersService();
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int num = 100;
		String a="sdfsdf<p> sdfdf< p> dsdf<br /> vsvsd sd ";
		System.out.println(a.replaceAll("<\\w+||\\s>", "!"));
		fillUsersAnswers(num,1400);
		// byte[] asnwers = getRndAnswers();
		 //testTwins();
		 System.out.println(String.format("%1$03d",23));
		// findTwinsWithPower(9);

	}

	public static void printTwins(User user, HashMap<Integer, ArrayList<Twins>> allTwins) {

		String matches;
		System.out.println("User has answers: " + getMatches(user, user));
		for (int i = 1; i <= 10; i++) {
			if (!allTwins.containsKey(i)) {
				System.out.println("(" + i + ") matches is absent ");
				continue;
			}
			System.out.println("(" + i + ") matches ");
			ArrayList<Twins> twins = allTwins.get(i);
			for (Twins twin : twins) {
				matches = getMatches(user, twin.getTwin());
				System.out.println("User has matches in: " + matches);

			}
			System.out.println("-------------------------------- ");
		}
	}

	public static void printTwins2(User user, ArrayList<Object> allTwins) {

		String matches;
		System.out.println("User has answers: " + getMatches(user, user));
		User twin;
		for (int i = 0; i < allTwins.size(); i++) {

			twin = (User) DBUtils.getObject(User.class, (Long) allTwins.get(i));
			matches = getMatches(user, twin);
			System.out.println("User " + twin.getName() + " has matches in: " + matches);

			System.out.println("-------------------------------- ");
		}
	}

	private static String getMatches(User user, User twin) {
		String matches = "";
		ArrayList<Byte> res = DBProcessor.getTwinsMatches(user, twin);
		for (Byte answer : res) {
			matches += answer + ", ";
		}
		return matches;
	}

	private static void testTwins() {
		User user = (User) DBUtils.getObject(User.class, (long) 337);
		AnswersService as = new AnswersService();

		as.seekTwins2(user);

		// userAnswersList = DBProcessor.getUserAnswers(user);
		// int key=Profiler.start("get Total Twins ");
		// HashMap<Integer, ArrayList<Twins>> res = as.getTwins(user);
		// Profiler.fin(key);
		// printTwins(user, res);
		// System.out.println(res);

	}

	private static void findTwinsWithPower(int power) {
		ArrayList<User> res = (ArrayList<User>) DBUtils.getObjects(User.class);
		ArrayList answers;
		int i = 0;
		AnswersService as = new AnswersService();
		ArrayList<Object> result;
		for (User user : res) {
			answers = DBProcessor.getUserAnswers(user);
			result = DBProcessor.getTwins(answers, power);
			if (result.size() > 0) {
				System.out.println("For user " + user.getName() + " total:"
						+ result.size());
			}
			if (i++ % 100 == 0)
				System.out.println("Scanned " + i);
		}
	}
//Generate users
	private static void fillUsersAnswers(int num,int start) {

		double rnd;
		int size = 127;
		byte[] answers = new byte[10];
		for (int i = 0; i < num; i++) {

			User user = new User();
			user.setName("user " + start+i);
			user.setLogin("user_" + start+i);
			user.setEmail("user" +start+ i + "@findmytwins");
			DBUtils.updateObject(user);
			answers = getRndAnswers();
			
			int key = Profiler.start("getanswers " + i);
			saveUserAnswers(user, answers);
			Profiler.fin(key);	
			if (i % 100 == 0)
				System.out.println("Filled of " + i);
		}

	}

	/**
	 */
	private static byte[] getRndAnswers() {
		double rnd;
		Math.random();
		Math.random();
		Math.random();
		byte[] answers = new byte[10];
		ArrayList<Byte> tmp = new ArrayList<Byte>();
		int j = 0;
		while (j < 10) {
			rnd = Math.random() * 94;
			if (!tmp.contains((byte) rnd)) {
				answers[j] = (byte) rnd;
				tmp.add(answers[j]);
				j++;
			}
		}
		return answers;
	}

	public User loginUser(String name, String pwd) {
		User user = null;
		String hashPwd = "";
		try {
				hashPwd = getMD5HashCode(pwd);
				user = DBProcessor.getUser(name, hashPwd);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public  User createUser(String name, String pwd) {
		User user = null;

			try {

				if (DBProcessor.getUser(name) != null)
					return null;
				user = new User();
				user.setLogin(name);
				user.setName(name);
				user.setEmail(name + "@findmytwins");
				String pwdHashed = getMD5HashCode(pwd);
				user.setPwd(pwdHashed);
				DBUtils.updateObject(user);
			} catch (Exception e) {
				e.printStackTrace();
				user = null;
			}
		return user;
	}

	public ArrayList<ArrayList<String[]>> getImages(int step) {
		ArrayList<ArrayList<String[]>> images = null;
		int rows = 8;
		int cols = 6;
		int start = 0;

		if (step == 2) {
			rows = 2;
			start = 30;
		}
		images = new ArrayList<ArrayList<String[]>>();
		ArrayList<String[]> row;
		int k = 0;
		String[] props;
		String imgName;
		HashMap imgProps ;
		for (int i = 0; i < rows; i++) {
			row = new ArrayList<String[]>();
			for (int j = 0; j < cols; j++) {
				props = new String[4];
				imgName="Thumb" + String.format("%1$03d",k) + ".jpg";
				props[0] = "grid/" + step + "/" + imgName;
				props[1] = "idp" + (start + k);
				if(imgData.containsKey(imgName)){
					imgProps = (HashMap) imgData.get(imgName);
					props[2]=(String) imgProps.get("width");
					props[3]=(String) imgProps.get("height");
				}
				k++;
				row.add(props);
			}
			images.add(row);
		}
		return images;
	}

	public static synchronized void saveUserAnswers(User user, byte[] answers) {
		UserAnswers ua;
		int key = Profiler.start("remove answers " );

		DBProcessor.deleteUserAnswers(user);
	Profiler.fin(key);
		for (byte b : answers) {

			ua = new UserAnswers();
			ua.setNumber(b);
			ua.setUser(user);
			DBUtils.updateObject(ua);
			// if(b<32){
			// result1|=temp<<b;
			// }else if(b<64){
			// result2|=temp<<(b-32);
			// }else if(b<96){
			// result3|=temp<<(b-64);
			// }else if(b<128){
			// result4|=temp<<(b-96);
			// }
		}
		// user.setResult(result1);
		// user.setResult2(result2);
		// user.setResult3(result3);
		// user.setResult4(result4);
		// DBUtils.updateObject(user);

	}

	public static HashMap<String, Object> saveUserAnswers(User user,
			ArrayList<String> answers) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
//			synchronized (user) {

				if (answers == null || user == null) {
					result.put("error", "Error in answers data");
					return result;
				}
				byte[] userAnswers = new byte[answers.size()];
				String tmp;
				int i = 0;
				for (String answer : answers) {
					if (answer.startsWith("idp")) {
						tmp = answer.substring(3);
						byte bTmp = Byte.parseByte(tmp);
						userAnswers[i++] = bTmp;
					}
				}
				saveUserAnswers(user, userAnswers);
				result.put("page", "result.zul");
	//		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

//	public HashMap<Integer, ArrayList<Twins>> getTwins(User user) {
//
//		int power = 0;
//		HashMap<Integer, ArrayList<Twins>> twinsMap = new HashMap<Integer, ArrayList<Twins>>();
//
//		for (int i = 1; i <= 10; i++) {
//
////			int key = Profiler.start("getTwins for " + user.getName() + " i=" + i);
//			ArrayList<User> twins = getTwinsWithPower(user, i);
////			if (twins.size() > 0)
////				 twinsMap.put(i, twins);
////				Profiler.fin(key);
//
//		}
//		return twinsMap;
//
//	}

	private ArrayList<User> getTwinsWithPower(User user, int power) {

		ArrayList<User> result = new ArrayList<User>();
		result = DBProcessor.getTwinsWithPower(user, power);
		return result;
	}

	public ArrayList<HashMap<String, String>> getResultTabs(User user) {

		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> powerMap;
			for (int i = 1; i <= NUM_SELECTED_PIC; i++) {
				powerMap = new HashMap<String, String>();
				powerMap.put("id", "tbr_" + i);
				powerMap.put("tabName", "SM Power:" + i);
				powerMap.put("power", "" + i);
				result.add(powerMap);
			}

		return result;
	}

	public ArrayList<HashMap<String, String>> getTwinsForTab(User user, String id) {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> twinsMap;
		if (user == null || id == null || id.length() == 0)
			return result;
//		synchronized (user) {
			try {

				int power = Integer.parseInt(id);
				if (power < 1)
					return result;
				ArrayList answers = DBProcessor.getUserAnswers(user);

//				int key = Profiler.start("seekTwins with" + power + "  power for "
//						+ user.getName());
				ArrayList<Object> uids = DBProcessor.getTwins(answers, power);
				User twin;
				if (power == NUM_SELECTED_PIC)
					uids.remove(user.getId());
				String name;

				ProfileData tmp;
				for (Object uid : uids) {

					twin = (User) DBUtils.getObject(User.class, (Long) uid);
					name = twin.getName();
					if(twin.getSurname()!=null){
						name += " "+twin.getSurname();
					}
					twinsMap = new HashMap<String, String>();
					twinsMap.put("avatar", twin.getPic());
					twinsMap.put("name", name);
					twinsMap.put("id", "" + twin.getId());
					//tmp=DBUtils.getProp((Long) uid, "fb_about_me");
					
				//	twinsMap.put("about", tmp==null?"-":tmp.getValue());
					
					twinsMap.put("country", twin.getCountry());
					twinsMap.put("gender", twin.getSex()==null?"-":twin.getSex());
					twinsMap.put("profileLink", twin.getProfileLink());

					result.add(twinsMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	//	}

		return result;
	}

	public void seekTwins2(User user) {
		ArrayList answers = DBProcessor.getUserAnswers(user);
		int key = Profiler.start("seekTwins with 1 power " + user.getName());
		ArrayList<Object> result = DBProcessor.getTwins(answers, 1);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		key = Profiler.start("seekTwins with 2 power for " + user.getName());
		result = DBProcessor.getTwins(answers, 2);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		key = Profiler.start("seekTwins with 3 power for " + user.getName());
		result = DBProcessor.getTwins(answers, 3);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		key = Profiler.start("seekTwins with 4 power for " + user.getName());
		result = DBProcessor.getTwins(answers, 4);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		printTwins2(user, result);
		key = Profiler.start("seekTwins with 5 power for " + user.getName());
		result = DBProcessor.getTwins(answers, 5);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		printTwins2(user, result);
		key = Profiler.start("seekTwins with 6 power for " + user.getName());
		result = DBProcessor.getTwins(answers, 6);
		Profiler.fin(key);
		System.out.println("total:" + result.size());
		printTwins2(user, result);

	}

	public int getTotalTestedUser() {
		int result = 0;
		try {

			result = DBProcessor.getTotalTestedUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getOnlineUsers() {
		int result = 0;
		try {

			result = SessionManager.getInstance().getSessionsCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * public void seekTwins(User user) { if (user == null || user.getResult()
	 * == 0) return; int key = Profiler.start("seekTwins for " +
	 * user.getName()); ArrayList<User> users =
	 * DBProcessor.getUsersWithResultIn(user); int tmp; int power; Twins twins;
	 * System.out.println("Seekin twins for " + user.getName()); int key2 =
	 * Profiler.start("seektwins process results"); for (User twin : users) { //
	 * int key3=Profiler.start("seektwins process results per user"); if
	 * (twin.getId() == user.getId()) continue; tmp = twin.getResult() &
	 * user.getResult(); power = getTwinPower2(tmp); tmp = twin.getResult2() &
	 * user.getResult2(); if (tmp > 0) power += getTwinPower2(tmp); tmp =
	 * twin.getResult3() & user.getResult3(); if (tmp > 0) power +=
	 * getTwinPower2(tmp); tmp = twin.getResult4() & user.getResult4(); if (tmp
	 * > 0) power += getTwinPower2(tmp); twins = new Twins(); if (power < 1) {
	 * System.out.println("Strange - power<1 for " + twin.getName()); continue;
	 * } twins.setTwin(twin); twins.setTwinPower(power); twins.setUser(user);
	 * DBUtils.updateObject(twins); // Profiler.fin(key3);
	 * 
	 * } Profiler.fin(key2); Profiler.fin(key); System.out.println("Found " +
	 * users.size());
	 * 
	 * }
	 * 
	 * private int getTwinPower(int tmp) {
	 * 
	 * int power = 0; for (int i = 0; i < 32; i++) { if (((tmp >>> i) & 1) == 1)
	 * { power++; } } return power; }
	 */
//	private int getTwinPower2(int tmp) {
//
//		int power = 0;
//		byte tmp2 = (byte) (tmp & 255);
//		power = numBits[tmp2];
//		tmp2 = (byte) ((tmp >>> 8) & 255);
//		power += numBits[tmp2];
//		tmp2 = (byte) ((tmp >>> 16) & 255);
//		power += numBits[tmp2];
//		tmp2 = (byte) ((tmp >>> 24) & 255);
//		power += numBits[tmp2];
//
//		return power;
//	}

//	private byte getPower(byte tmp) {
//
//		byte power = 0;
//		for (int i = 0; i < 8; i++) {
//			if (((tmp >>> i) & 1) == 1) {
//				power++;
//			}
//		}
//		return power;
//	}

//	private int initTables() {
//
//		int power = 0;
//		for (int i = 0; i < 255; i++) {
//			numBits[i] = getPower((byte) i);
//		}
//		return power;
//	}
	
	public void initPics(String path){
		AImage img;
		String imgPath=path+"";
		File folder=new File(imgPath);
		imgData=new HashMap<String, HashMap<String, HashMap>>();
		HashMap<String, String> imgProp= new HashMap<String, String>();
		File[] imgFiles=folder.listFiles();
		for (File file : imgFiles) {
			try {
				img=new AImage(file);
				imgProp=new HashMap<String, String>();
				imgProp.put("height", ""+img.getHeight());
				imgProp.put("width", ""+img.getWidth());
				imgData.put(file.getName(), imgProp);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static synchronized String getMD5HashCode(String text)

	{
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			String md5Hash = new String(md.digest((SECRET_STRING + text).getBytes()),"US-ASCII");
			//int hash = md5Hash.hashCode();
			return md5Hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
