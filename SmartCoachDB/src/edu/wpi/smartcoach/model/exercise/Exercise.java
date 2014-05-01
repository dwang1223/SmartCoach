package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class Exercise implements OptionModel {
	private int id;
	private String name;
	private String type;
	private String numberOfPersons;
	private String equipment;

	public Exercise() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exercise(int id, String exerciseName) {
		super();
		this.id = id;
		this.name = exerciseName;
	}

	public Exercise(String exerciseName, String exerciseType,
			String exerciseNumberOfPersons, String exerciseEquipment) {
		super();
		this.name = exerciseName;
		this.type = exerciseType;
		this.numberOfPersons = exerciseNumberOfPersons;
		this.equipment = exerciseEquipment;
	}

	public int getId() {
		return id;
	}	

	@Override
	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String exerciseName) {
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

	public void setExerciseNumberOfPersons(String numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	public String getExerciseEquipment() {
		return equipment;
	}

	public void setExerciseEquipment(String exerciseEquipment) {
		this.equipment = exerciseEquipment;
	}
	
	public String toString(){
		return getName();
	}
}
