package edu.wpi.smartcoach.model.exercise;


public class ExerciseLocation {
	private int id;
	private String specificLocation;
	private String locationType;
	private String formPreposition;

	public ExerciseLocation() {
		super();
	}

	public ExerciseLocation(int id, String specificLocation, String locationType, String preposition) {
		super();
		this.id = id;
		this.specificLocation = specificLocation;
		this.locationType = locationType;
		this.formPreposition = preposition;
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

	@Override
	public String toString(){
		return specificLocation;
	}
	
	public String getPreposition(){
		return formPreposition;
	}
	
	@Override
	public boolean equals(Object e){
		if(e == null){
			return false;
		}
		
		if(e instanceof ExerciseLocation){
			if(id == ((ExerciseLocation)e).getId()){
				return true;
			}
		}
		return false;
	}

}
