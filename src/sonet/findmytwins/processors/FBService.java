package sonet.findmytwins.processors;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.ProfileField;

import sonet.findmytwins.DBUtils;
import sonet.findmytwins.data.User;

public class FBService implements SonetService {

	public static final String ABOUT_ME = "about_me";
	FacebookJsonRestClient client;
	Logger log = Logger.getRootLogger();

	public FBService(FacebookJsonRestClient client) {
		this.client = client;
	}

	public HashMap connect() {

		return null;
	}

	public User fillProfile(User user) {
		try {

			long uid = client.users_getLoggedInUser();

			HashSet<ProfileField> fields = new HashSet<ProfileField>();
			HashSet<Long> uids = new HashSet<Long>();
			uids.add(uid);
			fields.add(ProfileField.ABOUT_ME);
			fields.add(ProfileField.BIRTHDAY);
			fields.add(ProfileField.FIRST_NAME);
			fields.add(ProfileField.HOMETOWN_LOCATION);
			fields.add(ProfileField.INTERESTS);
			fields.add(ProfileField.LAST_NAME);
			fields.add(ProfileField.PIC_SQUARE);
			fields.add(ProfileField.ACTIVITIES);
			fields.add(ProfileField.BOOKS);
			fields.add(ProfileField.INTERESTS);
			fields.add(ProfileField.MOVIES);
			fields.add(ProfileField.MUSIC);
			fields.add(ProfileField.SEX);
			fields.add(ProfileField.TV);
			fields.add(ProfileField.QUOTES);
			fields.add(ProfileField.PROFILE_URL);

			JSONArray res = client.users_getInfo(uids, fields);
			JSONObject profile;
			user.setSonetUserId("" + uid);
			user.setLogin("fb_" + uid);
			user.setProfileType(User.PROFILE_FB);

			String temp;
			JSONObject tempJSO;
			if (res != null && res.length() > 0) {
				profile = res.getJSONObject(0);
				temp = profile.getString("activities");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_activities", temp);
				}
				temp = profile.getString("first_name");
				if (!empty(temp)) {
					user.setName(temp);
				}
				temp = profile.getString("pic_square");
				if (!empty(temp)) {
					user.setPic(temp);
				}

				temp = profile.getString("sex");
				if (!empty(temp)) {
					user.setSex(temp);
				}
				temp = profile.getString("last_name");
				if (!empty(temp)) {
					user.setSurname(temp);
				}
				temp = profile.getString("books");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_books", temp);
				}
				temp = profile.getString("interests");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_interests", temp);
				}
				temp = profile.getString("movies");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_movies", temp);
				}
				temp = profile.getString("music");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_music", temp);
				}
				temp = profile.getString("tv");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_tv", temp);
				}
				temp = profile.getString("quotes");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_quotes", temp);
				}
				temp = profile.getString(ABOUT_ME);
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_" + ABOUT_ME, temp);
				}
				temp = profile.getString("birthday");
				if (!empty(temp)) {
					user.setBirthday(temp);
				}
				if (!profile.isNull("hometown_location")) {
					tempJSO = profile.getJSONObject("hometown_location");
					if (tempJSO != null) {
						temp = tempJSO.getString("country");
						if (!empty(temp)) {
							user.setCountry(temp);
						}
						temp = tempJSO.getString("name");
						if (!empty(temp)) {
							DBUtils.setProp(uid, "fb_hometown_location_name",
									temp);
						}
					}
				}
				temp = profile.getString("interests");
				if (!empty(temp)) {
					DBUtils.setProp(uid, "fb_interests", temp);
				}
				temp = profile.getString("profile_url");
				if (!empty(temp)) {
					user.setProfileLink(temp);
				}
				log.info("Fill info for FB user " + user.getName());
			}

			System.out.println(res);
		} catch (Exception e) {
			log.error("12", e);
		}
		return user;
	}

	private boolean empty(String temp) {
		if (temp != null && temp.trim().length() > 0
				&& !"null".equalsIgnoreCase(temp))
			return false;
		return true;
	}

	public void sendMessage(User user, String theme, String msg) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
