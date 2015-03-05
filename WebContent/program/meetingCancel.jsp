<%@page import="authentication.application"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  	<%
		String id =(String) request.getParameter("id");
		if(session.getAttribute("login")==null){
			out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
		}
		else if(isset(id)){
			out.write("<meta http-equiv=\"refresh\" content=\"3;url=timetable.jsp\" />");
		}
	    String userId =(String) session.getAttribute( "userId" );
	   %>
    <title>
      University College Cork (UCC)
    </title>
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
    <link href="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.8.10/themes/ui-lightness/jquery-ui.css"rel="stylesheet" type="text/css" />
    
  </head>
	<body>
		<div id="logout">
		<a href="index.jsp"><img src="images/homeIcon.png" class= "logo" /></a>
		</div>
		
		<div id="content">
			<img src="images/logo.png" class="logo" />
		</div>
    
	<div id="form-main">
			<div id="form-div">
				<div id="meetImg">
				<img src="images/cancelScheduled.png" class="logo" />
				</div>
				<form id="myform" method="post" action="meetingCancel.jsp">
			
				<%
	                application app = new application();
                	app.setup();
					String submit =(String) request.getParameter("submit");
	                if(isset(id)){
	                	app.deleteMeeting(id);
	                	out.write("<h1>Success!</h1>"+
	    						"<p>Your meeting has been successfully deleted."+ 
	    						"You will be redirected to your updated timetable in a few moments</p>");
	                }
	                else{
	                	if(isset(request.getParameter("submit"))){
	                		out.write("<p style='color:#E60000'>Please select a meeting to cancel</p>");
	                	}
						out.write(app.cancelMeetingForm(userId));
	                }
				%>
        </form>
      </div>
    </div>
  </body>
</html>