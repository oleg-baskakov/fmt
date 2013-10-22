package sonet.findmytwins.service.fb;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.w3c.dom.Document;

import sonet.findmytwins.DBUtils;
import sonet.findmytwins.SessionManager;
import sonet.findmytwins.data.User;
import sonet.findmytwins.processors.AnswersService;
import sonet.findmytwins.processors.DBProcessor;
import sonet.findmytwins.processors.FBService;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.ProfileField;

/**
 * Servlet implementation class FBInitServlet
 */
public class FBInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	String api;
	String secret;

	public FBInitServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		api = config.getInitParameter("API_KEY");
		secret = config.getInitParameter("SECRET_KEY");
		// String path;
		// path=this.getServletContext().getRealPath("grid/1");
		// AnswersService.getInstance().initPics(path);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String sessionKey = (String) session.getAttribute("FBAppSession");
		String authToken = request.getParameter("auth_token");
		FacebookJsonRestClient client = null;
		try {

			if (authToken != null) {
				client = new FacebookJsonRestClient(api, secret, sessionKey);
				if(session.getAttribute("client")==null){
					try {
						sessionKey = client.auth_getSession(authToken);
					} catch (Exception e) {
						session.invalidate();
						response.sendRedirect("http://www.facebook.com/login.php?api_key="
								+ api);
						return;
					}
					session.setAttribute("FBAppSession", sessionKey);
					setClient(client, session);
				}
//				if (sessionKey != null&&client.auth_getSession(authToken)!=null) {
//					// LegacyFacebookClient facebookClient = new
//					// DefaultLegacyFacebookClient(MY_API_KEY, MY_SECRET_KEY);
//					if (client.auth_expireSession()) {  
//						
//						response.sendRedirect("http://www.facebook.com/login.php?api_key="
//								+ api);
//						return;
//
//					} else
//				} else {
//					client = new FacebookJsonRestClient(api, secret);
//					// client.setIsDesktop(false);
//					try {
//						sessionKey = client.auth_getSession(authToken);
//						session.setAttribute("FBAppSession", sessionKey);
//						setClient(client, session);
//					} catch (Exception e) {
//						e.printStackTrace();
//						response.sendRedirect("http://www.facebook.com/login.php?api_key="
//								+ api);
//						return;
//					}
//				}
			} else {
				if (sessionKey != null) {
					try {
						//String ses = client.auth_getSession(authToken);
						if (client.auth_expireSession()) {  
						response.sendRedirect("http://www.facebook.com/login.php?api_key="
								+ api);
						return;

						} 
						
					} catch (Exception e) {
						session.invalidate();
						response.sendRedirect("http://www.facebook.com/login.php?api_key="
								+ api);
						return;
					}
				}else{
				response.sendRedirect("http://www.facebook.com/login.php?api_key="
						+ api);
				return;
				}
			}

			String encUrl = response.encodeRedirectURL("../index.zul");
			response.sendRedirect(encUrl);
			// RequestDispatcher rd =
			// request.getRequestDispatcher("/index.zul");
			// rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setClient(FacebookJsonRestClient client, HttpSession session) {
		FBService fbClient = new FBService(client);
		long snuid;
		try {
			snuid = client.users_getLoggedInUser();
			User user = DBProcessor.getUser("" + snuid, User.PROFILE_FB);
			if (user == null) {
				user = new User();
				fbClient.fillProfile(user);
			}
			user.setLastLogin(new Date());
			DBUtils.updateObject(user);
			session.setAttribute("user", user);

			session.setAttribute("client", fbClient);
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
