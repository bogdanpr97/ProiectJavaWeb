<% 
	if(request.getAttribute("aux") == null) {
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
<title>Comanda trimisa</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="menu.jsp"></jsp:include>
    <h2>Comanda a fost trimisa, veti primi un email de confirmare, multumim!</h2>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>