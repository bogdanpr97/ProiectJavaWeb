<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="proiectpao.beans.*"%>
<div style="padding: 5px;">
 
   <a href="${pageContext.request.contextPath}/">Acasa</a>
   |
   <a href="${pageContext.request.contextPath}/products">Produse</a>
   |
   <% if(session.getAttribute("loginedUser") != null) {%>
   <a href="${pageContext.request.contextPath}/account">Cont</a>
   |
   <% if(session.getAttribute("loginedUser") != null) {
          		User user = (User)session.getAttribute("loginedUser");
          		if(user.getPrivilege() == 2) {%>
          			<a href="managementUsers">Management clienti</a> |
          			<a href="records">Rapoarte</a> |
          			<a href="orders">Comenzi</a> |
          		<%}
   }%>
   <a href="${pageContext.request.contextPath}/logout">Logout</a>
   <%} %>
   <% if(session.getAttribute("loginedUser") == null) {%>
   <a href="${pageContext.request.contextPath}/login">Intra in cont</a>
   |
   <%} %>
   <% if(session.getAttribute("loginedUser") == null) {%>
   <a href="${pageContext.request.contextPath}/register">Inregistrare</a>
   <%} %>
</div>  