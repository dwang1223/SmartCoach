package edu.wpi.smartcoach.model;

import java.util.List;

public class SocialNetworkSubmission {

	public static final String CATEGORY_EXERCISE = "exercise";
	public static final String CATEGORY_DIET = "diet";
	
	private String solution;	
	private String category;
	private List<String> conditions;

	public SocialNetworkSubmission(String category, String solution, List<String> conditions) {
		super();
		this.category = category;
		this.solution = solution;
		this.conditions = conditions;
	}

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getConditions() {
        return conditions;
    }

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
}
