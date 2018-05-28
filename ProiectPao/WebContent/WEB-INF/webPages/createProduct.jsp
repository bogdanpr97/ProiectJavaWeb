<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Adaugare produs nou</title>
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
       
      <h3>Adauga un produs nou</h3>
       
      <p style="color: red;">${errorString}</p>
       
      <form method="POST" action="${pageContext.request.contextPath}/createProduct">
         <table>
            <tr>
               <td>Cod</td>
               <td><input type="text" name="code"/></td>
            </tr>
            <tr>
               <td>Nume</td>
               <td><input type="text" name="name"/></td>
            </tr>
            <tr>
               <td>Pret</td>
               <td><input type="text" name="price"/></td>
            </tr>
            <tr>
               <td>Cantitate</td>
               <td><input type="text" name="quantity"/></td>
            </tr>
            <tr>
               <td>Servicii</td>
               <td>
               	  <select name="servicii" required multiple>
               	  <c:forEach items="${serviceList}" var="service">
				     <option value="${service.getId()}">${service.getName()}</option>
				   </c:forEach>
				   </select>
			   </td>
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