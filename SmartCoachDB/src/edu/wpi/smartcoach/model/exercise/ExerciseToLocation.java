package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class ExerciseToLocation implements OptionModel {
	private int exerciseID;
	private int exerciseLocationID;

	public ExerciseToLocation(int exerciseID, int exerciseLocationID) {
		super();
		this.exerciseID = exerciseID;
		this.exerciseLocationID = exerciseLocationID;
	}

	@Override
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

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
