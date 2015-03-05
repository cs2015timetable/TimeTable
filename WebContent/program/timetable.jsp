<%@ page import="database.Timetable"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
	<% 
	if(session.getAttribute("login")==null){
		out.write("<meta http-equiv=\"refresh\" content=\"0;url=login.jsp\" />");
	}
	Timetable timetable= new Timetable();
    String userId =(String) session.getAttribute( "userId" );
	  %>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
	<title>Timetable</title>
	<link rel="stylesheet" href="css/studentStyle.css"/>
	  
	  <link rel="stylesheet" href="css/event.css"/>
</head>
	<script src="calendar/codebase/dhtmlxscheduler.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="calendar/codebase/dhtmlxscheduler_glossy.css" type="text/css" media="screen" title="no title" charset="utf-8">
	<script src="calendar/codebase/ext/dhtmlxscheduler_editors.js" type="text/javascript" charset="utf-8"></script>
	<script src="calendar/codebase/ext/dhtmlxscheduler_minical.js" type="text/javascript" charset="utf-8"></script>

	<!-- Davids -->
	<script src="codebase/connector/connector.js"></script>
	<!-- -->
	
<style type="text/css" media="screen">
	html, body{
		padding:20px;
		height:90%;
		overflow:hidden;
	}  
	#timetableMain{
		margin:20px;
	}
</style>



	<script type="text/javascript" charset="utf-8">
		function init() {
		
			scheduler.config.multi_day = true;
			var pizza_size = [
				{ key: 1, label: 'Small' },
				{ key: 2, label: 'Medium' },
				{ key: 3, label: 'Large' }
			];

			scheduler.locale.labels.section_text = 'Text';
			scheduler.locale.labels.section_checkbox = 'Checkbox';
			scheduler.locale.labels.section_radiobutton = 'Radiobutton';
			scheduler.locale.labels.section_select = 'Select';
			scheduler.locale.labels.section_template = 'Template';

			
			
	
		scheduler.config.limit_time_select = true;
		scheduler.config.first_hour =9;
		scheduler.config.last_hour =19;
		
		<!--example of adding an event-->	
		scheduler.init('scheduler_here',new Date(2015,1,17),"week");
		
		<!-------------------------- -->
		
		<!-- Davids -->

		scheduler.parse(<% out.write(timetable.xhtmlString( userId));%>, "json");
		
		<!---------End Data ------------------------->
		
		scheduler.config.multi_day = true;
		scheduler.config.mark_now = true;
		scheduler.config.xml_date="%Y-%m-%d %H:%i";
		scheduler.init('scheduler_here',new Date(),"week");

		}
		<!--if this is set to true it makes it uneditable but also makes everything uneditable-->
		scheduler.config.readonly = true;
	</script>
<body onload="init();">
<section id="timetableMain">
	<input type="hidden" name="json" value="English" id="hello" />

	<div id="content">
	    <img src="images/logo.png" class="logo" />
			<div id="event">
				<img src="images/TimeHead.png" class="logo" />
			</div>
	</div>
	
	<div id = "logout">
		<a href="index.jsp"><img src="images/homeIcon.png" class="logo" /></a>
	</div>
	
	<div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:465px;'>
	<div class="dhx_cal_navline">
		<div class="dhx_cal_prev_button">&nbsp;</div>
		<div class="dhx_cal_next_button">&nbsp;</div>
		<div class="dhx_cal_today_button"></div>
		<div class="dhx_cal_date"></div>
		<div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div>
		<div class="dhx_cal_tab" name="week_tab" style="right:140px;"></div>
		<div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
	</div>
	<div class="dhx_cal_header">
	</div>
	<div class="dhx_cal_data">
	</div>
</div >
</section>
</body>
</html>