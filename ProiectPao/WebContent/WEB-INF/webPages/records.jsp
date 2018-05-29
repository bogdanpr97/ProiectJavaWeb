<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Rapoarte</title>
      <style>
      	form {
      		margin-left: 1%;
      		margin-bottom: 1.75%;
      	}
      	form div {
      		margin-bottom: 0.5%;
      	}
      	h4 {
      		margin-left: 1%;
      	}
      </style>
   </head>
   <body>
    
      <jsp:include page="header.jsp"></jsp:include>
      <jsp:include page="menu.jsp"></jsp:include>
       
      <h3>Rapoarte</h3>
       
      <h3 style="margin-left: 1%;">${errorString}</h3>
       
      <form method="POST" action="${pageContext.request.contextPath}/records">
      	 <div><label>Raport pe produs</label></div>
         <div><input type="text" name="name"></input></div>
         <input type="hidden" name="type" value="product"></input>
         <input type="submit" value="Arata raport">
      </form>
      <h4>${recordProduct}</h4>
      <form method="POST" action="${pageContext.request.contextPath}/records">
      	 <div><label>Raport pe serviciu</label></div>
         <div><input type="text" name="name"></input></div>
         <input type="hidden" name="type" value="service"></input>
         <input type="submit" value="Arata raport">
      </form>
      <h4>${recordService}</h4>
      <form method="POST" action="${pageContext.request.contextPath}/records">
      	 <div><label>Raport pe client</label></div>
         <div><input type="text" name="name"></input></div>
         <input type="hidden" name="type" value="user"></input>
         <input type="submit" value="Arata raport">
      </form>
      <h4>${recordUser}</h4>
      <jsp:include page="footer.jsp"></jsp:include>
       
   </body>
</html>