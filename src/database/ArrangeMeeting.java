package database;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * 	Runs a given insert query
 *	@author gene
 */
public class ArrangeMeeting {
	public ArrangeMeeting(){
		app = new Application();
		app.setup();
	}
	private Application app;  
	//times in minutes so 540=540/60=9(o clock)
	 /**
     * 	Returns free time slots for given users on a certain date of a certain length
     * 	returns free times in format {startTime, EndTime}
     *	@param users A preformatted string of users who will attend the meeting
     *	@param date	The date
     *	@param minutes The length of the meeting required
     */
	public ArrayList<Integer[]> freeTimesDay(String users,  String date, int minutes){
		String query = "SELECT TIME_TO_SEC(start_time)/60, (TIME_TO_SEC(end_time)-TIME_TO_SEC(start_time))/60 AS length, sum(weight) AS conflicts FROM ( "+
        		"SELECT start_time, end_time, 3 AS weight FROM lecture "+
        		"WHERE module_id IN(SELECT mo.module_id "+
        		"FROM module_of AS mo "+
        		"JOIN users AS u "+
        		"ON mo.course_id = u.course_id "+
        		"AND mo.course_year= u.year "+
        		"WHERE u.user_id IN ("+users+") AND mo.mandatory = true UNION ALL "+
        		"SELECT module_id  "+
        		"FROM takes_module "+
        		"WHERE user_id IN ("+users+") UNION ALL "+
        		"SELECT module_id  "+
        		"FROM lectures "+
        		"WHERE user_id IN ("+users+") "+
        		") AND (lecture_id IN (SELECT lecture_id FROM lecture_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+
        		"UNION ALL "+
        		"SELECT start_time, end_time, 3 AS weight FROM lab "+
        		"WHERE module_id IN("+
	        	"SELECT module_id FROM users_modules WHERE user_id IN ("+users+")"+
        		") AND (lab_id IN (SELECT lab_id FROM lab_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, end_time, 1 AS weight FROM personal_events "+
        		"WHERE creator_id IN ("+users+") "+
        		"AND (event_id IN (SELECT event_id FROM personal_event_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, end_time, 2 AS weight FROM individual_meeting "+
        		"WHERE (user_id IN ("+users+") OR user_id_2 IN ("+users+")) "+
        		"AND (meeting_id IN (SELECT meeting_id from individual_meeting_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, end_time, 2 AS weight FROM group_meeting AS gm "+
        		"JOIN in_group AS ig "+
        		"ON gm.group_id = ig.group_id "+
        		"WHERE ig.user_id IN ("+users+") "+
        		"AND (meeting_id IN (SELECT meeting_id from group_meeting_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+
        		") AS events " +
        		"GROUP BY start_time, length;";
    	ArrayList<String[]> conflicts = app.select(query);
		ArrayList<Integer[]> times = new ArrayList<Integer[]>();
		int endTime=1080;
		int weight = 0;
		if(conflicts==null){
			return null;
		}
		while(times.size()<1){
			Iterator<String[]> iterator = conflicts.iterator();
			//initialized to start of day minutes in case no 
			//events in the day
			int lastEndTime = 540;
			int startTime;
			int length;
			while(iterator.hasNext()){
				String[] currentRow = iterator.next();
				try{
					if(weight<Integer.parseInt(currentRow[2])){
						startTime = (int)Double.parseDouble(currentRow[0]);
						length = (int)Double.parseDouble(currentRow[1]);
						for(int i = lastEndTime;i<startTime;i+=minutes){
							if(startTime-i>=minutes){
								Integer[] meetingTime = {i, i+minutes};
								times.add(meetingTime);
							}
						}
						lastEndTime = startTime+length;
					}
				}catch (NumberFormatException e) {
					
				}
			}
			if(endTime-lastEndTime>=minutes){
				for(int i = lastEndTime;i<endTime;i+=minutes){
					if(endTime-i>=minutes){
						Integer[] meetingTime = {i, i+minutes};
						times.add(meetingTime);
					}
				}
			}
			weight++;
		}
		return times;
	}
	 /**
     * 	Returns free time slots for given users during a certain week of a certain length
     * 	returns free times in format {startTime, EndTime}
     *	@param users A preformatted string of users who will attend the meeting
     *	@param date	The Monday of the week
     *	@param minutes The length of the meeting required
     */
	public ArrayList<Integer[]> freeTimesWeek(String users,  String date, int minutes){
		 String query = "SELECT TIME_TO_SEC(start_time)/60, (TIME_TO_SEC(end_time)-TIME_TO_SEC(start_time))/60 AS length, sum(weight) AS conflicts, weekday FROM ( "+
	        		"SELECT start_time, end_time, 3 AS weight, DAYOFWEEK(date) AS weekday FROM lecture "+
	        		"WHERE module_id IN("+
	        		"SELECT module_id FROM users_modules WHERE user_id IN ("+users+")"+
	        		") AND (lecture_id IN (SELECT lecture_id FROM lecture_recurring WHERE date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) "
	        				+ "AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		"OR date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		"UNION ALL "+
	        		"SELECT start_time, end_time, 3 AS weight, DAYOFWEEK(date) AS weekday FROM lab "+
	        		"WHERE module_id IN("+
	        		"SELECT module_id FROM users_modules WHERE user_id IN ("+users+")"+
	        		") AND (lab_id IN (SELECT lab_id FROM lab_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, end_time, 1 AS weight, DAYOFWEEK(date) AS weekday FROM personal_events "+
	        		"WHERE creator_id IN ("+users+") "+
	        		"AND (event_id IN (SELECT event_id FROM personal_event_recurring WHERE date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) "
	        				+ "AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		"OR date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, end_time, 2 AS weight, DAYOFWEEK(date) AS weekday FROM individual_meeting "+
	        		"WHERE (user_id IN ("+users+") OR user_id_2 IN ("+users+")) "+
	        		"AND (meeting_id IN (SELECT meeting_id from individual_meeting_recurring WHERE date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) "
	        				+ "AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		"OR date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, end_time, 2 AS weight, DAYOFWEEK(date) AS weekday FROM group_meeting AS gm "+
	        		"JOIN in_group AS ig "+
	        		"ON gm.group_id = ig.group_id "+
	        		"WHERE ig.user_id IN ("+users+") "+
	        		"AND (meeting_id IN (SELECT meeting_id from group_meeting_recurring WHERE date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) "
	        				+ "AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		"OR date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) "+
	        		") AS events " +
	        		"GROUP BY weekday, start_time, length;";
	    ArrayList<String[]> conflicts = app.select(query);
		ArrayList<Integer[]> times = new ArrayList<Integer[]>();
		int endTime=1080;
		int weight = 0;
		if(conflicts==null){
			return null;
		}
		while(times.size()<1){
			Iterator<String[]> iterator = conflicts.iterator();
			//initialized to start of day minutes in case no 
			//events in the day
			int lastEndTime = 540;
			int startTime;
			int length;
			//monday
			int curDay=2;
			while(iterator.hasNext()){
				String[] currentRow = iterator.next();
				int day =Integer.parseInt(currentRow[3]);
				if(curDay<day){
					for(;curDay<day;curDay++){
						if(endTime-lastEndTime>=minutes){
							for(int j = lastEndTime;j<endTime;j+=minutes){
								if(endTime-j>=minutes){
									Integer[] meetingTime = {curDay,j, j+minutes};
									times.add(meetingTime);
								}
							}
						}
						lastEndTime = 540;
					}
				}
				if(weight<Integer.parseInt(currentRow[2])){
					startTime = (int)Double.parseDouble(currentRow[0]);
					length = (int)Double.parseDouble(currentRow[1]);
					for(int i = lastEndTime;i<startTime;i+=minutes){
						if(startTime-i>=minutes){
							Integer[] meetingTime = {curDay,i, i+minutes};
							times.add(meetingTime);
						}
					}
					lastEndTime = startTime+length;
				}
			}
			for(;curDay<7;curDay++){
				if(endTime-lastEndTime>=minutes){
					for(int j = lastEndTime;j<endTime;j+=minutes){
						if(endTime-j>=minutes){
							Integer[] meetingTime = {curDay,j, j+minutes};
							times.add(meetingTime);
						}
					}
				}
				lastEndTime = 540;
			}
			weight++;
		}
		return times;
	}
	 /**
     * 	Returns the query needed to find all members of a group
     *	@param groupId The groups id
     */
	public String groupString(String groupId){
		return "SELECT user_id FROM in_group "
				+ "WHERE group_id = "+groupId;
	}
	 /**
     * 	Returns html output for a selectbox of free time during a week
     *	@param user The user id
     *	@param group Is it a group meeting
     *	@param groupOrUser The group or user id
     *	@param date	The date
     *	@param minutes The length of the meeting
     */
	public String weekSelect(String user, Boolean group, String groupOrUser, String date,int minutes){
		String users = group?groupString(groupOrUser):user+","+groupOrUser;
		ArrayList<Integer[]> results = freeTimesWeek(users,date, minutes);
		Iterator<Integer[]> it = results.iterator();
		String result = "";
		while(it.hasNext()){
			Integer[] times = it.next();
			String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
			int start = times[1];
			int end = times[2];
			result +="<option value = \""+times[0]+","+start+"\">"+days[times[0]-2]+":"+timeString(start)+"-"+timeString(end)+"</option>";
		}
		return result;
	}
	public String daySelect(String user, Boolean group, String groupOrUser, String date,int minutes){
		String users = group?groupString(groupOrUser):user+","+groupOrUser;
		ArrayList<Integer[]> results = freeTimesDay(users,date, minutes);
		Iterator<Integer[]> it = results.iterator();
		String result = "";
		while(it.hasNext()){
			Integer[] times = it.next();
			int start = times[0];
			int end = times[1];
			result +="<option value = \""+start+"\">"+timeString(start)+"-"+timeString(end)+"</option>";
		}
		return result;
	}
	/**
     * 	Returns a formatted string for a given minute of the day in format HH:MM
     *	@param minutes The minute of the day
     */
	public static String timeString(int minutes){
		return (minutes-(minutes%60))/60+":"+(minutes%60<10?"0":"")+minutes%60;
	}
	/**
     * 	Returns html output for a selectbox of groups a user is part of
     *	@param userId The users id
     */
	public String groupSelect(String userId){
		String result="";
		String query = "SELECT g.group_id, g.group_name FROM groups AS g"
				+ " JOIN  in_group AS ig ON ig.group_id = g.group_id WHERE ig.user_id = "+userId+";";
		ArrayList<String[]> results = app.select(query);
		if(results.size()>0){
			result+="<select class= \"validate[required,custom[email]] feedback-input\" id=\"name\" name=\"groupId\">"+
					"<option value='' selected='selected'>"+
					"Select group:"+
					"</option>";
			Iterator<String[]> it = results.iterator();
			while(it.hasNext()){
				String[] row = it.next();
				result+="<option value=\""+row[0]+"\">"+row[1]+"</option>";
			}
			result+="</select>";
		}else{
			result+="<div class= \"validate[required,custom[email]] feedback-input\" id=\"name\"><a href=\"group.jsp\">Create a group</a></div>";
		}
		return result;
	}/**
     * 	Returns html output for a selectbox of groups a user is part of
     *	@param userId The users id
     */
	public String userSelect(String userId){
		String result="";
		String query = "SELECT user_id, CONCAT(first_name, ' ', last_name) FROM users "+
						"WHERE user_id IN ("+
						//student same course and year
						"SELECT user_id FROM users WHERE course_id = (SELECT course_id FROM users WHERE user_id = "+userId+") AND"+
						"  year = (SELECT year FROM users WHERE user_id = "+userId+") UNION "+
						//select lecturers for students modules
						"SELECT user_id FROM lectures "+
						"WHERE module_id IN ("+
						"SELECT module_id "+
		        		"FROM module_of "+
		        		"WHERE course_id = (SELECT course_id FROM users WHERE user_id = "+userId+") "+
		        		"AND course_year= (SELECT year FROM users WHERE user_id = "+userId+") "+
		        		"AND mandatory = true UNION ALL "+
		        		"SELECT module_id  "+
		        		"FROM takes_module "+
		        		"WHERE user_id ="+userId+") "+
		        		" UNION "+	
		        		//students doing module user teachs			
		        		"SELECT user_id FROM takes_module "+
		        		"WHERE module_id IN ("+
		        		"SELECT module_id from lectures WHERE user_id = "+userId+"))"+
		        		"AND user_id <>"+userId+";";
		ArrayList<String[]> results = app.select(query);
		if(results.size()>0){
			result+="<select class= \"validate[required,custom[email]] feedback-input\" id=\"name\" name=\"userId\">"+
					"<option value='' selected='selected'>"+
					"Select user:"+
					"</option>";
			Iterator<String[]> it = results.iterator();
			while(it.hasNext()){
				String[] row = it.next();
				result+="<option value=\""+row[0]+"\">"+row[1]+"</option>";
			}
			result+="</select>";
		}else{
			result+="<div class= \"validate[required,custom[email]] feedback-input\" id=\"name\">No other students/lecturers found</div>";
		}
		return result;
	}
	/**
     * 	Returns html output for the content of the form to arrange a meeting given a users id
     *	@param userId the users id
     */
	public String formTime(String userId, boolean group){
		String result = "<p>"
			+"<input class= \"validate[required,custom[email]] feedback-input\" id=\"datepicker\" type=\"date\" name=\"date\">"
		+"</p>"
		+"<p>"
			+(group?groupSelect(userId):userSelect(userId))
			+"</p>"
		+"<p>"
			+"<select class= \"validate[required,custom[email]] feedback-input\" name=\"minutes\" id=\"time\">"+
			"<option value='' selected='selected'>"+
			"Length:"+
			"</option>";
		for(int i =10;i<=120;i+=10){
			result+="<option value=\""+i+"\">"+i+"</option>";
		}
		result+="</select>"
				+ "</p>"
				+ "<p>Search whole week for time slots?<input class= \"validate[required,custom[email]] feedback-input\"  style=\"zoom:1.2\" type=\"checkbox\" name=\"week\" value=\"true\"></p>"
				+ "<div class=\"submit\">"
				+ "<input type=\"submit\" onclick=\"show_alert()\" value=\"SEND\" name=\"submit\" id=\"button-blue\" />"
				+ "<div class=\"ease\"></div>"
				+ "</div>";
		return result;
	}
	/**
     * 	Returns html output for the content of the form to arrange a meeting given a users id and input data
     *	@param userId The users id
     *	@param groupId The groups Id
     *	@param date The date 
     *	@param minutes The length of the meeting required
     */
	public String formTime(String userId,boolean group,boolean week,String groupId,String date,int minutes){
		String result = "<input type=\"hidden\" name=\"date\" value=\""+date+ "\">"
				+"<input type=\"hidden\" name=\"minutes\" value=\""+minutes+ "\">"
						+"<input type=\"hidden\" name=\""+(group?"groupId":"userId")+"\" value=\""+groupId+ "\">"
			+"<p><select class= \"validate[required,custom[email]] feedback-input\" id=\"time\" name=\"timeSlot\">"+
			"<option value='' selected='selected'>"+
			"Select time:"+
			"</option>"
				+(week?weekSelect(userId, group, groupId, date, minutes):daySelect(userId, group, groupId, date, minutes))
				+"</select></p>"
						+ "<input type=\"text\" class= \"validate[required,custom[email]] feedback-input\" "
						+ "id=\"comment\" name=\"info\" placeholder='Enter a meeting description' maxlength=30/></p>"
						+ "<div class=\"submit\">"
						+ "<input type=\"hidden\" name=\"inserted\" value=\"true\" />"
								+ "<input type=\"submit\" onclick=\"show_alert()\" name=\"submit\" value=\"SEND\" id=\"button-blue\" />"
								+ "<div class=\"ease\"></div>"
								+ "</div>";
		return result;
	}

	/**
     * 	Tests whether a String variable is set
     *	@param string A string
     */
	public static boolean isset(String string){
		return string!=""&&string!=null;
	}

	/**
     * 	Inserts the newly arranged meeting into the database
     *	@param userID the users id
     *	@param groupId The groups Id
     *	@param date The date 
     *	@param startMins The starting minute of the meeting and day of week if user selected to search a whole week (in format weekday,mins)
     *	@param minutes The length of the meeting required
     *	@param info Any additional information given by the user
     */
	public void insertMeeting(String userId, boolean group, String groupId, String date, String startMins, int minutes, String info){
		String[] dayMins = startMins.split(",");
		int correctStartMins;
		String correctDate;
		if(dayMins.length==2){
			correctStartMins = Integer.parseInt(dayMins[1]);
			correctDate="DATE_ADD('"+date+"', INTERVAL ("+dayMins[0]+"-DAYOFWEEK('"+date+"')) DAY)";
		}
		else{
			correctStartMins = Integer.parseInt(dayMins[0]);
			correctDate="'"+date+"'";
		}
		String query = "INSERT into "+(group?"group_meeting":"individual_meeting")+" (user_id,"+(group?"group_id":"user_id_2")+",date, start_time, "
				+ "end_time, recurring, confirmed,room_id, other) VALUES ("+userId+", "+groupId+", "
				+ correctDate+", '"+timeString(correctStartMins)+"', '"+timeString(correctStartMins+minutes)+"', false, false,'g01', '"+info+"');";
				
		app.insert(query);
		//Notifucation Query
        String notify = "INSERT INTO notifications" + 
                        "(`type`, user_id, description, itemId)" + 
                        " VALUES";
        if(group){
            String membersQuery = "SELECT user_id" +
                            " FROM in_group" +
                            " WHERE group_id = "+groupId+
                            " AND user_id<>"+userId+";";
            
            ArrayList<String> array = app.selectDB(membersQuery, "user_id");
            // get the index of final variable
            for( Object a : array){
                notify += " ('meeting', "+a+", 'A meeting has been arranged for "+date+"', "+groupId+"),";
            }
            notify += " ('meeting', "+userId+", 'You arranged a meeting for "+date+"', "+groupId+");";
        //if it an individual meeting there is no need to 
        }else{
            notify += " ('meeting', "+userId+", 'You arranged a meeting for "+date+"', "+groupId+"),"
            		+ "('meeting', "+groupId+", 'A meeting has been arranged for "+date+"', "+groupId+");";
        }
        app.insert(notify);
	}
}
	
