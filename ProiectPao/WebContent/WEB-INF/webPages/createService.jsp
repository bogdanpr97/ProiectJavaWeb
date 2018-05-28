<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Adaugare serviciu nou</title>
      <style>
    	table, th, td {
    		border: 1px solid black;
    	}
    	thd, td {
    		padding: 5px;]
    	}
    </style>
   </head>
   <body>
    
      <jsp:include page="header.jsp"></jsp:include>
      <jsp:include page="menu.jsp"></jsp:include>
       
      <h3>Adauga un serviciu nou</h3>
       
      <p style="color: red;">${errorString}</p>
       
      <form method="POST" action="${pageContext.request.contextPath}/createService">
         <table>
            <tr>
               <td>Nume</td>
               <td><input type="text" name="name"/></td>
            </tr>
            <tr>
               <td colspan="2">                   
                   <input type="submit" value="Submit" />
                   <a href="products">Cancel</a>
               </td>
            </tr>
         </table>
      </form>
       
      <jsp:include page="footer.jsp"></jsp:include>
       
   </body>
</html>