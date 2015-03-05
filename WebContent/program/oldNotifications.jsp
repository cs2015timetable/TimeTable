<%-- 
    Document   : oldNotifications
    Created on : 03-Mar-2015, 17:30:24
    Author     : bd13
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Forms.GenForm"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <%
	    if(session.getAttribute("login")==null){
			out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
		}
	    String userId = (String)session.getAttribute("userId");
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
                    <img src="images/notifications_1.png" class="logo" />
                </div>
                <h1>
                    Previous Notifications
                </h1>
                <p class="room">
                    <%
                        Forms.GenForm form = new Forms.GenForm();
                        // must be obtained by session when we implement them
                        out.write(form.getNotifications(userId, 1));
                    %>
                </p>
                <form method='post' action='notifications.jsp'>
                    <input id='meeting_button' type='submit' value='View Current Notifications'/>
                </form>
            </div>
        </div>
    </body>
</html>
