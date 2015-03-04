package database;
import java.util.ArrayList;

/**
 * @author gene
 *	An Application that sets up a database connection, and allows the user to query the database
 */
public class Application {
	
	DbClass database;
	/**
	 * Sets up the database connection with hardcoded credentials
	 */
    public void setup()
    {
        database = new DbClass();
        database.setup("localhost","ucc_timetable", "gene","rrrrrttt");
    }
    /**
     * 	Returns the result of a select query
     *	@param query The query to be run
     */
    public ArrayList<String[]> select(String query)
    {
        return database.selectDB(query);
    }
    /**
     * 	Runs a given insert query
     *	@param query The query to be run
     */
    public void insert(String query)
    {
        database.Insert(query);
    }
    /**
     * 	Runs a given delete query
     *	@param query The query to be run
     */
    public void delete(String query)
    {
        database.Delete(query);
    }
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
