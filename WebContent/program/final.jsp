<%-- 
    Document   : final
    Created on : 26-Feb-2015, 17:21:38
    Author     : bd13
--%>

<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<!DOCTYPE html>
<html>
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
    <%
		if(session.getAttribute("login")==null){
			out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
		}
	    String userId =(String) session.getAttribute( "userId" );
		String name =(String)request.getParameter( "info" );
	    String[] students= request.getParameterValues( "students[]" );
	    String module = (String)request.getParameter("module");
	    boolean failure =!isset(module)||(students==null||students.length<1);
		if(!failure){
			out.write("<meta http-equiv=\"refresh\" content=\"3;url=index.jsp\" />");
		}
	%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>University College Cork (UCC)</title>
         <meta charset="UTF-8" />
    <link rel="stylesheet" href="css/studentStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/meeting.css" type="text/css" />
    <link href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/themes/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    	<div id = "logout">
            <a href="index.jsp"><img src="images/homeIcon.png" class="logo" /></a>
        </div>
        <div id="content">
            <img src="images/logo.png" class="logo" />
        </div>
        <div id="form-main">
		  <div id="form-div">
		  <form class="form" id="form1">
				<div id="meetImg">
					<img src="images/createGroup.png" class="logo" />
				</div>
        <%
        	if(failure){
        		response.sendRedirect("group.jsp");
        	}
	        else{
	            Forms.RegularUser x = new Forms.RegularUser();
	            // take the list of students, the chosen module and the user's name/id
	            // to create their
	            try{
	                x.createGroup(userId, (isset(name)?name:module), Arrays.asList(students));
						out.write("<h1>Success!</h1>"+
						"<p>Your Group has successfully been created."+ 
						"You will be redirected to the menu in a few moments</p>");
					}
	            catch(Exception e){
	                
	            }
	        }
        %>
   </form>
	 </div>
    </div>
  </body>
</html>