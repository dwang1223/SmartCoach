package edu.wpi.smartcoach.model;

import java.util.List;
import java.util.Map.Entry;

public class QuestionModel {
	
	public enum QuestionType {
		SINGLE, MULTIPLE
	};
	
	public static final String DEFAULT = "any";
	public static final int NO_LIMIT = -1;
	
	private String id;
	private String title;
	private String prompt;
	private List<OptionModel> responses;
	private OptionModel defaultResponse = null;
	
	private QuestionType type;
	
	/**
	 * only applies if type is multiple
	 */
	private int max = NO_LIMIT;
	
	public QuestionModel(String id, String title, String prompt, List<OptionModel> responses, QuestionType type){
		this.id = id;
		this.title = title;
		this.prompt = prompt;
		this.responses = responses;
		this.type = type;
		
		for(OptionModel opm:responses){
			if(opm.getId().equals(DEFAULT)){
				defaultResponse = opm;
			}
		}
		
	}
	
	public QuestionModel(String id, String title, String prompt, List<OptionModel> responses, QuestionType type, int max){
		this(id, title, prompt, responses, type);
		if(type.equals(QuestionType.MULTIPLE)){
			this.max = max;
			if(max < 1){
				max = NO_LIMIT;
			}
		} else {
			max = NO_LIMIT;
		}
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
	
	public int getLimit(){
		return max;
	}
	
	public boolean hasDefault(){
		return defaultResponse != null;
	}
	
	public OptionModel getDefault(){
		return defaultResponse;
	}
}
