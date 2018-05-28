<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 <head>
    <meta charset="UTF-8">
    <title>Cont</title>
 </head>
 <body>
 
    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="menu.jsp"></jsp:include>
 
    <h2>Bine ai venit, ${user.userName}</h2>
    <h3>Schimbare parola</h3>
    <h4>${errorString}</h4>
 	  <form method="POST" action="${pageContext.request.contextPath}/passwordChange">
         <div style="margin-top: 0.4%;"><label>Parola curenta</label></div>
         <div style="margin-top: 0.4%;"><input type="password" name="parolaCurenta"/></div>
         <div style="margin-top: 0.4%;"><label>Parola noua</label></div>
         <div style="margin-top: 0.4%;"><input type="password" name="parolaNoua"/></div>
         <div style="margin-top: 0.4%;"><input type="submit" value= "Submit" /></div>
 	  </form>
    <jsp:include page="footer.jsp"></jsp:include>
 
 </body>
</html>