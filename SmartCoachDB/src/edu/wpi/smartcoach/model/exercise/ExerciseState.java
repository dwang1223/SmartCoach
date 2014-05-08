package edu.wpi.smartcoach.model.exercise;

public class ExerciseState {

	private int id;
	private long recordTime;
	private Exercise exercise;
	private ExerciseLocation location;
	private ExerciseTime time;
	private int frequency;
	private int duration;

	private boolean exerciseLiked;
	private boolean timeLiked;
	private boolean locationLiked;

	/**
	 * Construct using all fields
	 */
	public ExerciseState(int id, long recordTime, Exercise exercise, ExerciseLocation location, ExerciseTime time, int frequency, int duration,
			boolean exerciseLiked, boolean timeLiked, boolean locationLiked) {
		super();
		this.id = id;
		this.recordTime = recordTime;
		this.exercise = exercise;
		this.location = location;
		this.time = time;
		this.frequency = frequency;
		this.duration = duration;
		this.exerciseLiked = exerciseLiked;
		this.timeLiked = timeLiked;
		this.locationLiked = locationLiked;
	}

	/**
	 * Construct using time of record creation
	 * 
	 * @param recordTime
	 */
	public ExerciseState(long recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the recordTime
	 */
	public long getRecordTime() {
		return recordTime;
	}

	/**
	 * @return the exercise
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * @return the location
	 */
	public ExerciseLocation getLocation() {
		return location;
	}

	/**
	 * @return the time
	 */
	public ExerciseTime getTime() {
		return time;
	}

	/**
	 * @return the frequency
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return the exerciseLiked
	 */
	public boolean isExerciseLiked() {
		return exerciseLiked;
	}

	/**
	 * @return the timeLiked
	 */
	public boolean isTimeLiked() {
		return timeLiked;
	}

	/**
	 * @return the locationLiked
	 */
	public boolean isLocationLiked() {
		return locationLiked;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param recordTime
	 *            the recordTime to set
	 */
	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	/**
	 * @param exercise
	 *            the exercise to set
	 */
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(ExerciseLocation location) {
		this.location = location;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(ExerciseTime time) {
		this.time = time;
	}

	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * @param exerciseLiked
	 *            the exerciseLiked to set
	 */
	public void setExerciseLiked(boolean exerciseLiked) {
		this.exerciseLiked = exerciseLiked;
	}

	/**
	 * @param timeLiked
	 *            the timeLiked to set
	 */
	public void setTimeLiked(boolean timeLiked) {
		this.timeLiked = timeLiked;
	}

	/**
	 * @param locationLiked
	 *            the locationLiked to set
	 */
	public void setLocationLiked(boolean locationLiked) {
		this.locationLiked = locationLiked;
	}

}
