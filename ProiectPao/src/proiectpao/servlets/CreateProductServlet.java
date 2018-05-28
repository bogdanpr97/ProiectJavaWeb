package proiectpao.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import proiectpao.beans.Produs;
import proiectpao.beans.Serviciu;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class CreateProductServlet
 */
@WebServlet(urlPatterns = { "/createProduct" })
public class CreateProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    public CreateProductServlet() {
        super();
    }
 
    // Show product creation page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Connection conn = MyUtils.getStoredConnection(request);
    	List<Serviciu> services = new ArrayList<Serviciu>();
    	try {
			services = DBUtils.queryServicii(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	request.setAttribute("serviceList", services);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/createProduct.jsp");
        dispatcher.forward(request, response);
    }
 
    // When the user enters the product information, and click Submit.
    // This method will be called.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = MyUtils.getStoredConnection(request);
 
        String code = (String) request.getParameter("code");
        String name = (String) request.getParameter("name");
        String priceStr = (String) request.getParameter("price");
        String quantityStr = (String) request.getParameter("quantity");
        String[] idServices = (String[]) request.getParameterValues("servicii");
   
        String errorString = null;
        float price = 0;
        int quantity = 0;
        try {
        	quantity = Integer.parseInt(quantityStr);
            price = Float.parseFloat(priceStr);
        } catch (Exception e) {
        	errorString = "Pretul si cantitate treubie sa fie un numar!";
        }
        Produs product = new Produs(code, name, price, quantity);
        for(int i=0; i<idServices.length; i++) {
        	int id = Integer.parseInt(idServices[i]);
        	Serviciu serviciu = new Serviciu();
        	serviciu.setId(id);
        	try {
				serviciu.setName(DBUtils.findServiciuNume(conn, id));
			} catch (SQLException e) {
				serviciu.setName("");
				e.printStackTrace();
			}
        	product.getListaServicii().add(serviciu);
        }
        
 
        // Product ID is the string literal [a-zA-Z_0-9]
        // with at least 1 character
        String regex = "\\w+";
 
        if (code == null || !code.matches(regex)) {
            errorString = "Codul produsului este invalid!";
        }
 
        if (errorString == null) {
            try {
                DBUtils.insertProdus(conn, product);
            } catch (SQLException e) {
                e.printStackTrace();
                errorString = e.getMessage();
            }
        }
        // Store infomation to request attribute, before forward to views.
        List<Serviciu> services = new ArrayList<Serviciu>();
    	try {
			services = DBUtils.queryServicii(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	request.setAttribute("serviceList", services);
        request.setAttribute("errorString", errorString);
 
        // If error, forward to Edit page.
        if (errorString != null) {
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/webPages/createProduct.jsp");
            dispatcher.forward(request, response);
        }
        // If everything nice.
        // Redirect to the product listing page.
        else {
            response.sendRedirect(request.getContextPath() + "/products");
        }
    }
 
}
