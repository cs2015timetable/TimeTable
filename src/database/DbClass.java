package database;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;

/**
 * A DBClass 
 * @author gene
 *
 */
public class DbClass {

    private Statement statementObject;
    private Connection connectionObject;
    private String username;
    private String password;
    private String URL;
    private boolean open=false;
    
    /**
     * Sets up the database connection
     * @param dbserver	The databases server
     * @param DSN The name of the database
     * @param username The database username
     * @param password The database password
     */
    public void setup(String dbserver, String DSN,String username,String password){
	   this.username=username;
	   this.password=password;
       this.URL = "jdbc:mysql://"+dbserver+"/" + DSN+"?allowMultiQueries=true";
       this.open = false;
    }
    
    /**
     * Opens the database connection with given credentials
     */
    public void Start(){
    	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exceptionObject) {
            writeLogSQL(URL + " caused error " + exceptionObject.getMessage()+" Error dbclass.setup.1. ");
        }
        try {
            connectionObject = DriverManager.getConnection(URL, username, password);
            this.open=true;
        } catch (SQLException exceptionObject) {
            writeLogSQL(URL + " caused error " + exceptionObject.getMessage()+" Error dbclass.setup.2");
        }
    }
    public boolean isOpen(){
    	return open;
    }
    
	 /**
	 * Closes the database connection completely
	 */
	public void Close(){
		try {	
    		if(connectionObject!=null){
    			connectionObject.close(); 
    		}
            this.open=false;
        }
        catch (SQLException exceptionObject) {
            System.out.println("Problem with closing up ");
            writeLogSQL("closing caused error " + exceptionObject.getMessage());
        }
    }

	/**
	 * Runs a given insert query
	 * @param SQLinsert The insert query
	 */
	public void Insert(String SQLinsert){
        Start();
        try {
            statementObject = connectionObject.createStatement();
            statementObject.executeUpdate(SQLinsert);
            writeLogSQL(SQLinsert +" Executed OK");
            }
        catch (SQLException exceptionObject) {
            System.out.println(SQLinsert+" - Problem is : " + exceptionObject.getMessage());
            writeLogSQL(SQLinsert + " caused error " + exceptionObject.getMessage());
            }
        finally {
                try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
            }
        Close();
        }
   
	/**
	 * Runs a given delete query
	 * @param SQLdelete The delete query
	 */
	public void Delete(String SQLdelete){
	   Start();
	   try {
		   statementObject = connectionObject.createStatement();
		   statementObject.executeUpdate(SQLdelete);
		   writeLogSQL(SQLdelete +" Executed OK");
       }
	   catch (SQLException exceptionObject) {
	       System.out.println(SQLdelete+" - Problem is : " + exceptionObject.getMessage());
	       writeLogSQL(SQLdelete + " caused error " + exceptionObject.getMessage());
       }
	   finally {
	       try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
	   }
	   Close();
   } 
	
	/**
	 * Returns the results of a select query in an ArrayList of string arrays
	 * each array is a row of results
	 * @param query The select query
	 */
	public ArrayList<String[]> selectDB(String query){
	   Start();
       ArrayList<String[]> results = new ArrayList<String[]>();
       try {
           statementObject = connectionObject.createStatement();
           ResultSet statementResult = statementObject.executeQuery(query);

           ResultSetMetaData rsmd = statementResult.getMetaData();
           int nrOfColumns = rsmd.getColumnCount();
			statementResult.beforeFirst();
           while (statementResult.next())
           {
        	   String[] row = new String[nrOfColumns];
               for(int count=0;count<nrOfColumns;count++){
					row[count] = statementResult.getString(count+1);
				}
				results.add(row);
           }
  
       }catch (Exception e) {
           System.err.println("Select problems with SQL " + query);
           System.err.println("Select problem is " + e.getMessage());
           writeLogSQL(query + " caused error " + e.getMessage());
           }
       finally {
           try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
       }
       Close();
       writeLogSQL(query + "worked ");
       return results;
   }
	/**
	 * Returns the first row of a select query
	 * @param SQLquery The select query
	 */
	public String[] SelectRow(String SQLquery){
        String Result[];
        Start();
        try {
            statementObject = connectionObject.createStatement();

            ResultSet statementResult = statementObject.executeQuery(SQLquery);

            ResultSetMetaData rsmd = statementResult.getMetaData();
            int nrOfColumns = rsmd.getColumnCount();

            Result = new String[nrOfColumns];

            statementResult.next();
            
            int currentCounter = 0;

            while (currentCounter<nrOfColumns)
            {
                Result[currentCounter] = statementResult.getString(currentCounter+1);
                currentCounter++;

            }
        } catch (Exception e) {
            System.err.println("Select problems with SQL " + SQLquery);
            System.err.println("Select problem is " + e.getMessage());
            Result = new String[0];
            writeLogSQL(SQLquery + " caused error " + e.getMessage());
            }
        finally {
            try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
        }
        Close();
        writeLogSQL(SQLquery + "worked ");
        return Result;
    }
   
    /**
     * Returns the first column of a select query
     * @param SQLquery The select query
     */
    public String[] SelectColumn(String SQLquery){
        String Result[];
        Start();
        try {
            statementObject = connectionObject.createStatement();

            ResultSet statementResult = statementObject.executeQuery(SQLquery);
            int rowcount = 0;
            if (statementResult.last()) {
                rowcount = statementResult.getRow();
                statementResult.beforeFirst();
                }

            Result = new String[rowcount];

            int currentCounter = 0;

            while (statementResult.next())
            {
                Result[currentCounter] = statementResult.getString(1);
                currentCounter++;

            }
        } catch (Exception e) {
            System.err.println("Select problems with SQL " + SQLquery);
            System.err.println("Select problem is " + e.getMessage());
            Result = new String[0];
            writeLogSQL(SQLquery + " caused error " + e.getMessage());
            }
        finally {
            try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
        }
        Close();
        writeLogSQL(SQLquery + "worked ");
        return Result;
    }

    /**
     * Writes a log message to system out and log file
     * @param message	The message to be written
     */
    public void writeLogSQL(String message) {
        PrintStream output;
        try {
            output = new PrintStream(new FileOutputStream("sql-logfile.txt", true));
            output.println(new java.util.Date() + " " + message);
            System.out.println(new java.util.Date() + " " + message);
            output.close();
        } catch (IOException ieo) {
        }
    }
    /**
     * Uses a query to get a result set from the database and returns that as an
     * arrayList
     * @param query : SQlQuery
     * @param columnName : Column to pull data out of
     * @return 
     */
    public ArrayList<String> createInitialForm(String query, String columnName){
        
        ArrayList<String> html = new ArrayList<String>();      
        try
        {
            Start();
			statementObject = connectionObject.createStatement();
			ResultSet statementResult = statementObject.executeQuery(query);
            // loop through the resultset
            while (statementResult.next())
            {
                String moduleName = statementResult.getString(columnName);
                // proccess the results
                html.add(moduleName);
            }
        } catch (Exception e) {
            System.err.println("Exception occured!");
            System.err.println(e.getMessage());
            writeLogSQL(query + " caused error " + e.getMessage());
            }
        finally {
            try { if (statementObject != null) statementObject.close(); } catch (Exception e) {};
        }
        Close();
        writeLogSQL(query + "worked ");
        return html;
    }//end of createInitialForm()
    /**
     * Uses a query to get a result set from the database and returns that as an
     * arrayList
     * @param query : SQlQuery
     * @param columnName : Column to pull data out of
     * @return 
     */
    public ArrayList<String> getColumnFromDb(String query, String columnName){
        
        ArrayList<String> html = new ArrayList<String>();      
        try{
            Start();
            // create the java statement
            statementObject = connectionObject.createStatement();
            // execute the query, and get a java resultset
            ResultSet statementResult = statementObject.executeQuery(query);
            // loop through the resultset
            while (statementResult.next())
            {
                String moduleName = statementResult.getString(columnName);
                // proccess the results
                html.add(moduleName);
            }
            statementObject.close();
        } catch (Exception e) {
            System.err.println("Exception occured!");
            System.err.println(e.getMessage());
        }
        return html;
    }//end of createInitialForm()

    
    public String checkNotifications(String query) {
        try{
            Start();
            // create the java statement
            statementObject = connectionObject.createStatement();
            // execute the query, and get a java resultset
            ResultSet statementResult = statementObject.executeQuery(query);
            // loop through the resultset
            String value = "";
            while (statementResult.next())
            {
                value = statementResult.getString("isSeen");
                if(value.equals("0"))
                {
                    return "<a href='notifications.jsp' id='one'><img src='images/hasNew";
                }
            }
            statementObject.close();
        } catch (Exception e) {
            System.err.println("Exception occured!");
            System.err.println(e.getMessage());
        }
        return "<a href='oldNotifications.jsp' id='one'><img src='images/noNew";
    }
    
   public ArrayList<String> getNotificationsFromDb(String query) {
        ArrayList<String> list = new ArrayList<String>();      
        try{
            Start();
            // create the java statement
            statementObject = connectionObject.createStatement();
            // execute the query, and get a java resultset
            ResultSet statementResult = statementObject.executeQuery(query);
            // loop through the resultset
            while (statementResult.next())
            {
                String html = "";
                String page = "meetingCancel";
                String type = statementResult.getString("type");
                String id_type = "meeting_button";
                if(type.equals("group")){
                    page = "leaveGroup";
                    id_type ="group_button";
                }
                //these will be presented in forms to give users
                //an option of dropping groups/cancelling meetings
                html += ("<form id='note_form' method='post' action='"+page+".jsp'>");
                String message = statementResult.getString("message");
                String groupId = statementResult.getString("itemId");
                String noteId = statementResult.getString("id");
                html += ("<input type='hidden' name='groupId' value='"+groupId+"'>");
                 html += ("<input type='hidden' name='noteId' value='"+noteId+"'>");
                html += (message);
                html += ("<br><input id='"+id_type+"' type='submit' value='"+page+"'></form>");
                list.add(html);
            }
            statementObject.close();
            Close();
        } catch (Exception e) {
            System.err.println("Exception occured!");
            System.err.println(e.getMessage());
        }
        return list;
    }
    public ArrayList<String> getOldNotificationsFromDb(String query) {
        ArrayList<String> list = new ArrayList<String>();      
        try{
            Start();
            // create the java statement
            statementObject = connectionObject.createStatement();
            // execute the query, and get a java resultset
            ResultSet statementResult = statementObject.executeQuery(query);
            // loop through the resultset
            while (statementResult.next())
            {
                String html = "";
                //these will be presented in forms to give users
                //an option of dropping groups/cancelling meetings
                html += ("<div id='note_form'>");
                String message = statementResult.getString("message");
                String groupId = statementResult.getString("itemId");
                String noteId = statementResult.getString("id");
                html += ("<input type='hidden' name='groupId' value='"+groupId+"'>");
                html += ("<input type='hidden' name='noteId' value='"+noteId+"'>");
                html += (message+"</div>");
                list.add(html);
            }
            statementObject.close();
        } catch (Exception e) {
            System.err.println("Exception occured!");
            System.err.println(e.getMessage());
        }
        return list;
    }
}
