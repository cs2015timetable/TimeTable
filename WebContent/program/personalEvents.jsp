<%@page import="authentication.application"%>
<%@page import="static Forms.GenForm.personalEvent"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>
      University College Cork (UCC)
    </title>
    <% 
    String userId =(String) session.getAttribute("userId");
    String name =request.getParameter("eventName");
    String date =request.getParameter("date");
    String time =request.getParameter("startTime");
    String minutes =request.getParameter("minutes");
    boolean insert = isset(name)&&isset(date)&&isset(time)&&isset(minutes);
	if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
	}
	else if(insert){
		out.write("<meta http-equiv=\"refresh\" content=\"3;url=timetable.jsp\" />");
	}
    %>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
    <link rel="stylesheet" href="css/studentStyle.css" type="text/css" />
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="css/meeting.css" type="text/css" />
    <meta charset="UTF-8" />
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.5.1.min.js">
	</script>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/jquery-ui.min.js">
	</script>
    <link href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/themes/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />
	</script> 
	
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
				<img src="images/personalHead.png" class="logo" />
				</div>
				<form id="myform" method="post" action="personalEvents.jsp">
					<% 
                            if(insert){
                            	application app = new application();
                                app.setup();
                            	app.InsertNewEvent(userId, name,date,time,minutes);
                            	out.write("<h1>Success!</h1>"+
                				"<p>Your event has successfully been created."+ 
                				"You will be redirected to your timetable in a few moments</p>");
                            }
                            else{
                            	if(isset(request.getParameter("submit"))){
                            		out.write("<p style='color:#E60000'>Please enter all inputs correctly</p>");
                            	}
                            	out.write(personalEvent());
                            }
                            %>
				</form>   
			</div>
	  
    </div>
	
  </body>
</html>