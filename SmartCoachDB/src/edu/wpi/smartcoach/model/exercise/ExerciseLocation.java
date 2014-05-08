package edu.wpi.smartcoach.model.exercise;


public class ExerciseLocation {
	private int id;
	private String specificLocation;
	private String locationType;

	public ExerciseLocation() {
		super();
	}

	public ExerciseLocation(int id, String specificLocation, String locationType) {
		super();
		this.id = id;
		this.specificLocation = specificLocation;
		this.locationType = locationType;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getSpecificLocation() {
		return specificLocation;
	}

	public void setSpecificLocation(String specificLocation) {
		this.specificLocation = specificLocation;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}


}