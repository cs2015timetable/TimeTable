package authentication;
import java.util.ArrayList;
import java.util.Iterator;

import database.DbClass;
import static database.ArrangeMeeting.timeString;
/**
 * A Class for connecting with a database and various methods for manipulating that database.
 * @author dd18{112301841}
 */
public class application { 
            DbClass database;
            /** 
             * Sets up a database.
             */
            public application(){
            	setup();
            }
	    public void setup()
	    {
	        database = new DbClass();
	        database.setup("localhost","ucc_timetable", "gene","rrrrrttt");
	    }                
	   /**
            * Selects a row from a database.
            * @param username The users username.
            * @param password The users password.
            * @return A String array of the users input.
            */
		    public String[] getNames(String email, String password)
	        {
	          try{
	            return database.SelectRow("Select user_id from users Where email = '" + email + "' AND password = '" + password + "';");         
	          } catch(Exception E){
	              return null;
	          }
	        }    
		    public String cancelMeetingForm(String userId){
		    	String query = "SELECT CONCAT(type,',', id), CONCAT(type,': ',name,' ',date) FROM(SELECT * FROM("
		    			+ "SELECT 'Group' AS type, meeting_id AS id, date, other AS name, start_time"
		    			+ " FROM group_meeting WHERE user_id = "+userId
		    			+ " UNION ALL SELECT 'Individual' AS type, meeting_id AS id, date, other AS name, start_time"
		    			+ " FROM individual_meeting WHERE user_id = "+userId+" OR user_id_2 = "+userId
		    			+ ") AS meetings ORDER BY date, start_time) AS results;";
		    	ArrayList<String[]> results = database.selectDB(query);
		    	String result = "<p class=\"time\"><div class= \"validate[required,custom[email]] feedback-input\" id=\"time\">No meetings to delete</div></p>";
		    	if(results.size()>=1){
			    	result="<p class=\"time\">"+
			    			"<select name=\"id\" type=\"text\" class=\"validate[required,custom[email]] feedback-input\""+
	            		" id=\"time\">"+
			    	"<option value=''>Select meeting to delete:</option>";
			    	Iterator<String[]> it = results.iterator();
			    	while(it.hasNext()){
			    		String[] curr = it.next();
			    		result+="<option value='"+curr[0]+"'>"+curr[1]+"</option>";
			    	}
			    	result+="</select>"+
						"</p>"+
						"<div class=\"submit\">"+
			           "<input type=\"submit\" value=\"CANCEL\" name=\"submit\" id=\"button-blue\" />"+
			            "<div class=\"ease\"></div>"+
			         "</div>";
		    	}
		    	return result;
		    }
            public void deleteMeeting(String input){
            	String[] split = input.split(",");
            	if(split.length==2){
            		System.out.print(split[0]);
	            	boolean group = split[0].equals("Group");
	            	String id =split[1];
	                try{
	                    String query = "DELETE FROM "+(group?"group_meeting":"individual_meeting")+" WHERE meeting_id="+id+";" +
	                    		"DELETE FROM "+(group?"group_meeting":"individual_meeting")+"_recurring WHERE meeting_id="+id+";";
	                    database.Delete(query);
	                } catch(Exception E){
	                    System.out.println("Exception Error during deleteMeeting stage.");
	                }
            	}
            }
            
            /**
             * A method for querying the database.
             * @param username The users username.
             * @param password The users password.
             * @return A boolean if their is a result in the database the boolean is true else false.
             */
            public boolean checkDatabase(String username, String password){
                boolean dbcontains;
                /**
                 * A String array which is the result of the database query.
                 */
                String[] names = getNames(username, password);
                //If the array is not initialized or the length is null.
                if(names.equals(null) || names.length == 0){
                    dbcontains = false;                    
                } else{
                    dbcontains = true;
                }
                return dbcontains;
            }
            
        public void InsertNewEvent(String creatorId, String event_name, String date, String start_time, String minutes){
        	int time = Integer.parseInt(start_time);
        	int mins =Integer.parseInt(minutes);
            String query ="INSERT INTO personal_events(creator_id, event_name,event_description,date, recurring,start_time, end_time) "
            		+ "VALUES('" + creatorId + "','" + event_name  + "','"+ event_name  + "','"+date+"',false,'" + timeString(time) + "', '"+timeString(time+mins)+"');";
            database.Insert(query);
        }
        
            public void setNewUser(String password, String type_of_user,String title,String first_name,String last_name, 
                                    String phone_no , String email,int year,String course_id){
                //database.Insert("Insert INTO users(user_id, password, type_of_user, title, first_name, last_name, phone_no, email, year, course_id, department_id)\n" +
                //               "VALUES('123456789', 'password','Student', 'Mr', 'Bob', 'foo', '12345678', '123456789@umail.ucc.ie', 4, 'CompWeb', 'Comp');");
            
                if(email.contains("@umail.ucc.ie")){
                    String upToNCharacters = email.substring(0, Math.min(email.length(), 9));
                    System.out.println(upToNCharacters);
                    password = MD5.getMD5(password);
                    
                    database.Insert("Insert INTO users(user_id, password, type_of_user, title, first_name, last_name, phone_no, email, year, course_id)\n" +
                                    "VALUES(" + "'" + upToNCharacters + "','" + password + "','" + type_of_user + "','" + title + "','" + first_name + "','" +
                            last_name + "','" + phone_no + "','" + email + "'," + year  + ",'" + course_id + "');");
                }
            }                     
            
            public void setCredentials(String email){
                if(email.contains("@umail.ucc.ie")){
                    database.Insert("Insert INTO users(email)VALUES(" + email + ");");
                    String upToNCharacters = email.substring(0, Math.min(email.length(), 9));
                    database.Insert("Insert INTO users(user_name)VALUES(" + upToNCharacters + ");");
                }
            }
            
            public void setPassword(String string){
                String password = MD5.getMD5(string);
                database.Insert("Insert INTO users(password)VALUES(" + password + ");");
            }                    
}


