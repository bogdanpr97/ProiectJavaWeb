<% 
	if(request.getAttribute("productId") == null || request.getAttribute("serviceId") == null) {
		RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/webPages/home.jsp");
        dispatcher.forward(request, response);
	}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Trimite comanda</title>
	<style>
		form div {
			margin-bottom: 0.4%;
		}
		
		form {
			margin-left: 3%;
		}
	</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="menu.jsp"></jsp:include>
    <h3>Finalizare comanda</h3>
    <h4 style="color: red;">${finalizeOrderString}</h4>
    <form method="post" action="${pageContext.request.contextPath}/finalizeOrder" enctype="multipart/form-data">
    	<div><label>Incarcati poza pe care doriti sa o imprimati.</label></div>
    	<div><input type="file" name="file"/></div>
    	<input type="hidden" name="pid" value="${productId}"/>
    	<input type="hidden" name="sid" value="${serviceId}"/>
    	<div><h4>Date de livrare</h4></div>
    	<div><label>Nume</label></div>
    	<div><input type="text" name="nume"/></div>
    	<div><label>Prenume</label></div>
    	<div><input type="text" name="prenume"/></div>
    	<div><label>Telefon</label></div>
    	<div><input type="text" name="telefon"/></div>
    	<div><label>Judet</label></div>
    	<div><input type="text" name="judet"/></div>
    	<div><label>Localitate</label></div>
    	<div><input type="text" name="localitate"/></div>
    	<div><label>Strada</label></div>
    	<div><input type="text" name="strada"/></div>
    	<div><label>Bloc</label></div>
    	<div><input type="text" name="bloc"/></div>
    	<div><label>Apartament</label></div>
    	<div><input type="text" name="apartament"/></div>
    	<input style="margin-top: 1%;" type="submit" value="Trimite"/>
    </form>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>