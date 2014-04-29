package edu.wpi.smartcoach.model;

public class PatientExercise {

	private int id;
	private int patientID;
	private int exerciseID;
	private int exerciseLocationID;
	private int exerciseTimeID;
	private boolean liked;

	public PatientExercise(int patientID, int exerciseID,
			int exerciseLocationID, int exerciseTimeID, boolean liked) {
		super();
		this.patientID = patientID;
		this.exerciseID = exerciseID;
		this.exerciseLocationID = exerciseLocationID;
		this.exerciseTimeID = exerciseTimeID;
		this.liked = liked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(int exerciseID) {
		this.exerciseID = exerciseID;
	}

	public int getExerciseLocationID() {
		return exerciseLocationID;
	}

	public void setExerciseLocationID(int exerciseLocationID) {
		this.exerciseLocationID = exerciseLocationID;
	}

	public int getExerciseTimeID() {
		return exerciseTimeID;
	}

	public void setExerciseTimeID(int exerciseTimeID) {
		this.exerciseTimeID = exerciseTimeID;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

}
