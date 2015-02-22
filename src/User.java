import java.util.ArrayList;
import java.util.Iterator;

public class User {
	private AdminApplication app;
	public ArrayList<String[]> conflictsDay(ArrayList<String> users,  String date){
		String usersString;
		Iterator<String> iterator = users.iterator();
		if(iterator.hasNext()){
			usersString = iterator.next();
			while(iterator.hasNext()){
			usersString+= ", "+iterator.next();
			}
			return app.meetingConflictsDay(usersString, date);
		}
		else{
			return null;
		}
	}
	public ArrayList<String[]> conflictsWeek(ArrayList<String> users,  String date){
		String usersString;
		Iterator<String> iterator = users.iterator();
		if(iterator.hasNext()){
			usersString = iterator.next();
			while(iterator.hasNext()){
			usersString+= ", "+iterator.next();
			}
			return app.meetingConflictsWeek(usersString, date);
		}
		else{
			return null;
		}
	}

	//returns free times in format {startTime, EndTime}
	//times in minutes so 540=540/60=9(o clock)
	public ArrayList<Integer[]> freeTimesDay(ArrayList<String> users,  String date, int minutes){
		ArrayList<Integer[]> times = new ArrayList<Integer[]>();
		int endTime=1080;
		int weight = 0;
		ArrayList<String[]> conflicts = conflictsDay(users,date);
		if(conflicts==null){
			return null;
		}
		while(times.size()<1){
			Iterator<String[]> iterator = conflicts.iterator();
			//initialized to start of day minutes in case no 
			//events in the day
			int lastEndTime = 540;
			int startTime;
			int length;
			while(iterator.hasNext()){
				String[] currentRow = iterator.next();
				if(weight<Integer.parseInt(currentRow[2])){
					startTime = Integer.parseInt(currentRow[0]);
					length = Integer.parseInt(currentRow[1]);
					if(startTime-lastEndTime>=minutes){
						Integer[] meetingTime = {lastEndTime, startTime};
						times.add(meetingTime);
					}
					lastEndTime = startTime+length;
				}
			}
			if(endTime-lastEndTime>=minutes){
				Integer[] meetingTime= {lastEndTime,endTime};
				times.add(meetingTime);
			}
			weight++;
		}
		return times;
	}
	//returns free times in format {dayOfweek, startTime, EndTime}
	//times in minutes so 540=540/60=9(o clock)
	public ArrayList<Integer[]> freeTimesWeek(ArrayList<String> users,  String date, int minutes){
		ArrayList<Integer[]> times = new ArrayList<Integer[]>();
		int endTime=1080;
		int weight = 0;
		ArrayList<String[]> conflicts = conflictsWeek(users,date);
		if(conflicts==null){
			return null;
		}
		while(times.size()<1){
			Iterator<String[]> iterator = conflicts.iterator();
			//initialized to start of day minutes in case no 
			//events in the day
			int lastEndTime = 540;
			int startTime;
			int length;
			//monday
			int curDay=2;
			while(iterator.hasNext()){
				String[] currentRow = iterator.next();
				int day =Integer.parseInt(currentRow[3]);
				if(curDay<day){
					curDay=day;
					lastEndTime = 540;
				}
				if(weight<Integer.parseInt(currentRow[2])){
					startTime = Integer.parseInt(currentRow[0]);
					length = Integer.parseInt(currentRow[1]);
					if(startTime-lastEndTime>=minutes){
						Integer[] meetingTime = {curDay,lastEndTime, startTime};
						times.add(meetingTime);
					}
					lastEndTime = startTime+length;
				}
			}
			if(endTime-lastEndTime>=minutes){
				Integer[] meetingTime= {curDay,lastEndTime,endTime};
				times.add(meetingTime);
			}
			weight++;
		}
		return times;
	}
}
	
