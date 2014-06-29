package edu.wpi.smartcoach.model.exercise;

public class ExerciseState {

	private int id;
	private Exercise exercise;

	private boolean isOnWeekdays;
	private boolean isOnWeekends;

	private ExerciseLocation location;
	private ExerciseTime time;
	private int frequency;
	private int duration;

	private boolean exerciseLiked;
	private boolean locationLiked;
	private boolean timeLiked;

	private ExerciseLocation weekendLocation;
	private ExerciseTime weekendTime;
	private int weekendFrequency;
	private int weekendDuration;

	private boolean weekendExerciseLiked;
	private boolean weekendLocationLiked;
	private boolean weekendTimeLiked;

	private long recordTime;

	public ExerciseState() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the exercise
	 */
	public Exercise getExercise() {
		return exercise;
	}

	/**
	 * @return the isOnWeekdays
	 */
	public boolean isOnWeekdays() {
		return isOnWeekdays;
	}

	/**
	 * @return the isOnWeekends
	 */
	public boolean isOnWeekends() {
		return isOnWeekends;
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
	 * @return the locationLiked
	 */
	public boolean isLocationLiked() {
		return locationLiked;
	}

	/**
	 * @return the timeLiked
	 */
	public boolean isTimeLiked() {
		return timeLiked;
	}

	/**
	 * @return the weekendLocation
	 */
	public ExerciseLocation getWeekendLocation() {
		return weekendLocation;
	}

	/**
	 * @return the weekendTime
	 */
	public ExerciseTime getWeekendTime() {
		return weekendTime;
	}

	/**
	 * @return the weekendFrequency
	 */
	public int getWeekendFrequency() {
		return weekendFrequency;
	}

	/**
	 * @return the weekendDuration
	 */
	public int getWeekendDuration() {
		return weekendDuration;
	}

	/**
	 * @return the weekendExerciseLiked
	 */
	public boolean isWeekendExerciseLiked() {
		return weekendExerciseLiked;
	}

	/**
	 * @return the weekendLocationLiked
	 */
	public boolean isWeekendLocationLiked() {
		return weekendLocationLiked;
	}

	/**
	 * @return the weekendTimeLiked
	 */
	public boolean isWeekendTimeLiked() {
		return weekendTimeLiked;
	}

	/**
	 * @return the recordTime
	 */
	public long getRecordTime() {
		return recordTime;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param exercise
	 *            the exercise to set
	 */
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * @param isOnWeekdays
	 *            the isOnWeekdays to set
	 */
	public void setOnWeekdays(boolean isOnWeekdays) {
		this.isOnWeekdays = isOnWeekdays;
	}

	/**
	 * @param isOnWeekends
	 *            the isOnWeekends to set
	 */
	public void setOnWeekends(boolean isOnWeekends) {
		this.isOnWeekends = isOnWeekends;
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
	 * @param locationLiked
	 *            the locationLiked to set
	 */
	public void setLocationLiked(boolean locationLiked) {
		this.locationLiked = locationLiked;
	}

	/**
	 * @param timeLiked
	 *            the timeLiked to set
	 */
	public void setTimeLiked(boolean timeLiked) {
		this.timeLiked = timeLiked;
	}

	/**
	 * @param weekendLocation
	 *            the weekendLocation to set
	 */
	public void setWeekendLocation(ExerciseLocation weekendLocation) {
		this.weekendLocation = weekendLocation;
	}

	/**
	 * @param weekendTime
	 *            the weekendTime to set
	 */
	public void setWeekendTime(ExerciseTime weekendTime) {
		this.weekendTime = weekendTime;
	}

	/**
	 * @param weekendFrequency
	 *            the weekendFrequency to set
	 */
	public void setWeekendFrequency(int weekendFrequency) {
		this.weekendFrequency = weekendFrequency;
	}

	/**
	 * @param weekendDuration
	 *            the weekendDuration to set
	 */
	public void setWeekendDuration(int weekendDuration) {
		this.weekendDuration = weekendDuration;
	}

	/**
	 * @param weekendExerciseLiked
	 *            the weekendExerciseLiked to set
	 */
	public void setWeekendExerciseLiked(boolean weekendExerciseLiked) {
		this.weekendExerciseLiked = weekendExerciseLiked;
	}

	/**
	 * @param weekendLocationLiked
	 *            the weekendLocationLiked to set
	 */
	public void setWeekendLocationLiked(boolean weekendLocationLiked) {
		this.weekendLocationLiked = weekendLocationLiked;
	}

	/**
	 * @param weekendTimeLiked
	 *            the weekendTimeLiked to set
	 */
	public void setWeekendTimeLiked(boolean weekendTimeLiked) {
		this.weekendTimeLiked = weekendTimeLiked;
	}

	/**
	 * @param recordTime
	 *            the recordTime to set
	 */
	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public String toString() {
		return String.format("%s, %s, %s, %dx%d mins", exercise.getName(), location.getSpecificLocation(), time.getTime(), frequency, duration);
	}
}
