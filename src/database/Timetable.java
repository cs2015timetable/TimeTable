package database;
import java.util.ArrayList;
import java.util.Iterator;

public class Timetable {
		public Timetable(){
			app = new Application();
			app.setup();
		}
		private Application app;  
	public ArrayList<String[]> events(String user){
		String query = "SELECT DISTINCT 1 AS id, info AS text,"
		 		+ "DATE_FORMAT(CONCAT(date,' ',start_time), '%m/%d/%Y %k:%i') AS start_date, "
		 		+ "DATE_FORMAT(CONCAT(date,' ',end_time), '%m/%d/%Y %k:%i') AS end_date FROM ( "
		 		+ " SELECT date, start_time, end_time, module_id AS info FROM lecture WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT lr.date, start_time, end_time, module_id AS info FROM lecture AS l JOIN lecture_recurring AS lr ON l.lecture_id = lr.lecture_id WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND lr.date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT date, start_time, end_time, module_id AS info FROM lab WHERE module_id IN( "
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT lr.date, start_time, end_time, module_id AS info FROM lab AS l JOIN lab_recurring AS lr ON l.lab_id = lr.lab_id WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND lr.date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT date, start_time, end_time, event_name AS info FROM personal_events WHERE creator_id = "+user+" AND date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT per.date, start_time, end_time, event_name AS info FROM personal_events AS pe JOIN personal_event_recurring AS per ON pe.event_id = per.event_id WHERE creator_id = "+user+" AND per.date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT imr.date, start_time, end_time, other AS info FROM individual_meeting AS im JOIN individual_meeting_recurring AS imr ON im.meeting_id = imr.meeting_id WHERE (user_id = "+user+" OR user_id_2 = "+user+") AND imr.date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT date, start_time, end_time, other AS info FROM individual_meeting WHERE (user_id = "+user+" OR user_id_2 = "+user+") AND date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "
		 		+ " SELECT gmr.date, start_time, end_time, other AS info FROM group_meeting AS gm JOIN in_group AS ig ON gm.group_id = ig.group_id JOIN group_meeting_recurring AS gmr ON gm.meeting_id = gmr.meeting_id WHERE ig.user_id = "+user+" AND gmr.date BETWEEN '2014-09-01' AND '2015-09-01' UNION ALL "																				
		 		+ " SELECT date, start_time, end_time, other AS info FROM group_meeting AS gm JOIN in_group AS ig ON gm.group_id = ig.group_id WHERE ig.user_id = "+user+" AND date BETWEEN '2014-09-01' AND '2015-09-01') AS events;";
			return app.select(query);
	}
	public String xhtmlString(String user){
		ArrayList<String[]> results = events(user);
		Iterator<String[]> it = results.iterator();
		String result ="[";
		int i =0;
		while(it.hasNext()){
			i++;
			String[] curr = it.next();
			result+= "{id:"+i+", text:'"+curr[1]+"', start_date:'"+curr[2]+"', end_date:'"+curr[3]+"'}"+(it.hasNext()?",":"");
		}
		result+="]";
		return result;
	}
}
