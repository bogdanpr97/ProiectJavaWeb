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
 * Servlet implementation class SendOrderServlet
 */
@WebServlet(urlPatterns = { "/sendOrder" })
public class SendOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/home.jsp");
            dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		int id = Integer.parseInt((String) request.getParameter("pid"));
		int serviceId = Integer.parseInt((String) request.getParameter("serviceSelect"));
		String sendOrderString = null;
		try {
			if(DBUtils.verifyCompatibility(conn, id, serviceId) == true) {
				request.setAttribute("productId", id);
				request.setAttribute("serviceId", serviceId);
				RequestDispatcher dispatcher = request.getServletContext()
		                .getRequestDispatcher("/WEB-INF/webPages/sendOrder.jsp");
		        dispatcher.forward(request, response);
			} else {
				sendOrderString = "Produsul si serviciul nu corespund!";
				request.setAttribute("sendOrderString", sendOrderString);
		        RequestDispatcher dispatcher = request.getServletContext()
		                .getRequestDispatcher("/WEB-INF/webPages/produse.jsp");
		        dispatcher.forward(request, response);
			}
		} catch (SQLException e) {
			sendOrderString = "Eroare, incercati mai tarziu!";
			e.printStackTrace();
			request.setAttribute("sendOrderString", sendOrderString);
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/webPages/produse.jsp");
	        dispatcher.forward(request, response);
		}
	}
	
}
