package edu.wpi.smartcoach.model.exercise;

import edu.wpi.smartcoach.model.OptionModel;

public class ExerciseTime {
	private int id;
	private String time;
	private String prepositionIn;

	public ExerciseTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ExerciseTime(int id, String time, String prepositionIn) {
		super();
		this.id = id;
		this.time = time;
		this.prepositionIn = prepositionIn;
	}



	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String exerciseTime) {
		this.time = exerciseTime;
	}
	
	

	/**
	 * @return the prepositionIn
	 */
	public String getPrepositionIn() {
		return prepositionIn;
	}

	/**
	 * @param prepositionIn the prepositionIn to set
	 */
	public void setPrepositionIn(String prepositionIn) {
		this.prepositionIn = prepositionIn;
	}

	public String toString(){
		return getTime();
	}

}
