package proiectpao.utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import proiectpao.beans.User; 

public class MyUtils {
 
    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
 
 
    // Pune conexiunea in atributul request
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }
 
    // preia obiectul connection
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }
 
    // INFO de la user in session
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        // On the JSP can access via ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }
 
    // preia informatia
    public static User getLoginedUser(HttpSession session) {
        User loginedUser = (User) session.getAttribute("loginedUser");
        return loginedUser;
    }
    
}
