package sonet.findmytwins.processors;

import java.util.HashMap;

import sonet.findmytwins.data.User;

public interface SonetService {

	public HashMap connect();
	public User fillProfile(User user);
	public void sendMessage(User user, String theme, String msg);
}
