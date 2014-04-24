package edu.wpi.smartcoach.model;

public class ExerciseLocation implements OptionModel{
	private int id;
	private String specificLocation;
	private String locationType;

	public ExerciseLocation() {
		super();
	}

	public ExerciseLocation(String specificLocation, String locationType) {
		super();
		this.specificLocation = specificLocation;
		this.locationType = locationType;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return getSpecificLocation();
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
