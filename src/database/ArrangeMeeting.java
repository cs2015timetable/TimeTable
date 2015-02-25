package database;
import java.util.ArrayList;
import java.util.Iterator;

public class ArrangeMeeting {
	public ArrangeMeeting(){
		app = new Application();
		app.setup();
	}
	private Application app;  
	//returns free times in format {startTime, EndTime}
	//times in minutes so 540=540/60=9(o clock)
	public ArrayList<Integer[]> freeTimesDay(String users,  String date, int minutes){
		String query = "SELECT start_time*60/10000, length*60, sum(weight) AS conflicts FROM ( "+
        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight FROM lecture "+
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
        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight FROM lab "+
        		"WHERE module_id IN(SELECT mo.module_id  "+
        		"FROM module_of AS mo  "+
        		"JOIN users AS u  "+
        		"ON mo.course_id = u.course_id "+
        		"AND mo.course_year= u.year "+
        		"WHERE u.user_id IN ("+users+") AND mo.mandatory = true UNION ALL "+
        		"SELECT module_id  "+
        		"FROM takes_module "+
        		"WHERE user_id IN ("+users+")UNION ALL "+
        		"SELECT module_id  "+
        		"FROM lectures "+
        		"WHERE user_id IN ("+users+") "+
        		") AND (lab_id IN (SELECT lab_id FROM lab_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, (end_time - start_time)/10000 AS length, 1 AS weight FROM personal_events "+
        		"WHERE creator_id IN ("+users+") "+
        		"AND (event_id IN (SELECT event_id FROM personal_event_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, (end_time - start_time)/10000 AS length, 2 AS weight FROM individual_meeting "+
        		"WHERE (user_id IN ("+users+") OR user_id_2 IN ("+users+")) "+
        		"AND (meeting_id IN (SELECT meeting_id from individual_meeting_recurring WHERE date = '"+date+"') "+
        		"OR date = '"+date+"') "+

        		"UNION ALL "+

        		"SELECT start_time, (end_time - start_time)/10000 AS length, 2 AS weight FROM group_meeting AS gm "+
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
	//returns free times in format {dayOfweek, startTime, EndTime}
	//times in minutes so 540=540/60=9(o clock)
	public ArrayList<Integer[]> freeTimesWeek(String users,  String date, int minutes){
		 String query = "SELECT start_time*60/10000, length*60, sum(weight) AS conflicts, weekday FROM ( "+
	        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight, DAYOFWEEK(date) AS weekday FROM lecture "+
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
	        		") AND (lecture_id IN (SELECT lecture_id FROM lecture_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"UNION ALL "+
	        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight, DAYOFWEEK(date) AS weekday FROM lab "+
	        		"WHERE module_id IN(SELECT mo.module_id  "+
	        		"FROM module_of AS mo  "+
	        		"JOIN users AS u  "+
	        		"ON mo.course_id = u.course_id "+
	        		"AND mo.course_year= u.year "+
	        		"WHERE u.user_id IN ("+users+") AND mo.mandatory = true UNION ALL "+
	        		"SELECT module_id  "+
	        		"FROM takes_module "+
	        		"WHERE user_id IN ("+users+")UNION ALL "+
	        		"SELECT module_id  "+
	        		"FROM lectures "+
	        		"WHERE user_id IN ("+users+") "+
	        		") AND (lab_id IN (SELECT lab_id FROM lab_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, (end_time - start_time)/10000 AS length, 1 AS weight, DAYOFWEEK(date) AS weekday FROM personal_events "+
	        		"WHERE creator_id IN ("+users+") "+
	        		"AND (event_id IN (SELECT event_id FROM personal_event_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, (end_time - start_time)/10000 AS length, 2 AS weight, DAYOFWEEK(date) AS weekday FROM individual_meeting "+
	        		"WHERE (user_id IN ("+users+") OR user_id_2 IN ("+users+")) "+
	        		"AND (meeting_id IN (SELECT meeting_id from individual_meeting_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+

	        		"UNION ALL "+

	        		"SELECT start_time, (end_time - start_time)/10000 AS length, 2 AS weight, DAYOFWEEK(date) AS weekday FROM group_meeting AS gm "+
	        		"JOIN in_group AS ig "+
	        		"ON gm.group_id = ig.group_id "+
	        		"WHERE ig.user_id IN ("+users+") "+
	        		"AND (meeting_id IN (SELECT meeting_id from group_meeting_recurring WHERE date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
	        		"OR date BETWEEN '"+date+"' AND DATE_ADD('"+date+"', INTERVAL +4 DAY)) "+
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
					curDay=day;
					lastEndTime = 540;
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
			if(endTime-lastEndTime>=minutes){
				for(int i = lastEndTime;i<endTime;i+=minutes){
					if(endTime-i>=minutes){
						Integer[] meetingTime = {curDay,i, i+minutes};
						times.add(meetingTime);
					}
				}
			}
			weight++;
		}
		return times;
	}
	public String groupString(String groupID){
		return "SELECT user_id FROM in_group "
				+ "WHERE group_id = "+groupID;
	}
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
	
	public String timeString(int minutes){
		return (minutes-(minutes%60))/60+":"+(minutes%60<10?"0":"")+minutes%60;
	}
	public String groupSelect(String user_id){
		String result="Group: ";
		String query = "SELECT g.group_id, g.group_name FROM groups AS g"
				+ " JOIN  in_group AS ig ON ig.group_id = g.group_id WHERE ig.user_id = "+user_id+";";
		ArrayList<String[]> results = app.select(query);
		if(results.size()>0){
			result+="<select name=\"groupId\">";
			Iterator<String[]> it = results.iterator();
			while(it.hasNext()){
				String[] row = it.next();
				result+="<option value=\""+row[0]+"\">"+row[1]+"</option>";
			}
			result+="</select>";
		}else{
			result+="<a href=\"creategroup.jsp\">Create a group</a>";
		}
		return result;
	}
	public String formTime(String userId){
		String result = "<form action=\"arrange.jsp\" method=\"POST\"><p>"
			+"Date: <input type=\"date\" name=\"date\">"
		+"</p>"
		+"<p>"
			+groupSelect(userId)
			+"</p>"
		+"<p>"
			+"Length: "
			+"<select name=\"minutes\" >";
		for(int i =5;i<120;i+=5){
			result+="<option value=\""+i+"\">"+i+"</option>";
		}
		result+="</select>"
				+ "</p>"
				+ "<input type=\"hidden\" name=\"type\" value=\"time\" />"
				+ "	<input type=\"submit\" name=\"submit\" value=\"submit\"/>"
						+ "</form>";
		return result;
	}
	public String formTime(String userId,String groupId,String date,int minutes){
		String result = "<form action=\"arrange.jsp\" method=\"POST\">"
				+"<input type=\"hidden\" name=\"date\" value=\""+date+ "\">"
				+"<input type=\"hidden\" name=\"minutes\" value=\""+minutes+ "\">"
						+"<input type=\"hidden\" name=\"type\" value=\"time\">"
			+"<p>Select Time Slot: <select name=\"timeSlot\">"
				+daySelect(userId, true, groupId, date, minutes)
				+"</select></p>"
				+ "	<input type=\"submit\" name=\"submit\" value=\"submit\"/>"
				+ "</form>";
		return result;
	}
	public boolean isset(String string){
		return string!=""&&string!=null;
	}
	
}
	
