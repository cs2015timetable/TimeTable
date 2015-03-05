<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=Edge;chrome=1" />

	<title>Campus Map</title>
	<%
	if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=../login.jsp\" />");
	}
	%>
	<link rel="stylesheet" href="css/example.css" />
	<link rel="stylesheet" href="css/pygments.css" />
	<link rel="stylesheet" href="css/easyzoom.css" />
	<link rel="stylesheet" href="css/studentStyle2.css"/>

	

</head>
<header>

</header>
<body>
 <div id="content">
         <img src="images/logo.png" class="logo"/>
      </div>
	  <!--logout button. jsp file needed here-->
	  <div id="exit">
	  <a href="../index.jsp">
		<input type="image" src="images/homeIcon.png" value="Logout" width="10%"/>
	  </a>
	  </div>

	<div class="container">

		<!-- Introduction -->
		

		

		

		<hr />

		<!-- Example -->
		<section id="example">

			<h1>
				University College Cork Map
			</h1>

			<p>
				Use your mouse cursor or finger to zoom and pan the images below.
			</p>

			<h2>
				Map of University Campus
			</h2>

			<div class="easyzoom easyzoom--overlay">
				<a href="example-images/1_zoom.jpg">
					<img src="example-images/1_standard.jpg" alt="Map of UCC" width="100%" />
				</a>
			</div>
			

			<p style="text-align: center;">
				<small>Photo by: University College Cork</small>
			</p>

			<h2>
				Index for Campus Map
			</h2>

			<div class="easyzoom easyzoom--overlay">
				<a href="example-images/index_zoom.jpg">
					<img src="example-images/index_standard.jpg" alt="Index for map of UCC" width="100%" />
				</a>
			</div>
			

			<p style="text-align: center;">
				<small>Photo by: University College Cork</small>
			</p>
			

		
	</div>

	

	<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="dist/easyzoom.js"></script>
	<script>
		// Instantiate EasyZoom plugin
		var $easyzoom = $('.easyzoom').easyZoom();

		// Get the instance API
		var api = $easyzoom.data('easyZoom');
	</script>

</body>
</html>