package Forms;
import java.util.ArrayList;
import java.util.Iterator;
import static database.ArrangeMeeting.timeString;
/**
 *
 * @author bd13
 */
public class GenForm {
    
    Application app = new Application();
    
    public String createAnotherForm(String userId){
        String query = "SELECT module_id"
                        + " FROM users_modules "
                        + " WHERE user_id = "+ userId +";";
        String html = "";
        ArrayList<String> array = new ArrayList<String>();
        array = app.selectDB(query, "module_id");
        for(Object a : array){
            html += "<option value='" + a + "'>"+ a + "</option>\n";
        }
        return html;
    }
    public static String personalEvent(){
    	String result ="<!--option to choose department that the lecturer is in-->"+
    			"<p class='eventName'>"+
    			"<input type='text' name='eventName' class='validate[required,custom[email]] feedback-input' id='eventName' placeholder='Enter your Event Name' />"+
    		"</p>"+
    		"<!--option to choose a lecturer-->"+
    		"<!--datepicker-->"+
    		"<p class='date'>"+
    			"<input type='date' class='validate[required,custom[email]] feedback-input' id='datepicker' name='date' placeholder='Choose a Date' />"+
    		"</p>"+
    		"<!--option to choose an available time just hard coded a few of these values-->"+
    		"<p class='time'>"+
    			"<select type='text' name='startTime' class='validate[required,custom[email]] feedback-input' id='time' placeholder='Choose Available Time' >"+
    				"<option value='' selected='selected'>"+
    				"Start Time"+
    				"</option>";
    				for(int i =9;i<18;i++){
    					result+="<option value='"+(i*60)+"'>"+timeString(i*60)+"</option>";
    				}
    	
    			result+="</select>"+
    		"</p>"+
    		"<p>"
			+"<select class= \"validate[required,custom[email]] feedback-input\" name=\"minutes\" id=\"time\">"+
			"<option value='' selected='selected'>"+
			"Length:"+
			"</option>";
			for(int i =10;i<=120;i+=10){
				result+="<option value=\""+i+"\">"+i+"</option>";
			}
			result+="</select>"
				+ "</p>"+
    		"<!--submit button-->"+
    		"<div class='submit'>"+
    			"<input type='submit' value='SEND' name='submit'id='button-blue' />"+
    		"<div class='ease'></div>"+
    		"</div>";
    	return result;
    			
    }
    public String createStudentList(String moduleId, String userId){
        String query = "SELECT CONCAT(first_name, ' ', last_name, ' ', user_id) AS name" +
                        " FROM users" +
                        " WHERE user_id IN (" +
                        " SELECT user_id" +
                        " FROM takes_module AS t" +
                        " JOIN modules AS m" +
                        " ON t.module_id = m.module_id" +
                        " WHERE t.module_id = '"+moduleId+"'" +
                        " UNION SELECT user_id FROM users AS u"
                        + " JOIN module_of AS mo ON u.year = mo.course_year AND"
                        + " u.course_id = mo.course_id"
                        + " WHERE mo.module_id = '"+moduleId+"' AND mo.mandatory=true"+
                        " )AND user_id <>"+userId+";";
        String html = "";
        ArrayList<String> array = new ArrayList<String>();
        array = app.selectDB(query, "name");
        String spacing = "', '";
        Iterator<String> it = array.iterator();
        while(it.hasNext()){
            html += it.next() + (it.hasNext()?spacing:"");
        }

        return html;
    }
    /**
     * Queries database and returns any notifications that have not been seen yet
     * Also returns a Hidden from that gives option to leave group or cancel/postpone
     * a meeting
     * 
     * @param userId : user's id needed to query database
     * @param isSeen : boolean to test for seen or unseen notifications
     */
    public String getNotifications(String userId, int isSeen){
        String query = "select `type`, concat(description, ' on ',  NoteDate, '.') AS message, itemId, id" +
                        " FROM notifications" +
                        " WHERE user_id = "+userId+
                        " AND isSeen ="+isSeen+" ORDER BY NoteDate DESC LIMIT 10;"
                        + " DELETE FROM notifications WHERE id NOT IN (select id FROM (select id" +
                " FROM notifications" +
                " WHERE user_id = "+userId+
                " AND isSeen ="+isSeen+" ORDER BY NoteDate DESC LIMIT 10) AS inside ) AND user_id = "+userId+" AND isSeen ="+isSeen+";";
        String html = "";
        app.setup();
        ArrayList<String> array = app.selectNotifications(query, isSeen);
        for( Object a : array){
            html += a + "<br>";
        }
        return html;
    }
    public String checkNotifications(String user){
        
        String query = "select DISTINCT isSeen" +
                        " from notifications" +
                        " where user_id = "+user+
                        " AND isSeen = 0  ORDER BY NoteDate DESC LIMIT 10;";
        app.setup();
        String url = app.checkNotifications(query);
        return url;
    }
    
}// end of genForm