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
    		padding: 5px;
    	}
    	span.span_comanda:hover {
    		cursor: pointer;
    		color: red;
    	}
    	div {
    		margin-bottom: 0.5%;
    	}
    </style>
    <script>
    	/*$(document).ready(function() {
    		confirm_order = function(aux) {
    			alert('abc');
    			var id = parseInt($(aux).attr("oid"));
    			if(!isNaN(id)) {
	    			$.ajax({
	    		        type: "post",
	    		        url:  "/confirmOrder",
	    		        dataType: 'json',
			            data: "oid=" + id,
	    		        success: function () {
							
	    		        }
	    		    });
    			}
    		}
    	})*/
    </script>
 </head>
 <body>
 
    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="menu.jsp"></jsp:include>
 
    <h3>Comenzi</h3>
	
	<form method="POST" action="${pageContext.request.contextPath}/orders">
      	 <div><label>Cauta o comanda dupa ID</label></div>
         <div><input type="text" name="oid"></input></div>
         <input type="submit" value="Afiseaza">
    </form>
	
    <h3>${orderString}</h3>
 
 	<%if(request.getAttribute("order") != null) {%>
    <table>
       <tr>
          <th>Id</th>
          <th>Nume client</th>
          <th>Nume Produs</th>
          <th>Nume Serviciu</th>
          <th>Pret</th>
          <th>Nume poza</th>
          <th>Data</th>
          <th>Status</th>
          <%if(((Comanda) request.getAttribute("order")).getStatus().equals("nepregatita")) {%>
          <th></th>
          <%}%>
       </tr>
          <tr>
             <td>${order.getId()}</td>
             <td>${order.getNameClient()}</td>
             <td>${order.getNameProduct()}</td>
             <td>${order.getNameService()}</td>
             <td>${order.getPrice()}</td>
             <td>${order.getNameImg()}</td>
             <td>${order.getData()}</td>
             <td>${order.getStatus()}</td>
             <%if(((Comanda) request.getAttribute("order")).getStatus().equals("nepregatita")) {%>
             <td>
	             <form method="post" action="${pageContext.request.contextPath}/confirmOrder">
		             <input type="hidden" name="oid" value="${order.getId()}">
		             <!--<span class="span_comanda" onclick="confirm_order(this)" oid="${order.getId()}">Confirma</span>-->
		             <input type="submit" value="Confirma">
	             </form>
             </td>
             <%}%>
          </tr>
    </table>
    <%}%>
	<h3>${confirmString}</h3>
    <jsp:include page="footer.jsp"></jsp:include>
 
 </body>
</html>