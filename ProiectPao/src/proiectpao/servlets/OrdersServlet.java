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

import proiectpao.beans.Comanda;
import proiectpao.beans.User;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class OrdersServlet
 */
@WebServlet(urlPatterns = { "/orders" })
public class OrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersServlet() {
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
                    .getRequestDispatcher("/WEB-INF/webPages/orders.jsp");
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
		int id = Integer.parseInt((String) request.getParameter("oid"));
		String orderString = null;
		Comanda order = null;
		try {
			order = DBUtils.findOrder(conn, id);
			if(order == null) {
				orderString = "Nu exista comanda cu acest id!";
			}
		} catch (SQLException e) {
			orderString = "Eroare, incercati mai tarziu!";
			e.printStackTrace();
		}
		request.setAttribute("orderString", orderString);
		request.setAttribute("order", order);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/orders.jsp");
        dispatcher.forward(request, response);
	}
}
