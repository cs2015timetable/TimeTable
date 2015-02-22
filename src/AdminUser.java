import java.io.FileInputStream;
import java.io.Serializable;
import java.io.BufferedReader;
public class AdminUser extends User{
	private AdminApplication app;
	public AdminUser(){
	}
	public void setup(){
		app = new AdminApplication();
		app.setup();
	}
	public void addCourse(String courseId,String departmentId, String courseTitle, 
			int years){
			String query = "INSERT into modules (course_id, department_id, course_title,"
					+ "years,award_typer) values ('"+courseId+"', '"+departmentId
					+"', '"+courseTitle+"', '"+years+");";
			app.insert(query);
				
	}
	public void addModule(String moduleId,String departmentId, String moduleName, 
			int credits, String coordinator,String semester){
			String query = "INSERT into modules (module_id, department_id, module_name,"
					+ "credits,coordinator,semester) values ('"+moduleId+"', '"+departmentId
					+"', '"+moduleName+"', '"+credits+"', '"+coordinator+"', '"+semester+");";
			app.insert(query);
				
	}
	public void addModuleOf(String moduleId,String courseId, int year, boolean mandatory, 
			int credits, String coordinator,String semester){
			String query = "INSERT into module_of (module_id, course_id, course_year,"
					+ "mandatory) values ('"+moduleId+"', '"+courseId
					+"', '"+year+"', '"+mandatory+");";
			app.insert(query);
				
	}
	public void addLecture( String moduleId ,String type, int startTime, int endTime, String day, int startDate,
			int endDate, String location){

			String query = "INSERT into lectures (module_id , type, startTime, endTime, day"
			+", startDate, endDate, location) values ('"+moduleId+"', '"+type
			+"', '"+startTime+"', '"+endTime+"', '"+day+"', '"+startDate+"', '"+endDate+"', '"+location+");";
	app.insert(query);
	}
	public void editCourse(String courseId,String departmentId, String courseTitle, 
			int years){
			String query = "INSERT into modules (course_id, department_id, course_title,"
					+ "years,award_typer) values ('"+courseId+"', '"+departmentId
					+"', '"+courseTitle+"', '"+years+"');";
			app.insert(query);
				
	}
	public void editModule(String moduleId,String departmentId, String moduleName, 
			int credits, String coordinator,String semester){
			String query = "UPDATE modules SET (module_id, department_id, module_name,"
					+ "credits,coordinator,semester) values ('"+moduleId+"', '"+departmentId
					+"', '"+moduleName+"', '"+credits+"', '"+coordinator+"', '"+semester
							+ ") WHERE module_id='"+moduleId+"';";
			app.insert(query);
					
			}
	public void editLecture( int lectureId, String moduleId ,String type, int startTime, int endTime, String day, int startDate,
			int endDate, String location){
				String query = "UPDATE lectures SET (module_id , type, startTime, endTime, day"
					+", startDate, endDate, location) values ('"+moduleId+"', '"+type
							+"', '"+startTime+"', '"+endTime+"', '"+day+"', '"+startDate+"', '"+endDate+"', '"+location+")"
									+ " WHERE lecture_id = "+lectureId+";";
					app.insert(query);
				
	}
	public void deleteUser(int userId){
		String query = "DELETE FROM users WHERE user_id = "+userId+";";
		app.delete(query);
	}
	public void deleteModule(String moduleId){
		String query = "DELETE FROM modules WHERE module_id = '"+moduleId+"';";
		app.delete(query);
	}
	public void deleteLecture(int lectureId){
		String query = "DELETE FROM lectures WHERE lecture_id = "+lectureId+";";
		app.delete(query);
	}
	public void deleteCourse(String courseId){
		String query = "DELETE FROM courses WHERE course_id = '"+courseId+"';";
		app.delete(query);
	}
	
}
