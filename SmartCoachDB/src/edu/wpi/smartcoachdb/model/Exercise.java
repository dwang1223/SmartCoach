package edu.wpi.smartcoachdb.model;

public class Exercise {
	private int id;
	private String exerciseName;
	private String exerciseType;
	private String exerciseNumberOfPersons;
	private String exerciseEquipment;

	public Exercise(String exerciseName, String exerciseType,
			String exerciseNumberOfPersons, String exerciseEquipment) {
		super();
		this.exerciseName = exerciseName;
		this.exerciseType = exerciseType;
		this.exerciseNumberOfPersons = exerciseNumberOfPersons;
		this.exerciseEquipment = exerciseEquipment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExerciseName() {
		return exerciseName;
	}

	public void setExerciseName(String exerciseName) {
		this.exerciseName = exerciseName;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public String getExerciseNumberOfPersons() {
		return exerciseNumberOfPersons;
	}

	public void setExerciseNumberOfPersons(String exerciseNumberOfPersons) {
		this.exerciseNumberOfPersons = exerciseNumberOfPersons;
	}

	public String getExerciseEquipment() {
		return exerciseEquipment;
	}

	public void setExerciseEquipment(String exerciseEquipment) {
		this.exerciseEquipment = exerciseEquipment;
	}

}
