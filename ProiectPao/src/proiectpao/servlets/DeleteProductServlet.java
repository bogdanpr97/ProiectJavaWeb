package proiectpao.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proiectpao.beans.Produs;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class DeleteProductServlet
 */
@WebServlet(urlPatterns = { "/deleteProduct" })
public class DeleteProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public DeleteProductServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        int id = Integer.parseInt((String) request.getParameter("pid"));
 
        String errorString = null;
 
        try {
            DBUtils.deleteProdus(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } 
         
        
        if (errorString != null) {
           
            request.setAttribute("errorString", errorString);
            
            List<Produs> list = null;
            try {
                list = DBUtils.queryProdus(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("productList", list);
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/produse.jsp");
            dispatcher.forward(request, response);
        }
            
        else {
            response.sendRedirect(request.getContextPath() + "/products");
        }
 
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
