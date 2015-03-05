<%-- 
    Document   : nConfirm
    Created on : 03-Mar-2015, 16:59:37
    Author     : bd13
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Forms.RegularUser"%>
<!DOCTYPE html>
<html>
    <head>
    <%
    if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
	}
    String userId = (String)session.getAttribute("userId");
    %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            RegularUser x = new RegularUser();
            x.confirmNotifications(userId);
            response.sendRedirect("index.jsp");
        %>
    </body>
</html>
