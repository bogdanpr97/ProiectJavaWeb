<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<header style="background-color: green; height: 55px; padding: 5px;">
  <div style="float: left">
     <h1>Printuri PRO</h1>
  </div>
 
  <div style="float: right; padding: 10px; text-align: right;">
     <!-- User store in session with attribute: loginedUser -->
     <% if(session.getAttribute("loginedUser") != null) {%>
   			<span>Bine ai venit, <b>${loginedUser.userName}!</b></span>
   	 <%} %>
     
   <br/>
 
  </div>
 
</header>