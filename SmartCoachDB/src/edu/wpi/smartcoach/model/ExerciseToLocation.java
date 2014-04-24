package edu.wpi.smartcoach.model;

public class ExerciseToLocation implements OptionModel {
	private int exerciseID;
	private int exerciseLocationID;

	@Override
	public int getId() {
		return exerciseID;
	}

	public void setID(int exerciseID) {
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
