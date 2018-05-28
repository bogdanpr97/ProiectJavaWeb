<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Management clienti</title>
      <style>
      	form {
      		margin-left: 1%;
      		margin-bottom: 1.75%;
      	}
      	form div {
      		margin-bottom: 0.5%;
      	}
      </style>
   </head>
   <body>
    
      <jsp:include page="header.jsp"></jsp:include>
      <jsp:include page="menu.jsp"></jsp:include>
       
      <h3>Managementul clientilor</h3>
       
      <h3 style="margin-left: 1%;">${errorString}</h3>
       
      <form method="POST" action="${pageContext.request.contextPath}/managementUsers">
      	 <div><label>Blocheaza un utilizator</label></div>
         <div><input type="text" name="username"></input></div>
         <input type="hidden" name="tip" value="blocare"></input>
         <input type="submit" value="Blocheaza">
      </form>
      <form method="POST" action="${pageContext.request.contextPath}/managementUsers">
      	 <div><label>Deblocheaza un utilizator</label></div>
         <div><input type="text" name="username"></input></div>
         <input type="hidden" name="tip" value="deblocare"></input>
         <input type="submit" value="Deblocheaza">
      </form>
      <jsp:include page="footer.jsp"></jsp:include>
       
   </body>
</html>