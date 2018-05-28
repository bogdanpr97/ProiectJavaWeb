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

import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class ManagementUsersServlet
 */
@WebServlet(urlPatterns = { "/managementUsers" })
public class ManagementUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementUsersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/managementUsers.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String username = (String) request.getParameter("username");
		String tip = (String) request.getParameter("tip");
		String errorString = null;
		try {
			if(DBUtils.findUser(conn, username) != null) {
				if(tip.equals("blocare")) {
					if(DBUtils.statusUser(conn, username) == 1) {
						errorString = "Utilizatorul este deja blocat!";
					} else {
						DBUtils.blockUser(conn, username);
					}
				} else if(tip.equals("deblocare")) {
					if(DBUtils.statusUser(conn, username) == 0) {
						errorString = "Utilizatorul nu este blocat!";
					} else {
						DBUtils.unblockUser(conn, username);
					}
				}
			} else {
				errorString = "Utilizatorul nu exista!";
			}
		} catch (SQLException e) {
			e.printStackTrace();
            errorString = e.getMessage();
		}
		if(errorString == null) {
			errorString = "Cerere reusita!";
		}
		request.setAttribute("errorString", errorString);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/managementUsers.jsp");
        dispatcher.forward(request, response);
	}

}
