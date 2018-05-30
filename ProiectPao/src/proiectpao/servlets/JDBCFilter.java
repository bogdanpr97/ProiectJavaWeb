package proiectpao.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import proiectpao.beans.User;
import proiectpao.conn.ConnectionUtils;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet Filter implementation class JDBCFilter
 */
@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {

    /**
     * Default constructor. 
     */
    public JDBCFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	private boolean needJDBC(HttpServletRequest request) {
       
        String servletPath = request.getServletPath();
        
        String pathInfo = request.getPathInfo();
 
        String urlPattern = servletPath;
 
        if (pathInfo != null) {
           
            urlPattern = servletPath + "/*";
        }
 
     
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();
 
      
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest req = (HttpServletRequest) request;
     
        if (this.needJDBC(req)) {
 
            
 
            Connection conn = null;
            try {
              
                conn = ConnectionUtils.getConnection();
 
                
                MyUtils.storeConnection(request, conn);
                HttpSession session = req.getSession();
            	if(session.getAttribute("loginedUser") != null) {
            		User user = (User) session.getAttribute("loginedUser");
                    if(DBUtils.statusUser(conn, user.getUserName()) == 1) {
                    	session.setAttribute("loginedUser", null);
                    }
                }
               
                chain.doFilter(request, response);
 
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(conn);
            }
        }
       
        else {
           
            chain.doFilter(request, response);
        }
 
    }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
