<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<%
		if(session.getAttribute("login")==null){
			//out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
		}
	%>
	<meta charset="UTF-8">
	<title>Contact Form</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
	<link href="css/style.css" rel="stylesheet">
	<link rel="stylesheet" href="css/studentStyle.css"/>
	<link rel="stylesheet" href="css/contactStyle.css"/>
</head>

<body>
<% 
		if(session.getAttribute("login") == null){
                  //  response.sendRedirect("Home Page");
		} else if(!session.getAttribute("login").equals(true)){
          //          response.sendRedirect("Home Page");
        }
	%>
<!--UCC Logo top right hand corner of page-->
      <div id="content">
         <img src="images/logo.png" class="logo"/>
      </div>
	  <!--logout button. jsp file needed here-->
	  <div id="exit">
	  <a href="index.jsp">
		<input id = 'contacthome' type="image" src="images/homeIcon.png" value="" width="14%"/>
	  </a>
	  </div>
	
	<div class="wrapper">
		<div id="main" style="padding:50px 0 0 0;">
		
		<!-- Form -->
		<form id="contact-form" action="index.jsp" method="post">
			<h3>Get in touch</h3>
			<input type="image" src="images/mail.png" value="" width="10%"/>
			<h4>Fill in the form below, and we'll get back to you within 24 hours.</h4>
			<div>
				<label>
					<span>Name: (required)</span>
					<input placeholder="Please enter your name" type="text" tabindex="1" required autofocus>
				</label>
			</div>
			<div>
				<label>
					<span>Email: (required)</span>
					<input placeholder="Please enter your email address" type="email" tabindex="2" required>
				</label>
			</div>
			<div>
				<label>
					<span>Student No: (required)</span>
					<input placeholder="Please enter your student number" type="tel" tabindex="3" required>
				</label>
			</div>
			
			<div>
				<label>
					<span>Message: (required)</span>
					<textarea placeholder="Include all the details you can" tabindex="5" required></textarea>
				</label>
			</div>
			<div>
				<button name="submit" type="submit" id="contact-submit">Send Email</button>
			</div>
		</form>
		<!-- /Form -->
		
		</div>
	</div>

	<script src="js/scripts.js"></script>
	
</body>
</html>