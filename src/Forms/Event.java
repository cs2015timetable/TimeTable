package Forms;

import java.util.Date;


public class Event {

	private Date date;
	private int endTime;
	private int id;
	private String name;
	private int StartTime;
	private EventType type;
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTime() {
		return StartTime;
	}

	public void setStartTime(int startTime) {
		StartTime = startTime;
	}

	public EventType getType() {
		return type;
	}
	
}
