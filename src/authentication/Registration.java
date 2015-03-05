/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

/**
 *
 * @author dd18
 */
public class Registration {
    public void setCredentials(String title, String firstname, String surname,
            String email, String password, String phone, String user_type, 
            int year, String course_id){
        application app = new application();
        try{
            app.setup();
            System.out.println("Registration connected!");
        } catch(Exception Error){
            System.out.println("Can't connect to database!");
        }     
        app.setNewUser(password, user_type, title, firstname, surname, phone, email, year, course_id);
    }
}
