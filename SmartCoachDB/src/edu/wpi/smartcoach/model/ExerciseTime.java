package edu.wpi.smartcoach.model;

public class ExerciseTime implements OptionModel {
	private int id;
	private String exerciseTime;

	public ExerciseTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExerciseTime(String exerciseTime) {
		super();
		this.exerciseTime = exerciseTime;
	}

	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return getTime();
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return exerciseTime;
	}

	public void setTime(String exerciseTime) {
		this.exerciseTime = exerciseTime;
	}

	public String toString(){
		return getName();
	}

}
