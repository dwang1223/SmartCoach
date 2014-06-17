package edu.wpi.smartcoach.model.exercise;

public class ExerciseState {

	private int id;
	private Exercise exercise;
	private ExerciseLocation location;
	private ExerciseLocation weekendLocation;
	private ExerciseTime time;
	private ExerciseTime weekendTime;
	private int frequency;
	private int duration;
	private int weekendDuration;
	
	private boolean weekendDifferent;

	private boolean exerciseLiked;
	private boolean locationLiked;
	private boolean timeLiked;
	
	private boolean weekendExerciseLiked;
	private boolean weekendLocationLiked;
	private boolean weekendTimeLiked;
	private long recordTime;
	
	private boolean wouldIncrease;


	public ExerciseState(Exercise exercise, ExerciseLocation location,
			ExerciseTime timeID, int frequency, int duration,
			boolean exerciseLiked, boolean locationLiked, boolean timeLiked,
			long recordTime) {
		super();
		this.exercise = exercise;
		this.location = location;
		this.time = timeID;
		this.frequency = frequency;
		this.duration = duration;
		this.exerciseLiked = exerciseLiked;
		this.locationLiked = locationLiked;
		this.timeLiked = timeLiked;
		this.recordTime = recordTime;
	}
	
	public ExerciseState(){
		
	}
	

	/**
	 * @return the wouldIncrease
	 */
	public boolean wouldIncrease() {
		return wouldIncrease;
	}

	/**
	 * @param wouldIncrease the wouldIncrease to set
	 */
	public void setWouldIncrease(boolean wouldIncrease) {
		this.wouldIncrease = wouldIncrease;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public ExerciseLocation getLocation() {
		return location;
	}

	public void setLocation(ExerciseLocation location) {
		this.location = location;
	}

	public ExerciseTime getTime() {
		return time;
	}

	public void setTime(ExerciseTime time) {
		this.time = time;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isExerciseLiked() {
		return exerciseLiked;
	}

	public void setExerciseLiked(boolean exerciseLiked) {
		this.exerciseLiked = exerciseLiked;
	}

	public boolean isLocationLiked() {
		return locationLiked;
	}

	public void setLocationLiked(boolean locationLiked) {
		this.locationLiked = locationLiked;
	}

	public boolean isTimeLiked() {
		return timeLiked;
	}

	public void setTimeLiked(boolean timeLiked) {
		this.timeLiked = timeLiked;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}
	
	public boolean isLiked(){
		return isExerciseLiked() && isLocationLiked() && isTimeLiked();
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
	 * @return the weekendsDifferent
	 */
	public boolean isWeekendDifferent() {
		return weekendDifferent;
	}

	/**
	 * @param weekendLocation the weekendLocation to set
	 */
	public void setWeekendLocation(ExerciseLocation weekendLocation) {
		this.weekendLocation = weekendLocation;
	}

	/**
	 * @param weekendTime the weekendTime to set
	 */
	public void setWeekendTime(ExerciseTime weekendTime) {
		this.weekendTime = weekendTime;
	}

	/**
	 * @param weekendsDifferent the weekendsDifferent to set
	 */
	public void setWeekendDifferent(boolean weekendsDifferent) {
		this.weekendDifferent = weekendsDifferent;
	}

	/**
	 * @return the weekendDuration
	 */
	public int getWeekendDuration() {
		return weekendDuration;
	}

	/**
	 * @param weekendDuration the weekendDuration to set
	 */
	public void setWeekendDuration(int weekendDuration) {
		this.weekendDuration = weekendDuration;
	}

	@Override
	public ExerciseState clone(){
		return new ExerciseState(exercise, location, time, frequency, duration, false, false, false, 0);
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
	 * @param weekendExerciseLiked the weekendExerciseLiked to set
	 */
	public void setWeekendExerciseLiked(boolean weekendExerciseLiked) {
		this.weekendExerciseLiked = weekendExerciseLiked;
	}

	/**
	 * @param weekendLocationLiked the weekendLocationLiked to set
	 */
	public void setWeekendLocationLiked(boolean weekendLocationLiked) {
		this.weekendLocationLiked = weekendLocationLiked;
	}

	/**
	 * @param weekendTimeLiked the weekendTimeLiked to set
	 */
	public void setWeekendTimeLiked(boolean weekendTimeLiked) {
		this.weekendTimeLiked = weekendTimeLiked;
	}

	@Override 
	public String toString(){
		return String.format("%s, %s, %s, %dx%d mins", exercise.getName(), location.getSpecificLocation(), time.getTime(), frequency, duration );
	}
}
