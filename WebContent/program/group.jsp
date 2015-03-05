<%-- 
    Document   : process
    Created on : 25-Feb-2015, 14:56:43
    Author     : bd13
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
    <% 
	if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
	}
	String userId =(String) session.getAttribute( "userId" );
    %>
        <title>
            University College Cork (UCC)
        </title>
        <meta charset="UTF-8" />
        <!--This is a comment. scripts for multiple select-->
        <!--multiple select css-->
        <link rel="stylesheet" type="text/css" href="css/jquery.bsmselect.css" />

        <link rel="stylesheet" href="css/studentStyle.css" type="text/css" />
        <link rel="stylesheet" href="css/meeting.css" type="text/css" />
        <link href="multi-select.css" media="screen" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.5.1.min.js">
        </script>
        <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/jquery-ui.min.js">
        </script>
        <link href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/themes/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />

        <!--multiselect javascript-->
        <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.js"></script>
        <script type="text/javascript" src="jquery.bsmselect.js"></script>
        <script type="text/javascript" src="jquery.bsmselect.sortable.js"></script>
        <script type="text/javascript" src="jquery.bsmselect.compatibility.js"></script>
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
                <div id="meetImg">
                    <img src="images/createGroup.png" class="logo" />
                </div>
                <form id="myform" method="post" action="process.jsp">
                    <p class="room">
                    <select name="module" class="validate[required,custom[email]] feedback-input" id="mySelect" onchange="myFunction()">
                        <option value="" selected="selected">Choose a module</option>
                        <%
	                        if(isset(request.getParameter("submit"))){
	                    		out.write("<p style='color:#E60000'>Please enter all inputs correctly</p>");
	                    	}
                            Forms.GenForm form = new Forms.GenForm();
                            out.write(form.createAnotherForm(userId));
                        %>
                    </select>
                    </p>
                </form>
                <script>
                    function myFunction() {
                        var x = document.getElementById("mySelect").value;
                        // prevents user sending 
                        if(x != ""){
                            document.getElementById("myform").submit();
                        }
                    }
                </script>
            </div>
        </div>
    </body>
</html>