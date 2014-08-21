package edu.wpi.smartcoach.model;

public class QuestionResponseOutline {
	
	private String questionID;
	private String[] responses;
	public QuestionResponseOutline(String questionID, String[] responses) {
		super();
		this.questionID = questionID;
		this.responses = responses;
	}
	/**
	 * @return the questionID
	 */
	public String getQuestionID() {
		return questionID;
	}
	/**
	 * @return the responses
	 */
	public String[] getResponses() {
		return responses;
	}
	/**
	 * @param questionID the questionID to set
	 */
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	/**
	 * @param responses the responses to set
	 */
	public void setResponses(String[] responses) {
		this.responses = responses;
	}
	
	

}
