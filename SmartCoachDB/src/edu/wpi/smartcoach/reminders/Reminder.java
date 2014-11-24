package edu.wpi.smartcoach.reminders;

public class Reminder {
	
	int id;
	String message;
	String days;
	int hour;
	int minute;
	public Reminder(int id, String message, String days, int hour, int minute) {
		super();
		this.id  = id;
		this.message = message;
		this.days = days;
		this.hour = hour;
		this.minute = minute;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the days
	 */
	public String getDays() {
		return days;
	}
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		this.days = days;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	@Override
	public String toString(){
		return String.format("%s @ %d:%d on %s", message, hour, minute, days);		
	}
	
	

}
