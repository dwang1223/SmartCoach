package edu.wpi.smartcoach.model;

/**
 * A question that is shown to the user
 * @author Akshay
 */
public interface QuestionModel {
	public String getPrompt();	
	public String getId();
	public void setId(String newID);
	public QuestionResponseOutline getOutline();
	public QuestionModel clone();
}
