package edu.wpi.smartcoach.model;

public class SocialNetworkSubmission {

	public static final String EXERCISE_TIME = "exercise_time";
	public static final String EXERCISE_MOTIVATION = "exercise_motivation";
	public static final String EXERCISE_BOREDOM = "exericse_boredom";
	public static final String EXERCISE_INJURY = "exericse_injury";
	public static final String EXERCISE_TIRED = "exercise_tired";
	public static final String EXERCISE_WEATHER = "exercise_weather";
	public static final String DIET = "diet";
	
	private String solution;	
	private String category;
	private QuestionResponseOutline[] path;

	public SocialNetworkSubmission(String category, String solution, QuestionResponseOutline[] path) {
		super();
		this.category = category;
		this.solution = solution;
		this.path = path;
	}

	/**
	 * @return the solution
	 */
	public String getSolution() {
		return solution;
	}

	/**
	 * @return the path
	 */
	public Object getPath() {
		return path;
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(String solution) {
		this.solution = solution;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(QuestionResponseOutline[] path) {
		this.path = path;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
