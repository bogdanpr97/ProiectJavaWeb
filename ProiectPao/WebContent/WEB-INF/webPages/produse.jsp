<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="proiectpao.beans.*"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <script  src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
	crossorigin="anonymous"></script>
    <style>
    	table, th, td {
    		border: 1px solid black;
    	}
    	thd, td {
    		padding: 5px;]
    	}
    	span.span_comanda:hover {
    		cursor: pointer;
    		color: red;
    	}
    </style>
 </head>
 <body>
 
    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="menu.jsp"></jsp:include>
 
    <h3>Produse</h3>
 	<% if(session.getAttribute("loginedUser") == null) {%>
         <h4>Trebuie sa fii logat pentru a putea comanda!</h4>
    <%}%>
    <p style="color: red;">${errorString}</p>
 
    <table>
       <tr>
          <th>Cod</th>
          <th>Nume</th>
          <th>Pret</th>
          <th>Servicii</th>
          <% if(session.getAttribute("loginedUser") != null) {
          		User user = (User)session.getAttribute("loginedUser");
          		if(user.getPrivilege() == 2) {%>
          <th>Edit</th>
          <th>Delete</th>
          		<%} else {%>
          			<th>Poza</th>
          			<th>Comanda</th>
          			<%}%>
          <%}%>
       </tr>
       <c:forEach items="${productList}" var="product" >
          <tr>
             <td>${product.getCode()}</td>
             <td>${product.getName()}</td>
             <td>${product.getPrice()}</td>
             <td><select pid="${product.getId()}">
             		<c:forEach items="${product.listaServicii}" var="serviciu" >
             			<option value="${serviciu.getId()}">${serviciu.getName()}</option>
             		</c:forEach>
                 </select></td>
             <% if(session.getAttribute("loginedUser") != null) {
          		User user = (User)session.getAttribute("loginedUser");
          		if(user.getPrivilege() == 2) {%>
          <td>
                <a href="editProduct?pid=${product.getId()}">Editeaza</a>
             </td>
             <td>
                <a href="deleteProduct?pid=${product.getId()}">Sterge</a>
             </td>
          		<%} else {%>
          			<td><input type="file" name="file" pid="${product.getId()}"></input></td>
          			<td><span class="span_comanda" onclick="send_order(this)" pid="${product.getId()}">Comanda</span></td>
          			<%}%>
          <%}%>
             
          </tr>
       </c:forEach>
    </table>
 	<% if(session.getAttribute("loginedUser") != null) {
          		User user = (User)session.getAttribute("loginedUser");
          		if(user.getPrivilege() == 2) {%>
          			<p><a href="createProduct">Adauga un produs</a></p>
          			<p><a href="createService">Adauga un serviciu</a></p>
          		<%}
          }%>
  
    <jsp:include page="footer.jsp"></jsp:include>
 
 </body>
</html>