package proiectpao.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proiectpao.beans.User;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class RecordsServlet
 */
@WebServlet(urlPatterns = { "/records" })
public class RecordsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginedUser");
    	if(user != null && user.getPrivilege() == 1) {
    		RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/home.jsp");
            dispatcher.forward(request, response);
        } else if (user != null && user.getPrivilege() == 2) {
        	RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/records.jsp");
            dispatcher.forward(request, response);
        } else if (user == null) {
        	RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/home.jsp");
            dispatcher.forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginedUser");
    	if(user != null && user.getPrivilege() == 1) {
    		RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/home.jsp");
            dispatcher.forward(request, response);
        }
		Connection conn = MyUtils.getStoredConnection(request);
		String name = (String) request.getParameter("name");
		String type = (String) request.getParameter("type");
		String recordProduct = null;
		String recordService = null;
		String recordUser = null;
		if(type.equals("product")) {
			try {
				if(name.length() > 2) {
					recordProduct = "' " + name + " ' - " + DBUtils.recordObject(conn, name, type) + " vanzari";
				} else {
					recordProduct = "Introduceti numele!";
				}
			} catch (SQLException e) {
				recordProduct = "Eroare, incercati mai tarziu!";
				e.printStackTrace();
			}
		} else if(type.equals("service")) {
			try {
				if(name.length() > 2) {
					recordService = "' " + name + " ' - " + DBUtils.recordObject(conn, name, type) + " vanzari";
				} else {
					recordService = "Introduceti numele!";
				}
			} catch (SQLException e) {
				recordService = "Eroare, incercati mai tarziu!";
				e.printStackTrace();
			}
		} else if(type.equals("user")) {
			try {
				if(name.length() > 2) {
					recordUser = "' " + name + " ' - " + DBUtils.recordObject(conn, name, type) + " vanzari";
				} else {
					recordUser = "Introduceti numele!";
				}
			} catch (SQLException e) {
				recordUser = "Eroare, incercati mai tarziu!";
				e.printStackTrace();
			}
		}
		request.setAttribute("recordProduct", recordProduct);
		request.setAttribute("recordService", recordService);
		request.setAttribute("recordUser", recordUser);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/records.jsp");
        dispatcher.forward(request, response);
	}

}
