<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="proiectpao.beans.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="proiectpao.utils.*"%>
<%@ page import="java.sql.Connection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="/WEB-INF/webPages/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Proiectpao</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="menu.jsp"></jsp:include>
		<h2>Acasa</h2>
		<section id="boxes">
      <div class="container">
        <div class="box">
         
          <h3>Tricouri</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies</p>
        </div>
        <div class="box">
         
          <h3>Cani</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies</p>
        </div>
        <div class="box">
         
          <h3>Orice vrei!</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus mi augue, viverra sit amet ultricies</p>
        </div>
      </div>
    </section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>