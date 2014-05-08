package edu.wpi.smartcoach.model;

public class TimeQuestionModel implements QuestionModel {
	
	private String id;
	private String title;
	private String prompt;
	
	private int response = 0;
	
	public TimeQuestionModel(String id, String title, String prompt) {
		super();
		this.id = id;
		this.title = title;
		this.prompt = prompt;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the prompt
	 */
	public String getPrompt() {
		return prompt;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @param prompt the prompt to set
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	public void setResponse(int hours, int minutes){
		response = hours*60 + minutes;
	}
	
	public int getResponse(){
		return response;
	}
	
	@Override
	public TimeQuestionModel clone(){
		return new TimeQuestionModel(id, title, prompt);
	}
}
