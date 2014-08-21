package edu.wpi.smartcoach.model;

public interface QuestionModel {
	public String getPrompt();
	public String getId();
	public void setId(String newID);
	public QuestionResponseOutline getOutline();
	public QuestionModel clone();
}
