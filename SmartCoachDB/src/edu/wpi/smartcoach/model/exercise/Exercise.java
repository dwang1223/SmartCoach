package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class Exercise {
	private int id;
	private String name;
	private String type;
	private String numberOfPersons;
	private String equipment;
	
	private String formInfinitive;

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
			String exerciseNumberOfPersons, String exerciseEquipment, String infinitive) {
		super();
		this.name = exerciseName;
		this.type = exerciseType;
		this.numberOfPersons = exerciseNumberOfPersons;
		this.equipment = exerciseEquipment;
		this.formInfinitive = infinitive;
	}

	/**
	 * @return the formInfinitive
	 */
	public String getFormInfinitive() {
		return formInfinitive;
	}

	/**
	 * @param formInfinitive the formInfinitive to set
	 */
	public void setFormInfinitive(String formInfinitive) {
		this.formInfinitive = formInfinitive;
	}

	public int getId() {
		return id;
	}	

	public String getName(){
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
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object e){
		if(e == null){
			return false;
		}
		if(e instanceof Exercise){
			return id == ((Exercise)e).getId();
		} else {
			return false;
		}
		
	}


}
