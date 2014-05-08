package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class ExerciseToLocation {
	private int exerciseID;
	private int exerciseLocationID;

	public ExerciseToLocation(int exerciseID, int exerciseLocationID) {
		super();
		this.exerciseID = exerciseID;
		this.exerciseLocationID = exerciseLocationID;
	}

	public int getId() {
		return exerciseID;
	}

	public void setID(int exerciseID) {
		this.exerciseID = exerciseID;
	}

	public int getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(int exerciseID) {
		this.exerciseID = exerciseID;
	}

	public int getLocationID() {
		return exerciseLocationID;
	}

	public void setLocationID(int exerciseLocationID) {
		this.exerciseLocationID = exerciseLocationID;
	}
}
