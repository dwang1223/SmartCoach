package edu.wpi.smartcoach.model.exercise;


public class Exercise {
	private int id;
	private String name;
	private String type;
	private String numberOfPersons;
	private String equipment;
	
	private String formPresent;
	private String formContinuous;

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
			String exerciseNumberOfPersons, String exerciseEquipment, String present) {
		super();
		this.name = exerciseName;
		this.type = exerciseType;
		this.numberOfPersons = exerciseNumberOfPersons;
		this.equipment = exerciseEquipment;
		this.formPresent = present;
	}

	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the numberOfPersons
	 */
	public String getNumberOfPersons() {
		return numberOfPersons;
	}

	/**
	 * @return the equipment
	 */
	public String getEquipment() {
		return equipment;
	}

	/**
	 * @return the formPresent
	 */
	public String getFormPresent() {
		return formPresent;
	}

	/**
	 * @return the formContinuous
	 */
	public String getFormContinuous() {
		return formContinuous;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param numberOfPersons the numberOfPersons to set
	 */
	public void setNumberOfPersons(String numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	/**
	 * @param formPresent the formPresent to set
	 */
	public void setFormPresent(String formPresent) {
		this.formPresent = formPresent;
	}

	/**
	 * @param formContinuous the formContinuous to set
	 */
	public void setFormContinuous(String formContinuous) {
		this.formContinuous = formContinuous;
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
