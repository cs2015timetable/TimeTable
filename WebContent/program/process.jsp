<%-- 
    Document   : process
    Created on : 25-Feb-2015, 15:30:26
    Author     : bd13
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import ="static database.ArrangeMeeting.isset"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="shortcut icon" href="http://www.ucc.ie/favicon.ico" />
	    <% 
		if(session.getAttribute("login")==null){
			out.write("<meta http-equiv=\"refresh\" content=\"url=0;login.jsp\" />");
		}
		String userId =(String) session.getAttribute( "userId" );
	    %>
	    <% 
		        String module = (String)request.getParameter("module");
	   			String users="none";
		    	if(!isset(module)){
		    		response.sendRedirect("group.jsp");
		    	}
		    	else{
			    	String module2 = module;
			        Forms.GenForm form = new Forms.GenForm();
			        users = form.createStudentList( module, userId);
		    	}
		    %>  
        <title>
            University College Cork (UCC)
        </title>

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
        <!--date picker-->
        <script type="text/javascript">//<![CDATA[
            jQuery(function($) {
                // Initialize options
                var students = [ '<% out.write(users);  %>'];
                //remove the empty string at the last index of the array students
                $.each(students, function(index, student)
                {
                    $(".sminit").each(function() {
                        var res = student.split(" ");
                        $(this).append($("<option value='"+res[2]+"'>").html(res[0] + " " + res[1] + "</option>"));
                    });
                });
                // Example 1
                $("#students1").bsmSelect({
                    addItemTarget: 'bottom',
                    animate: true,
                    highlight: true,
                    plugins: [
                        $.bsmSelect.plugins.sortable({axis: 'y', opacity: 0.5}, {listSortableClass: 'bsmListSortableCustom'}),
                        $.bsmSelect.plugins.compatibility()
                    ]
                });
            });
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
                    <img src="images/createGroup.png" class="logo" />
                </div>
                <form id="myform" method="post" action="final.jsp">
                    <input type="hidden" name="module" value="<% out.write(module); %>"/>
                    <select name="students[]" multiple="multiple" title="Choose your group members" class="sminit" id="students1">
                            
                    </select>
                    <p><input type="text" class= "validate[required,custom[email]] feedback-input" id="comment" placeholder='Enter a group name' name="info" maxlength="30" /></p>
                    <!--submit button-->
                    <div class="submit">
                        <input type="submit" value="SEND" id="button-blue" />
                        <div class="ease"></div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
