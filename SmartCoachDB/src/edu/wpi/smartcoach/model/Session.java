package edu.wpi.smartcoach.model;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A question session
 * @author Akshay
 *
 */
public class Session {
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM d, yyyy h:mm a");
	
	private long time;
	private String type;
	
	private List<Solution> solutions;

	public Session(){
		time = -1;
		type = null;
		solutions = null;
	}
	
	public Session( long time, String type, List<Solution> solutions) {
		super();
		this.time = time;
		this.type = type;
		this.solutions = solutions;
	}

	

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the solutions
	 */
	public List<Solution> getSolutions() {
		return solutions;
	}


	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(List<Solution> solutions) {
		this.solutions = solutions;
	}

}
