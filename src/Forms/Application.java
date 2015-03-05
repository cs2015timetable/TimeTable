/**
 *
 * @author colin
 */
package Forms;
import database.DbClass;

import java.util.ArrayList;

public class Application {
	
    DbClass database;
	public Application(){
		setup();
	}
    public void setup()
    {
        database = new DbClass();
        database.setup("localhost","ucc_timetable", "gene","rrrrrttt");
    }
    
    public String[] getNames()
    {
        return database.SelectColumn("select username from users;");
    }
    
    public void insert(String query)
    {
        database.Insert(query);
    }
    
    public void delete(String query)
    {
        database.Delete(query);
    }
    
   /**
     * Returns an arrayList of rows from a database
     * 
     * @param query
     * @return
     */
    public ArrayList<String> selectDB(String query, String columnName){
        return database.createInitialForm(query, columnName);
    }
    public ArrayList<String> selectNotifications(String query, int isSeen) {
        if(isSeen == 0 ){
            return database.getNotificationsFromDb(query);
        }else{
            return database.getOldNotificationsFromDb(query);
        }
    }

    public void update(String query) {
        database.Insert(query);
    }

    public String checkNotifications(String query) {
        
         return database.checkNotifications(query);
    }
}
