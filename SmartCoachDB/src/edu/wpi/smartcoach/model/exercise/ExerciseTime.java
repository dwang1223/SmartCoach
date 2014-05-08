package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class ExerciseTime {
	private int id;
	private String time;

	public ExerciseTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExerciseTime(String exerciseTime) {
		super();
		this.time = exerciseTime;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String exerciseTime) {
		this.time = exerciseTime;
	}

	public String toString(){
		return getTime();
	}

}
