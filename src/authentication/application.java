package authentication;
/**
 * A Class for connecting with a database and various methods for manipulating that database.
 * @author dd18{112301841}
 */
public class application { 
            dbClass database;
            /** 
             * Sets up a database.
             */
	    public void setup()
	    {
	        database = new dbClass();
	        database.setup("cs1.ucc.ie","2016_dd18", "dd18","haeweich");
	    }         
	   /**
            * Selects a row from a database.
            * @param username The users username.
            * @param password The users password.
            * @return A String array of the users input.
            */
            public String[] getNames(String username, String password)
	    {
              try{
                return database.SelectRow("Select * from users Where user_name = '" + username + "' AND password = '" + password + "';");         
              } catch(Exception E){
                  return null;
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
                 * A String array which ius the result of the database query.
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
}


