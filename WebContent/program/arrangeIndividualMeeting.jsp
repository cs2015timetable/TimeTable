<%@ page import="database.ArrangeMeeting"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% ArrangeMeeting meeting = new ArrangeMeeting(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>
      University College Cork (UCC)
    </title>
	<!--This is a comment. scripts for multiple select-->
	<% 
	boolean insert = isset(request.getParameter("inserted"));
	if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
	}
	else if(insert&&isset(request.getParameter("timeSlot"))){
		  out.write("<meta http-equiv=\"refresh\" content=\"3;url=timetable.jsp\" />");
	}
    %>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
	<meta charset="UTF-8" />
	
    <link rel="stylesheet" href="css/studentStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/meeting.css" type="text/css" />
    <meta charset="UTF-8" />
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
		  <form class="form" id="form1" action="arrangeIndividualMeeting.jsp" method="post">
				<div id="meetImg">
					<img src="images/meetingIndividual.png" class="logo" />
				</div>
		<% 
		if(session.getAttribute("login")!=null){
			String userId =(String) session.getAttribute( "userId" );
			String date=request.getParameter("date");
			String minutes=request.getParameter("minutes");
			String userId2=request.getParameter("userId");
			if(!isset(date)||!isset(userId)||!isset(minutes)){
				if(isset(request.getParameter("submit"))){
            		out.write("<p style='color:#E60000'>Please enter all inputs correctly</p>");
            	}
				out.write(meeting.formTime(userId,false));
			}
			else{
				String startMinutes=request.getParameter("timeSlot");
				String info =request.getParameter("info");
				int mins =Integer.parseInt(minutes);
				String week=request.getParameter("week");
				if(isset(startMinutes)&&isset(info)){
					meeting.insertMeeting(userId,false, userId2, date, startMinutes, mins, info);
					out.write("<h1>Success!</h1>"+
					"<p>Your meeting has been successfully arranged."+ 
					"You will be redirected to your updated timetable in a few moments</p>");
				}
				else{
					if(insert){
						out.write("<p style='color:#E60000'>Please enter all inputs correctly</p>");
					}
					out.write(meeting.formTime(userId,false,isset(week),userId2,date,mins));
				}
			}
		}%>
	  </form>
	 </div>
    </div>
  </body>
</html>