<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Login</title>
   </head>
   <body>
      <jsp:include page="header.jsp"></jsp:include>
      <jsp:include page="menu.jsp"></jsp:include>
 
      <h3>Creare cont</h3>
      <p style="color: red;">${errorString}</p>
 
      <form method="POST" action="${pageContext.request.contextPath}/register">
         <table style="border:none">
            <tr>
               <td>Username</td>
               <td><input type="text" name="userName"/> </td>
            </tr>
            <tr>
               <td>Email</td>
               <td><input type="text" name="email"/> </td>
            </tr>
            <tr>
               <td>Parola</td>
               <td><input type="password" name="password"/> </td>
            </tr>
            <tr>
               <td colspan ="2">
                  <input type="submit" value= "Submit" />
               </td>
            </tr>
         </table>
      </form>
      <jsp:include page="footer.jsp"></jsp:include>
   </body>
</html>