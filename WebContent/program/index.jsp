<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Forms.GenForm"%>
<html>
   <head>
      <title>University College Cork (UCC)</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
      <link rel="stylesheet" href="css/studentStyle.css"/>
	  <% 
		if(session.getAttribute("login")==null){
			out.write("<meta http-equiv=\"refresh\" content=\"0;login.jsp\" />");
		}
		String userId =(String) session.getAttribute( "userId" );
		 Forms.GenForm form = new Forms.GenForm();
		%>
      <meta charset="UTF-8"/>
      <script type="text/javascript" src="<http://code.jquery.com/jquery-1.7.2.min.js>"></script> 
      <script type="text/javascript" src="script.js"></script> 
	  <style>
    .black_overlay{
        display: none;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 100%;
        background-color: black;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
	}
    .white_content {
        display: none;
        position: absolute;
        top: 25%;
        left: 25%;
        width: 50%;
        height: 50%;
        padding: 16px;
        border: 16px solid orange;
        background-color: white;
        z-index:1002;
        overflow: auto;
		color: black;
    }
</style>
   </head>
   
   <body>
   

	  <!--UCC Logo top right hand corner of page-->
      <div id="content">
         <img src="images/logo.png" class="logo"/>
      </div>
	  <!--logout button. jsp file needed here-->
	  <div id="exit">
	  <a href="../logout.jsp">
		<input type="image" src="images/LogoutIcon.png" value="Logout" width="10%"/>
	  </a>
	  </div>
	  <!--Student logo top center of page-->
      <div id="head" >
         <img  src="images/headImage.png" width = "45%" />
      </div>
	  
	  <!--Main tile menu layout-->
      <div class="menu">
		 <!--first row of options-->
         <div class="row">           
            
			<!--Arrange a group meeting option-->
            <div class="tile blue">
               <a href="arrangeGroupMeeting.jsp" class="btn" id="one">
               <img src="images/meetingBlue.png" class="ribbon3" title="Arrange a meeting"/>
               </a>
            </div>
			<!--View and alter your timetable option-->
            <div class="tile tileLarge red">
               <a href="timetable.jsp" class="btn" id="one">
               <img src="images/timetable.png" class="ribbon4" title="View Timetable"/>
               </a>
            </div>
			<!--UCC home page link-->
            <div class="tile gold"> 
               <a href="personalEvents.jsp" class="btn" id="one"> 
               <img src="images/personalEvent.png" class="ribbon4" title="Personal Events"/> 
               </a>
            </div>
			<!--Option to cancel a meeting-->
            <div class="tile tileLarge green">
				<a href="meetingCancel.jsp" class="btn" id="one">
            <img src="images/cancel.png" class="ribbon5" title="Cancel a meeting"/> 
            </a>
            </div>
			<!--Option to create a group-->
		 <div class="tile red">
				<a href="group.jsp" class="btn" id="one">
					<img src="images/createGroupIcon.png" class="ribbon4" title="Create a group"/> 
				</a>
				
            </div>
			
         </div>
		 
		 <!--Second row of options-->
         <div class="row">
			<!--option to arrange one to one meeting-->
            <div class="tile tileLarge gold">
               <a href="arrangeIndividualMeeting.jsp" class="btn" id="one">
               <img src="images/oneMeet.png" class="ribbon3" title="One to one meeting"/> 
               </a>
            </div>
			
			<!--Help option-->
            <div class="tile green">
               <a "javascript:void(0)" onclick = "document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'" class="btn" id="one">
               <img src="images/helpIcon.png" class="ribbon2" title="Help Section"/> 
               </a>
			   <div id="light" class="white_content"><h3><u>How to use:</u></h3>
                                        <h3>How to create a group:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/GKWE5ElJT94">
                                        </object>
                                       
                                        <h3>How to create a group meeting:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/6W_B1FXuSsE">
                                        </object>
                                       
                                        <h3>How to view your timetable:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/fpaFIYXwk9o">
                                        </object>
                                       
                                        <h3>Arranging a 1 to 1 meeting:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/CMQukCdhjIc">
                                        </object>
                                       
                                        <h3>How to view the campus map:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/Xf8oj5O9M_4">
                                        </object>
                                       
                                        <h3>How to create a personal event:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/wjnjA4eJLfQ">
                                        </object>
                                       
                                        <h3>Cancel a meeting:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/u_jHdoJ7RKs">
                                        </object>
                                       
                                        <h3>Viewing Notifications:</h3>
                                        <object id='youtube' width="420" height="315"
                                        data="https://www.youtube.com/embed/MkYHH2uXx1c">
                                        </object>
                                       
                                        <p></p>
					
					
			<a href = "javascript:void(0)" onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">Close</a></div>
				<div id="fade" class="black_overlay"></div>
			</div>
			<!--Contact page for phone numbers etc.-->
            <div class="tile blue">
               <a href="contact.jsp" class="btn" id="one">
                  <img src="images/contact.png" class="ribbon2" title="Contact Page"/> 
            </div>
			<!--Campus map for university-->
            <div class="tile red">
				<a href="map/index.jsp" class="btn" id="one">
					<img src="images/mapIcon.png" class="ribbon3" title="UCC Campus Map"/> 
				</a>
				
            </div> 
			<!--Options to view notifications of requested events-->
            <div class="tile tileLarge green"> 
				<%=form.checkNotifications(userId)%>.png' class="ribbon6" title="Check Your Meetings notifications"/> 
				</a>
            </div>
         </div>
		 
		 <!--Third row of options currently but to add extra features if needed-->
         <!-- <div class="row">
            <div class="tile gold">
            </div>
            <div class="tile green"> 
            </div>
            <div class="tile red">
            </div>
            <div class="tile tileLarge green">
            </div>
            <div class="tile blue">
            </div>
            <div class="tile green"> 
            </div> -->
         </div> 
      </div>
	  
    
    <!--<footer>
		<p>Created by: Group5 Design Team</p>
		
	</footer> --> 
   </body>
</html>

