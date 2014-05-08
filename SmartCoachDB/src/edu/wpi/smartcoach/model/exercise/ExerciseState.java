package edu.wpi.smartcoach.model.exercise;

public class ExerciseState {

	private int id;
	private Exercise exerciseID;
	private ExerciseLocation locationID;
	private ExerciseTime timeID;
	private int frequency;
	private int duration;

	private boolean exerciseLiked;
	private boolean locationLiked;
	private boolean timeLiked;
	private long recordTime;

	public ExerciseState(Exercise exerciseID, ExerciseLocation locationID,
			ExerciseTime timeID, int frequency, int duration,
			boolean exerciseLiked, boolean locationLiked, boolean timeLiked,
			long recordTime) {
		super();
		this.exerciseID = exerciseID;
		this.locationID = locationID;
		this.timeID = timeID;
		this.frequency = frequency;
		this.duration = duration;
		this.exerciseLiked = exerciseLiked;
		this.locationLiked = locationLiked;
		this.timeLiked = timeLiked;
		this.recordTime = recordTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exercise getExerciseID() {
		return exerciseID;
	}

	public void setExerciseID(Exercise exerciseID) {
		this.exerciseID = exerciseID;
	}

	public ExerciseLocation getLocationID() {
		return locationID;
	}

	public void setLocationID(ExerciseLocation locationID) {
		this.locationID = locationID;
	}

	public ExerciseTime getTimeID() {
		return timeID;
	}

	public void setTimeID(ExerciseTime timeID) {
		this.timeID = timeID;
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

}
