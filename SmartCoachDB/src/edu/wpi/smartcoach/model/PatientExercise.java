package edu.wpi.smartcoach.model;

public class PatientExercise {
	
	private Exercise exercise;
	private String location;
	private String time;
	private boolean liked;	
	
	public PatientExercise(Exercise exercise, String location, String time,boolean liked) {
		this.exercise = exercise;
		this.location = location;
		this.time = time;
		this.liked = liked;
	}

	public PatientExercise(){
		this(null, null, null, false);
	}
	
	public Exercise getExercise() {
		return exercise;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getTime() {
		return time;
	}
	
	public boolean isLiked() {
		return liked;
	}
	
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public void setLiked(boolean liked) {
		this.liked = liked;
	}

}
