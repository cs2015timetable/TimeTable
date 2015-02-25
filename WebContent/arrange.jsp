<%@ page import="database.ArrangeMeeting"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<% ArrangeMeeting meeting = new ArrangeMeeting(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Arrange Meeting</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	</head>
	<body>
		<h1>Arrange a group meeting</h1>
		<% 
			
			String date=request.getParameter("date");
			String minutes=request.getParameter("minutes");
			String groupId=request.getParameter("groupId");
			if(meeting.isset(date)&&meeting.isset(groupId)&&meeting.isset(minutes)){
				out.write(meeting.formTime("88888888",groupId,date,Integer.parseInt(minutes)));
			}
			else if(meeting.isset()&&){
				
			}
			else{
				out.write(meeting.formTime("88888888"));
			}%>
	</body>
</html>
