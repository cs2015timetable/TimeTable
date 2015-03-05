import java.util.ArrayList;
import java.util.Iterator;


public class SQL {
	public static void main(String[] args){
		String date="2015-03-02";
		String user ="112431848";
		 String query = "SELECT 1 AS id, info AS text,"
		 		+ "DATE_FORMAT(CONCAT(date,' ',start_time), '%m/%d/%Y %k:%i') AS start_date, "
		 		+ "DATE_FORMAT(CONCAT(date,' ',end_time), '%m/%d/%Y %k:%i') AS end_date FROM ( "
		 		+ " SELECT date, start_time, end_time, module_id AS info FROM lecture WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT lr.date, start_time, end_time, module_id AS info FROM lecture AS l JOIN lecture_recurring AS lr ON l.lecture_id = lr.lecture_id WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND lr.date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT date, start_time, end_time, module_id AS info FROM lab WHERE module_id IN( "
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT lr.date, start_time, end_time, module_id AS info FROM lab AS l JOIN lab_recurring AS lr ON l.lab_id = lr.lab_id WHERE module_id IN("
		 		+ " SELECT module_id FROM users_modules WHERE user_id = "+user+") AND lr.date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT date, start_time, end_time, event_name AS info FROM personal_events WHERE creator_id = "+user+" AND date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT per.date, start_time, end_time, event_name AS info FROM personal_events AS pe JOIN personal_event_recurring AS per ON pe.event_id = per.event_id WHERE creator_id = "+user+" AND per.date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT imr.date, start_time, end_time, other AS info FROM individual_meeting AS im JOIN individual_meeting_recurring AS imr ON im.meeting_id = imr.meeting_id WHERE (user_id = "+user+" OR user_id_2 = "+user+") AND imr.date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT date, start_time, end_time, other AS info FROM individual_meeting WHERE (user_id = "+user+" OR user_id_2 = "+user+") AND date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "
		 		+ " SELECT gmr.date, start_time, end_time, other AS info FROM group_meeting AS gm JOIN in_group AS ig ON gm.group_id = ig.group_id JOIN group_meeting_recurring AS gmr ON gm.meeting_id = gmr.meeting_id WHERE ig.user_id = "+user+" AND gmr.date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY) UNION ALL "																				
		 		+ " SELECT date, start_time, end_time, other AS info FROM group_meeting AS gm JOIN in_group AS ig ON gm.group_id = ig.group_id WHERE ig.user_id = "+user+" AND date BETWEEN DATE_ADD('"+date+"', INTERVAL (2-DAYOFWEEK('"+date+"')) DAY) AND DATE_ADD('"+date+"', INTERVAL (6-DAYOFWEEK('"+date+"')) DAY)) AS events";
			System.out.println(query);
	}
}
