<%-- 
    Document   : leaveGroup
    Created on : 04-Mar-2015, 17:27:25
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
            // should be obtained via session when implemented
            String groupId = request.getParameter("groupId");
            String noteId = request.getParameter("noteId");
            RegularUser x = new RegularUser();
            x.leaveGroup(groupId, userId, noteId);
            response.sendRedirect("notifications.jsp");
        %>
    </body>
</html>
