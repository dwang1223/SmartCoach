package edu.wpi.smartcoach.model;


public class Exercise implements OptionModel {
	private int id;
	private String name;
	private String type;
	private String numberOfPersons;
	private String equipment;

	public Exercise(int id, String name){
		this.id = id;
		this.name = name;
		//TODO: fill in remaining from db
	}
	
	public Exercise(String exerciseName, String exerciseType,
			String exerciseNumberOfPersons, String exerciseEquipment) {
		super();
		this.name = exerciseName;
		this.type = exerciseType;
		this.numberOfPersons = exerciseNumberOfPersons;
		this.equipment = exerciseEquipment;
	}

	@Override
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setExerciseName(String exerciseName) {
		this.name = exerciseName;
	}

	public String getExerciseType() {
		return type;
	}

	public void setExerciseType(String exerciseType) {
		this.type = exerciseType;
	}

	public String getExerciseNumberOfPersons() {
		return numberOfPersons;
	}

	public void setExerciseNumberOfPersons(String exerciseNumberOfPersons) {
		this.numberOfPersons = exerciseNumberOfPersons;
	}

	public String getExerciseEquipment() {
		return equipment;
	}

	public void setExerciseEquipment(String exerciseEquipment) {
		this.equipment = exerciseEquipment;
	}

}
