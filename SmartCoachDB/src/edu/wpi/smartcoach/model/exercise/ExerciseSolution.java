package edu.wpi.smartcoach.model.exercise;


/**
 * A solution to the problems identified during problem solving sessions.
 * Has an exercise, location, time, and message.
 * @author akshay
 *
 */
public class ExerciseSolution {
	
	private Exercise exercise;
	private ExerciseLocation location;
	private ExerciseTime time;
	
	
	private int frequency;
	private int duration;
	
	private String message;
	private String reminder;
	
	private long timeStamp;

	/**
	 * Default Constructor
	 */
	public ExerciseSolution(){

	}
	
	/**
	 * Construct with all fields
	 * @param exercise The recommended exercise
	 * @param location The recommended location
	 * @param time The recommended time of day
	 * @param frequency Frequency of exercise, in days per week
	 * @param duration Duration of exercise in minutes
	 * @param message The message to show to the user
	 * @param timeStamp When this object was created
	 */
	public ExerciseSolution(Exercise exercise, ExerciseLocation location,
			ExerciseTime time, int frequency, int duration, String message,String reminder,
			long timeStamp) {
		this();
		this.exercise = exercise;
		this.location = location;
		this.time = time;
		this.frequency = frequency;
		this.duration = duration;
		this.message = message;
		this.reminder = reminder;
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Initializes a ExerciseSolution using an ExerciseState
	 * Copies the exercise, location, time, frequency, and duration
	 * @param state
	 */
	public ExerciseSolution(ExerciseState state){
		this(state.getExercise(),
			state.getLocation(),
			state.getTime(),
			state.getFrequency(),
			state.getDuration(),
			null,
			null,
			0);
	}


	/**
	 * @return the reminder
	 */
	public String getReminder() {
		return reminder;
	}

	/**
	 * @param reminder the reminder to set
	 */
	public void setReminder(String reminder) {
		this.reminder = reminder;
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
