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


@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public LoginServlet() {
        super();
    }
 
    // Pagina de login
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        //  /WEB-INF/views/loginView.jsp
        // Userii nu pot accesa direct jsp
    	HttpSession session = request.getSession();
    	if(session.getAttribute("loginedUser") != null) {
            response.sendRedirect(request.getContextPath() + "/account");
        } else {
        	RequestDispatcher dispatcher //
            = this.getServletContext().getRequestDispatcher("/WEB-INF/webPages/login.jsp");

    dispatcher.forward(request, response);
        }
        
    }
 
    //userul trimite username si parola
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	if(session.getAttribute("loginedUser") != null) {
            response.sendRedirect(request.getContextPath() + "/account");
        }
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        
        User user = null;
        boolean hasError = false;
        String errorString = null;
 
        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Introduceti toate campurile!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                // Gaseste userul in db
                user = DBUtils.findUser(conn, userName, password);
 
                if (user == null) {
                    hasError = true;
                    errorString = "Nume sau parola invalide!";
                } else if (user != null && user.getDisabled().equals("1")) {
                	hasError = true;
                    errorString = "Cont blocat!";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        // In caz de eroare / retrimite catre login.jsp
        if (hasError) {
            user = new User();
            user.setUserName(userName);
            user.setPassword(password);
 
            // salveaza in request inainte de a trimite
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
 
            // duce la login
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/webPages/login.jsp");
 
            dispatcher.forward(request, response);
        }
        //salveaza in sesiune userul / trimite la account
        else {
            MyUtils.storeLoginedUser(session, user);
           
            response.sendRedirect(request.getContextPath() + "/account");
        }
    }
 
}
