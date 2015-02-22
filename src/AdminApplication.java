import java.util.ArrayList;


public class AdminApplication {
	
	DbClass database;
	
    public void setup()
    {
        database = new DbClass();
        database.setup("cs1.ucc.ie","2016_gcd1", "gcd1","ienirogh");
    }
    
    public ArrayList<String[]> select(String query)
    {
        return database.selectDB(query);
    }
    
    public void insert(String query)
    {
        database.Insert(query);
    }
    
    public void delete(String query)
    {
        database.Delete(query);
    }
    public ArrayList<String[]> meetingConflictsDay(String users, String date)
    {
        String query = "SELECT start_time, length, sum(weight) AS conflicts FROM ( "+
        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight FROM "+
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

        		"UNION ALL "+

        		"select start_time, (end_time-start_time)/10000 AS length, 1 AS weight FROM personal_events WHERE creator_id = 00 "+
        		") AS events " +
        		"GROUP BY start_time, length;";
    	return database.selectDB(query);
    }
    public ArrayList<String[]> meetingConflictsWeek(String users, String date)
    {
        String query = "SELECT start_time, length, sum(weight) AS conflicts FROM ( "+
        		"SELECT start_time, (end_time - start_time)/10000 AS length, 3 AS weight, DAYOFWEEK(date) AS weekday FROM "+
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

        		"UNION ALL "+

        		"select start_time, (end_time-start_time)/10000 AS length, 1 AS weight, DAYOFWEEK(date) AS weekday FROM personal_events WHERE creator_id = 00 "+
        		") AS events " +
        		"GROUP BY weekday, start_time, length;";
    	return database.selectDB(query);
    }
}
