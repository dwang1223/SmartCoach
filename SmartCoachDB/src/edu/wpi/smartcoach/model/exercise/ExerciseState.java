package edu.wpi.smartcoach.model.exercise;

public class ExerciseState {

	private int id;
	private Exercise exercise;
	private ExerciseLocation location;
	private ExerciseTime time;
	private int frequency;
	private int duration;

	private boolean exerciseLiked;
	private boolean locationLiked;
	private boolean timeLiked;
	private long recordTime;

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

}
