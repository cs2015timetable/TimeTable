import java.io.FileInputStream;
import java.io.Serializable;
import java.io.BufferedReader;
public class AdminUser extends User{
	private DB db;
	public AdminUser(){
	}
	public void startDB(){
		db = new DB("admin");
		db.start();
	}
	public boolean addCourse(String courseId,String departmentId, String courseTitle, 
			int years, String awardType){
			String query = "INSERT into modules (course_id, department_id, course_title,"
					+ "years,award_typer) values ('"+courseId+"', '"+departmentId
					+"', '"+courseTitle+"', '"+years+"', '"+awardType+");";
			return db.run(query);
				
	}
	public boolean addModule(String moduleId,String departmentId, String moduleName, 
			int credits, String coordinator,String semester){
			String query = "INSERT into modules (module_id, department_id, module_name,"
					+ "credits,coordinator,semester) values ('"+moduleId+"', '"+departmentId
					+"', '"+moduleName+"', '"+credits+"', '"+coordinator+"', '"+semester+");";
			return db.run(query);
				
	}
	public boolean addLecture​( String moduleId ,String type, int startTime, int endTime, String day, int startDate,
			int endDate, String location){

			String query = "INSERT into lectures ​(module_id , type, startTime, endTime, day"
			+", startDate, endDate, location) values ('"+moduleId+"', '"+type
			+"', '"+startTime+"', '"+endTime+"', '"+day+"', '"+startDate+"', '"+endDate+"', '"+location+");";
	return db.run(query);
	}
	public boolean editCourse(String courseId,String departmentId, String courseTitle, 
			int years, String awardType){
			String query = "INSERT into modules (course_id, department_id, course_title,"
					+ "years,award_typer) values ('"+courseId+"', '"+departmentId
					+"', '"+courseTitle+"', '"+years+"', '"+awardType+"');";
			return db.run(query);
				
	}
	public boolean editModule(String moduleId,String departmentId, String moduleName, 
			int credits, String coordinator,String semester){
			String query = "UPDATE modules SET (module_id, department_id, module_name,"
					+ "credits,coordinator,semester) values ('"+moduleId+"', '"+departmentId
					+"', '"+moduleName+"', '"+credits+"', '"+coordinator+"', '"+semester
							+ ") WHERE module_id='"+moduleId+"';";
			return db.run(query);
					
			}
	public boolean editLecture​( int lectureId, String moduleId ,String type, int startTime, int endTime, String day, int startDate,
			int endDate, String location){
				String query = "UPDATE lectures SET ​(module_id , type, startTime, endTime, day"
					+", startDate, endDate, location) values ('"+moduleId+"', '"+type
							+"', '"+startTime+"', '"+endTime+"', '"+day+"', '"+startDate+"', '"+endDate+"', '"+location+")"
									+ " WHERE lecture_id = "+lectureId+";";
					return db.run(query);
				
	}
	public boolean deleteUser(int userId){
		String query = "DELETE FROM users WHERE user_id = "+userId+";";
		return db.run(query);
	}
	public boolean deleteModule(String moduleId){
		String query = "DELETE FROM modules WHERE module_id = '"+moduleId+"';";
		return db.run(query);
	}
	public boolean deleteLecture(int lectureId){
		String query = "DELETE FROM lectures WHERE lecture_id = "+lectureId+";";
		return db.run(query);
	}
	public boolean deleteCourse(String courseId){
		String query = "DELETE FROM courses WHERE course_id = '"+courseId+"';";
		return db.run(query);
	}
	
}
