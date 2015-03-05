package Forms;

import java.util.*;

/**
 * This is a class that represents a regular user.
 * It extends the USER super class. It is the super class to LECTURER and STUDENT 
 * @author bd13
 */
public class RegularUser extends User {
	
	//This is the address of a user
	private String address;
	
	//This is the weeks calender for a user
	private WeekCalender calender;
	
	//This is a list of groups a regular user has.
	private List<String> groups;
	
	//This is a list of modules a regular user has.
	private List<String> modules;

        /**
        * This is a setter for setting the address of a user.
        * @param address The address of a user.
        */
	public void setAddress(String address) {
            this.address = address;
	}
        /**
        * This is a getter for getting a users address.
        * @return address The address of a user.
        */
	public String getAddress() {
            return address;
	}
	/**
        * This is a setter for setting a users weekly calender.
        * @param calender The weekly calender of a user.
        */
	public void setCalender(WeekCalender calander) {
            this.calender = calander;
	}
	/**
        * This is a getter for getting a users weekly calender.
        * @return calender The weekly calender of a user.
        */
	public WeekCalender getCalender() {
            return calender;
	}
	
	
	/**
        * This is a setter for setting a users weekly group.
        * 
        * @param groups The group a users has.
        */
	public void setGroups(List<String> groups) {
            this.groups = groups;
	}
	/**
        * This is a getter for getting a users weekly group.
        * 
        * @return groups The group a user has.
        */
	public List<String> getGroups() {
            return groups;
	}
	
	/**
        * This is a setter for setting a users modules.
        * 
        * @param modules 
        */
	public void setModules(List<String> modules) {
            this.modules = modules;
	}
	/**
        * This is a getter that returns a list of modules.
        * 
        * @return 
        */
	public List<String> getModules() {
            return modules;
	}	
	
	/**	
        * This method is used to arrange a meeting.
        * 
        * @param  group: the group that the meeting is being set up for
        */
	public void arrangeMeeting( String group ){
		
            //possibly return a form that gives the user a list of available 
            //time slots that they can either confirm or cancel
		
	}//end of arrangeMeeting()
	
        /**
        * This is a method for creating a group.
        * 
        * @param groupLeader : the creator of the group
        * @param module : the module this group is made for
        * @param users : list of users to be added to the group
        */
	public void createGroup(String groupLeader, String name, List<String> users){
		
            // statement to create the group in database
            String query = "INSERT INTO groups ( owner_id, group_name)"
                                    + "VALUES(" + groupLeader + ", '"+name+"');";
            
            // beginning of statement to add individual users
            query+= " INSERT INTO in_group (user_id, group_id) VALUES ";

            String notifyQuery="INSERT INTO notifications"+ 
                    "(`type`, user_id, description, itemId)"+ 
                    " VALUES";
            String groupId = "(SELECT group_id FROM groups WHERE group_name = '"+name+"' AND owner_id = "+groupLeader+" ORDER BY group_id DESC LIMIT 1)";
            //Iterate through users and add their details to the database
            for(String u : users){
                query += " (" + u + ", "+groupId+"),";
                notifyQuery+=" ('group', "+u+", 'You were added to the group "+name+"',"+groupId+"),";
            }//end of for loop
            query += " (" + groupLeader + ", "+groupId+");";
            notifyQuery+=" ('group', "+groupLeader+", 'You created the group "+name+"',"+groupId+");";
            //connect to db
            app.insert(query+notifyQuery);
	}//end of createGroup()
	
	public void editGroup(String action, String group, User user){
		
            
	}//end of editGroup()
	public void leaveGroup(String groupId, String user, String NoteId){

        String query = "DELETE FROM groups WHERE group_id = "+
        		groupId+" AND owner_id = "+user+";"+
        		" DELETE FROM in_group WHERE group_id = "+
        		"(SELECT group_id FROM groups WHERE group_id = "+groupId+
        		" AND owner_id = "+user+");"+
        		" DELETE FROM in_group" +
                        " WHERE user_id = "+user+
                        " AND group_id = "+groupId+";";
        //query to set noteification as seen
        //query to set noteification as seen
        String notify = "UPDATE notifications" +
                        " SET isSeen = 1" +
                        " WHERE id = "+NoteId+";";
        app.delete(query);
        app.update(notify);
    }//end of editGroup()
	/**
     * This is a method for deleting a group.
     * 
     * @param groupLeader : leader of the group(only user that can delete the group)
     * @param module : the module this group is made for
     * @param groupId : id for group
     */
	public void deleteGroup(String groupLeader, String module, String groupId){

            //This method should only be called by a group leader so if 
            //a user is not a group leader this method is not callable
            //look up db to find group leader and if it matches the user 
            //continue as normal

            String sqlStatement1 = "Delete FROM group WHERE group_id = " + groupId + ";";
            String sqlStatement2 = "Delete FROM in_group WHERE group_id " + groupId + ";";

            //connect to db
          
            app.delete(sqlStatement1);
            app.delete(sqlStatement2);
	}//end of deleteGroup()
	
	/**
     * This is a method for creating an Event.
     * 
     * @param userId : user who is setting up the event
     * @param name : name of the event
     * @param date : what date the event takes place
     * @param startTime : when the event starts
     * @param isRecurring : is it on every week
     * @param endTime : when the event ends
     * @param type : is it a lab(lecturer only), personal event
     */
    public void addEvent( int userId, String name, Date date, int startTime, boolean isRecurring, int endTime, EventType type ){

        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        String sqlStatement = "INSERT INTO personal_events(creator_id, event_name,"
                            + " event_description, event_date, recurring, start_time, end_time)"
                            + "VALUES ('" + userId + "', '" + event.getName() + "', '" + type + "', '" 
                            + event.getDate() + "', '" + isRecurring + "', '" +  event.getStartTime() 
                            + "', '" + event.getEndTime() + ";";
        //execute statements
        app.insert(sqlStatement);
    }
    /**
     * This is a method for deleting an Event.
     * 
     * @param creatorid : creator of event being deleted
     * @param eventid : id for event being deleted
     */
    public void deleteEvent( int creatorId, int eventId ){
        String sqlStatement = "DELETE FROM personal_events WHERE creator_id = "
                            + creatorId + " AND event_id = " + eventId +";";

        //execute the query
        app.delete(sqlStatement);
    }
	
    public void confirmMeeting(){


    }
    /**
     * This is a method that will cancel a meeting.
     * 
     * @param group : the group the meeting is associated with
     * @param user : the user that deletes the meeting
     */
    public void cancelMeeting(String group, User user){

        //user should be a member of the group otherwise
        //exit method without canceling
        String groupId = group;
        String sqlStatement = "DELETE FROM Meeting WHERE group_id = " + 
                                groupId + ";";
       
        app.delete(sqlStatement);
    }
    public void confirmNotifications(String user){
        String query = "UPDATE notifications" +
                    " SET isSeen = 1" +
                    " WHERE user_id = "+user+";";
        try{
            app.setup(); //sets up a database.
        } catch(Exception Error){
            System.out.println("Error with setup of Database!");
        }
        app.update(query);
    }
	
}
