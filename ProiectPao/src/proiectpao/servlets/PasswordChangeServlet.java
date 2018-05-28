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
 * Servlet implementation class PasswordChangeServlet
 */
@WebServlet(urlPatterns = { "/passwordChange" })
public class PasswordChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/account.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		String parolaCurenta = (String) request.getParameter("parolaCurenta");
		String parolaNoua = (String) request.getParameter("parolaNoua");
		String errorString = null;
		if(parolaNoua.length() < 6) {
			errorString = "Parola noua nu are formatul corect!";
		} else {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginedUser");
			try {
				if(DBUtils.findUser(conn, user.getUserName(), parolaCurenta) != null) {
					DBUtils.changePassowrd(conn, user, parolaNoua);
					errorString = "Parola a fost schimbata cu succes!";
				} else {
					errorString = "Parola curenta nu este corecta!";
				}
			} catch (SQLException e) {
				errorString = "Eroare la modificarea parolei, incercati mai tarziu!";
				e.printStackTrace();
			}
		}
		request.setAttribute("errorString", errorString);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/account.jsp");
        dispatcher.forward(request, response);
	}

}
