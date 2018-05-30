package proiectpao.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import proiectpao.beans.*;
import proiectpao.utils.*;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet(urlPatterns = { "/account" })
public class AccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public AccountServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
 
        // sa fie logat
        User loginedUser = MyUtils.getLoginedUser(session);
 
        //nu e logat
        if (loginedUser == null) {
            // duce la login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // salveaza in request
        request.setAttribute("user", loginedUser);
 
        // daca e logat il duce in account jsp
       
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/webPages/account.jsp");
        dispatcher.forward(request, response);
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
