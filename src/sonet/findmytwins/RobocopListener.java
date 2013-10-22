package sonet.findmytwins;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class RobocopListener
 */
public class RobocopListener implements Filter {

	
	Logger log=Logger.getRootLogger();
    /**
     * Default constructor. 
     */
    public RobocopListener() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		String uri = req.getRequestURI();
		String url = req.getContextPath();
		try {
			
		if(uri.endsWith("login.zul")
				||uri.endsWith("register.zul")
				||uri.endsWith("/FB/")
				){
			
		}else{
		HttpSession session = req.getSession();
		
        if(session==null||session.getAttribute("user")==null){
        	String referer = req.getHeader("REFERER");
        	
        	String host = req.getHeader("HOST");
        	if (referer != null && referer.indexOf("facebook.com") != -1) { // coming from another site
            	RequestDispatcher rd = req.getRequestDispatcher("/FB/");
            	rd.forward(request, response);
        		//((HttpServletResponse)response).sendRedirect(url+"/FB/");
            	
        	}else
        	((HttpServletResponse)response).sendRedirect("login.zul");
        }
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
