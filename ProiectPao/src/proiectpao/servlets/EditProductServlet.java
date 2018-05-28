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

import proiectpao.beans.Produs;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class EditProdusServlet
 */
@WebServlet(urlPatterns = { "/editProduct" })
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public EditProductServlet() {
        super();
    }
 
    // Show Produs edit page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        int id = Integer.parseInt((String) request.getParameter("pid"));
 
        Produs Produs = null;
 
        String errorString = null;
 
        try {
            Produs = DBUtils.findProdusId(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
 
        // If no error.
        // The Produs does not exist to edit.
        // Redirect to ProdusList page.
        if (errorString != null && Produs == null) {
            response.sendRedirect(request.getServletPath() + "/products");
            return;
        }
 
        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", Produs);
 
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/editProduct.jsp");
        dispatcher.forward(request, response);
 
    }
 
    // After the user modifies the Produs information, and click Submit.
    // This method will be executed.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        String code = (String) request.getParameter("code");
        String name = (String) request.getParameter("name");
        String priceStr = (String) request.getParameter("price");
        String quantityStr = (String) request.getParameter("quantity");
        float price = 0;
        int quantity = 0;
        String errorString = null;
        try {
        	quantity = Integer.parseInt(quantityStr);
            price = Float.parseFloat(priceStr);
        } catch (Exception e) {
        	errorString = "Pretul si cantitate treubie sa fie un numar!";
        }
        Produs Produs = new Produs(code, name, price, quantity);
 
        try {
            DBUtils.updateProdus(conn, Produs);
        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        }
        // Store infomation to request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("product", Produs);
 
        // If error, forward to Edit page.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/editProduct.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the Produs listing page.
        else {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }
 
}	
