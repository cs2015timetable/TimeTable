<%@ page import="database.ArrangeMeeting"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>
      University College Cork (UCC)
    </title>
	<!--This is a comment. scripts for multiple select-->
	
	<meta charset="UTF-8" />
	<!--multiple select css-->
	<link rel="stylesheet" type="text/css" href="css/jquery.bsmselect.css" />
	
	
    <link rel="stylesheet" href="css/studentStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/meeting.css" type="text/css" />
	<link href="multi-select.css" media="screen" rel="stylesheet" type="text/css" />
    <meta charset="UTF-8" />
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
   <!--date picker-->
   <script type="text/javascript">//<![CDATA[

    jQuery(function($) {

      // Initialize options
      var cities = ['Gene Doherty', 'Billy Downes', 'David Dowling', 'David Dunne', 'Ivor Duggan', 'Christopher Gunkel', 'Rosa Bronks',
        'James Troy', 'Denise Lucey', 'Calvin Freeman'];

      $.each(cities, function(index, city)
      {
        $(".sminit").each(function() {
          $(this).append($("<option>").html(city));
        });
      });

      

      // Example 1
      $("#cities1").bsmSelect({
        addItemTarget: 'bottom',
        animate: true,
        highlight: true,
        plugins: [
          $.bsmSelect.plugins.sortable({ axis : 'y', opacity : 0.5 }, { listSortableClass : 'bsmListSortableCustom' }),
          $.bsmSelect.plugins.compatibility()
        ]
      });
  });
  </script>
	<script type="text/javascript">

                $(document).ready(function () {
                        $("#datepicker").datepicker();
                });
   
    </script>
    
	<!--message box to alert thatmessage has been sent-->
	<script type="text/javascript"> 
			function show_alert() 
		{ 
		alert("Your meeting request has been sent"); 
	} 
	</script> 
	
  </head>
	  <body>
		<div id = "logout">
			<a href="studentHome.html"><img src="images/homeIcon.png" class="logo" /></a>
		  </div>
		<div id="content">
		  <img src="images/logo.png" class="logo" />
		</div>
	
    
		<div id="form-main">
		  <div id="form-div">
			<form class="form" id="form1">
				<div id="meetImg">
					<img src="images/meetingGroup.png" class="logo" />
				</div>
			<!--pick a date-->
			<p class="date">
				<input type="text" class= "validate[required,custom[email]] feedback-input" id="datepicker" placeholder="Choose a Date" />
			</p>
			<!--pick a time-->
			<p class="time">
				<select type="text" class="validate[required,custom[email]] feedback-input" id="time" placeholder="Choose Available Time" >
					 <%
					 	ArrangeMeeting meeting = new ArrangeMeeting();
					 			 	out.write(meeting.week("88888888",false,"11111111","2015-02-16",30));
					 %> 
				</select>
				
				
			</p>
			
			<!--option to pick a room -->
			<p class="room">
				<select type="text" class="validate[required,custom[email]] feedback-input" id= "room"  >
					<option value="" selected="selected">
					Choose available room
					</option>
				</select>
			</p>
			
			<!--option to select group multi select but will be using better approach for this-->
			<p class="city">
			
			<select type = "text"  multiple="multiple" name="cities[]" title="Select names from list" class="sminit" id="cities1">
			</select>
			</p>
			<!--option to send a comment-->
          <p class="text">
            <textarea name="text" class="validate[required,length[6,300]] feedback-input" id="comment" placeholder="Comment">
			</textarea>
          </p>
		  <!--submit button-->
          <div class="submit">
		     <input type="submit" onclick="show_alert()" value="SEND" id="button-blue" />
				<div class="ease"></div>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>