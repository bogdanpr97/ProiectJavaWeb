package proiectpao.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
 * Servlet implementation class ConfirmOrderServlet
 */
@WebServlet(urlPatterns = { "/confirmOrder" })
public class ConfirmOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmOrderServlet() {
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
		String confirmString = null;
		try {
			DBUtils.confirmOrder(conn, id);
			String email = DBUtils.findEmailOrder(conn, id);
			String host = "smtp.mail.yahoo.com";
		    String port = "465";
		    String emailid = null;
		    final String username = "robertgrmds@yahoo.com";
		    final String password = "zxc567bnM0";
		    Properties props = System.getProperties();
		    Session l_session = null;
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		    props.put("mail.smtp.host", host);
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.debug", "false");
	        props.put("mail.smtp.port", port);
	        l_session = Session.getInstance(props,
	                new javax.mail.Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                    }
	                });
	        l_session.setDebug(true);
	        try {
	            MimeMessage message = new MimeMessage(l_session);
	            emailid = "robertgrmds@yahoo.com";
	            message.setFrom(new InternetAddress(emailid));
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	            message.setSubject("Comanda proiectpao");
	            message.setContent("Comanda cu numarul " + id + " este pregatita si puteti sa veniti sa o ridicati. Multumim!", "text/html");
	            Transport.send(message);
	            confirmString = "Clientul a fost notificat!";
	        } catch (MessagingException mex) {
	        	confirmString = "Eroare, incercati mai tarziu!";
	            mex.printStackTrace();
	        } catch (Exception e) {
	        	confirmString = "Eroare, incercati mai tarziu!";
	            e.printStackTrace();
	        }
		} catch (SQLException e) {
			confirmString = "Eroare, incercati mai tarziu!";
			e.printStackTrace();
		}
		request.setAttribute("confirmString", confirmString);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/orders.jsp");
        dispatcher.forward(request, response);
	}

}
