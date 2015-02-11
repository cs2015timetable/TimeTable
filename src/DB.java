
public class DB {
	private boolean connected;
	public DB(String type){
		connected=false;
	}
	public boolean run(String query){
		try{
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	public boolean start(){
		try{
			connected=true;
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	public boolean close(){
		try{
			connected=false;
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
