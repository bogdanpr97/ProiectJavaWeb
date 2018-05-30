package proiectpao.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import proiectpao.beans.User;
import proiectpao.utils.DBUtils;
import proiectpao.utils.MyUtils;

/**
 * Servlet implementation class FinalizeOrderServlet
 */
@WebServlet(urlPatterns = { "/finalizeOrder" })
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
		maxFileSize=1024*1024*10,      // 10MB
		maxRequestSize=1024*1024*50)
public class FinalizeOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalizeOrderServlet() {
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginedUser");
        String finalizeOrderString = null;
        String fileName = "";
        
        FileItemFactory factory = new DiskFileItemFactory();

        // File Upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            // Salveaza toati parametrii din request in lista
            List items = upload.parseRequest(new ServletRequestContext(request));
            Iterator iterator = items.iterator();
            while (iterator.hasNext()) {
                FileItem item = (FileItem) iterator.next();
                if(item.isFormField()) {
                	session.setAttribute(item.getFieldName(), item.getString());
                }
                if (!item.isFormField()) {
                    String fileName1 = item.getName();
                    File f = new File(fileName1);
                    fileName = f.getName();
                    String root = "C:\\Users\\dvigrup\\Downloads\\ProiectJavaWeb-master\\ProiectJavaWeb-master\\ProiectPao";
                    File path = new File(root + "/uploads");
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    fileName =  user.getUserName() + " - " + fileName;
                    File uploadedFile = new File(path + "/" + fileName);
                    System.out.println(uploadedFile.getAbsolutePath());
                    item.write(uploadedFile);
                }
            }
        } catch (FileUploadException e) {
        	finalizeOrderString = "Eroare, incercati mai tarziu!";
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/webPages/sendOrder.jsp");
	        dispatcher.forward(request, response);
        } catch (Exception e) {
        	finalizeOrderString = "Eroare, incercati mai tarziu!";
            e.printStackTrace();
            RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/webPages/sendOrder.jsp");
	        dispatcher.forward(request, response);
        }
        
        int pid = Integer.parseInt((String) session.getAttribute("pid"));
		int sid = Integer.parseInt((String) session.getAttribute("sid"));
		String telefon = (String) session.getAttribute("telefon");
		String nume = (String) session.getAttribute("nume");
		String prenume = (String) session.getAttribute("prenume");
		String judet = (String) session.getAttribute("judet");
		String localitate = (String) session.getAttribute("localitate");
		String strada = (String) session.getAttribute("strada");
		String bloc = (String) session.getAttribute("bloc");
		String apartament = (String) session.getAttribute("apartament");
        try {
    		
			DBUtils.finalizeOrder(conn, user.getUserName(), pid, sid, nume, prenume, telefon, judet, localitate, strada, bloc, apartament, fileName);
			request.setAttribute("aux", "1");
			RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/webPages/orderSent.jsp");
	        dispatcher.forward(request, response);
		} catch (SQLException e) {
			finalizeOrderString = "Eroare, incercati mai tarziu!";
			e.printStackTrace();
			request.setAttribute("productId", pid);
			request.setAttribute("serviceId", sid);
			request.setAttribute("finalizeOrderString", finalizeOrderString);
	        RequestDispatcher dispatcher = request.getServletContext()
	                .getRequestDispatcher("/WEB-INF/webPages/sendOrder.jsp");
	        dispatcher.forward(request, response);
		}
	}

}
