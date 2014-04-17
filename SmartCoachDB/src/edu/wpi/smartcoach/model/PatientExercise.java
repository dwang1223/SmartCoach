package edu.wpi.smartcoach.model;

public class PatientExercise {
	private int id;
	private int patientID;
	private int exerciseID;
	private int exerciseTimeID;
	private String patientPreference;

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

	public int getExerciseTimeID() {
		return exerciseTimeID;
	}

	public void setExerciseTimeID(int exerciseTimeID) {
		this.exerciseTimeID = exerciseTimeID;
	}

	public String getPatientPreference() {
		return patientPreference;
	}

	public void setPatientPreference(String patientPreference) {
		this.patientPreference = patientPreference;
	}

}
