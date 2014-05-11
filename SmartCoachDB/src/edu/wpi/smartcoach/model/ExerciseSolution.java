package edu.wpi.smartcoach.model;

import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;

public class ExerciseSolution {
	
	private static int nextID = 0;
	
	private int id;
	
	private Exercise exercise;
	private ExerciseLocation location;
	private ExerciseTime time;
	
	private int frequency;
	private int duration;
	
	private String message;
	
	private long timeStamp;

	public ExerciseSolution(){
		id = nextID;
		nextID++;
	}
	
	public ExerciseSolution(Exercise exercise, ExerciseLocation location,
			ExerciseTime time, int frequency, int duration, String message,
			long timeStamp) {
		this();
		this.exercise = exercise;
		this.location = location;
		this.time = time;
		this.frequency = frequency;
		this.duration = duration;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	
	public ExerciseSolution(ExerciseState state){
		this(state.getExercise(),
			state.getLocation(),
			state.getTime(),
			state.getFrequency(),
			state.getDuration(),
			null,
			0);
	}

	public int getId() {
		return id;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public ExerciseLocation getLocation() {
		return location;
	}

	public ExerciseTime getTime() {
		return time;
	}

	public int getFrequency() {
		return frequency;
	}

	public int getDuration() {
		return duration;
	}

	public String getMessage() {
		return message;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public void setLocation(ExerciseLocation location) {
		this.location = location;
	}

	public void setTime(ExerciseTime time) {
		this.time = time;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}	
	
	@Override
	public String toString(){
		return message;
	}
}
