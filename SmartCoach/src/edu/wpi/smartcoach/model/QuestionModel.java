package edu.wpi.smartcoach.model;

import java.util.List;
import java.util.Map.Entry;

public class QuestionModel {
	
	public enum QuestionType {
		SINGLE, MULTIPLE
	};
	
	public static final String DEFAULT = "any";
	
	private String id;
	private String title;
	private String prompt;
	private List<OptionModel> responses;
	private QuestionType type;
	
	public QuestionModel(String id, String title, String prompt, List<OptionModel> responses, QuestionType type){
		this.id = id;
		this.title = title;
		this.prompt = prompt;
		this.responses = responses;
		this.type = type;
	}
	
	public String getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getPrompt(){
		return prompt;
	}
	
	public List<OptionModel> getResponses(){
		return responses;
	}
	
	public QuestionType getType(){
		return type;
	}
}
