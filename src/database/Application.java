package database;
import java.util.ArrayList;

public class Application {
	
	DbClass database;
	
    public void setup()
    {
        database = new DbClass();
        database.setup("localhost","timetable", "gcd1","ienirogh");
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
}
