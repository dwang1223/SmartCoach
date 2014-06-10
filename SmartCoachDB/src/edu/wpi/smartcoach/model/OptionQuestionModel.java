package edu.wpi.smartcoach.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import edu.wpi.smartcoach.view.Option;
import android.util.Log;

public class OptionQuestionModel implements QuestionModel{
	
	private static final String TAG = OptionQuestionModel.class.getSimpleName();
	
	public enum QuestionType {
		SINGLE, MULTIPLE
	};
	
	public static final int DEFAULT = -1;
	public static final int NO_LIMIT = -1;
	
	private String id;
	private String title;
	private String prompt;
	private List<Option> responses;
	private Option defaultResponse = null;
	
	private boolean isSorted;
	
	private QuestionType type;
	
	
	/**
	 * only applies if type is multiple
	 */
	private int min = 0;
	private int max = NO_LIMIT;
	
	public OptionQuestionModel(String id, String title, String prompt, List<? extends OptionModel> responses, QuestionType type, boolean sort){
		this.id = id;
		this.title = title;
		this.prompt = prompt;
		this.responses = new ArrayList<Option>();
		this.type = type;
		for(OptionModel opm:responses){
			Option op = new Option(opm);
			this.responses.add(op);
			if(opm.getId() == DEFAULT){
				defaultResponse = op;
				this.responses.remove(defaultResponse);
			}
		}
		
		this.isSorted = sort;
		
		if(sort){		
			Collections.sort(this.responses, new Comparator<Option>() {
				
				@Override
				public int compare(Option a, Option b){
					return a.getText().compareTo(b.getText());
				}
				
			});
		}
		
		if(defaultResponse != null){
			this.responses.add(defaultResponse);
		}
		
	}
	
	public OptionQuestionModel(String id, String title, String prompt, List<? extends OptionModel> responses, QuestionType type, int min, int max, boolean sort){
		this(id, title, prompt, responses, type, sort);
		
			this.min = Math.max(0, min);
			this.max = max;
			if(max < 1){
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
	
	public List<Option> getResponses(){
		return responses;
	}
	
	public QuestionType getType(){
		return type;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax(){
		return max;
	}
	
	public boolean hasDefault(){
		return defaultResponse != null;
	}
	
	public Option getDefault(){
		return defaultResponse;
	}
	
	public void setPrompt(String p){
		this.prompt = p;
	}
		
	public void setOptions(List<? extends OptionModel> newOptions){
		this.responses.clear();
		for(OptionModel opm:newOptions){
			Option op = new Option(opm);
			this.responses.add(op);
			if(opm.getId() == DEFAULT){
				defaultResponse = op;
			}
		}
	}
	
	public List<OptionModel> getOptions(){
		ArrayList<OptionModel> opms = new ArrayList<OptionModel>();
		for(Option op:responses){
			opms.add(op.getModel());
		}
		return opms;
	}
		
	public OptionModel getSelectedResponse(){
		for(Option opm:responses){
			if(opm.isSelected()){
				return opm.getModel();
			}
		}
		return null;
	}
	
	public List<OptionModel> getSelectedResponses(){
		ArrayList<OptionModel> responseList = new ArrayList<OptionModel>();
		for(Option opm:responses){
			if(opm.isSelected()){
				responseList.add(opm.getModel());
			}
		}
		return responseList;
	}
	
	public boolean hasMinimumResponses(){
		if(min == 0){
			return true;
		}
		int count = 0;
		for(Option opm:responses){
			if(opm.isSelected()){
				count++;
			}
		}
		return count >= min;
	}
	
	
	@Override
	public OptionQuestionModel clone(){
		List<OptionModel> responseModelList = new ArrayList<OptionModel>();
		for(Option op:responses){
			responseModelList.add(op.getModel());
		}
		return new OptionQuestionModel(
				id,
				title,
				prompt,
				responseModelList,
				type,
				min,
				max,
				isSorted);
	}
	
	@Override
	public String toString(){
		return String.format("%s:%s {%s}", id, prompt, responses.toString());
	}
}
