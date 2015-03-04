package authentication;
/**
 * This is a class which queries and validates the users Info.
 * @author dd18{112301841}.
 */
public class validate {
        /**
         * This is a private variable which holds the username entered in the form.
         */
	private String username; //checks username entered
	/**
         * This is a private variable which holds the passweord entered in the form.
         */
        private String password; //checks password entered
	
        /**
         * A setter for setting a username and password to their respective private variables i.e username, password.
         * @param username The users username.
         * @param password  The users Password.
         */
	public void setCredentials(String username, String password){
            this.username = username;
            this.password = password;
        }
        
        /**
         * A method for querying a database.
         * @return String A url for redirecting depending on the users input.
         */
        public String queryCredentials(){
            /**
             * Initializing the application class.
             */
            application app = new application();
            // Try and setup/connect to the database.
            try{
                app.setup();
            } catch(Exception Error){
                System.out.println("Can't connect to database!");
            }
            //overwrite the password value with the respective md5 hash value.
            password = getMd5Value(password);
            /**
             * A boolean - true if the user is genuine and false if the users credentials are incorrect.
             */
            String[] user = app.getNames(username, password);
            if(user==null||user.length!=1){
                return "";   
            } else{           
                return user[0];
            }
        }     
        
        /**
         * A method for converting a string value to a hash.
         * @param userPassword //A string value of the users input.
         * @return // A string of a hash code.
         */
        public String getMd5Value(String userPassword){                       
            return MD5.getMD5(userPassword);
        }
}
