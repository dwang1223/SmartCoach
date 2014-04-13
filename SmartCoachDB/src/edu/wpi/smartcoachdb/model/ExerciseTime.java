package edu.wpi.smartcoachdb.model;

public class ExerciseTime {
	private int id;
	private String exerciseTime;

	public ExerciseTime(String exerciseTime) {
		super();
		this.exerciseTime = exerciseTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExerciseTime() {
		return exerciseTime;
	}

	public void setExerciseTime(String exerciseTime) {
		this.exerciseTime = exerciseTime;
	}

}
