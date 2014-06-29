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
		SINGLE, MULTIPLE, AT_LEAST_ONE
	};
	
	public static final String DEFAULT = "DEFAULT";
	
	private String id;
	private String title;
	private String prompt;
	private List<Option> options;
	private Option defaultResponse = null;
	
	private boolean isSorted;
	
	private QuestionType type;
	
		
	public OptionQuestionModel(String id, String title, String prompt, List<Option> options, QuestionType type, boolean sort){
		this.id = id;
		this.title = title;
		this.prompt = prompt;
		this.options = options;
		this.type = type;
		
		for(Option opt:options){
			if(opt.getId().equals(DEFAULT)){
				defaultResponse = opt;
				this.options.remove(defaultResponse);
				break;
			}
		}
		
		this.isSorted = sort;
		
		if(sort){		
			Collections.sort(this.options, new Comparator<Option>() {
				
				@Override
				public int compare(Option a, Option b){
					return a.getText().compareTo(b.getText());
				}
				
			});
		}
		
		if(defaultResponse != null){
			this.options.add(defaultResponse);
			defaultResponse.setSelected(true);
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
	
	public List<Option> getOptions(){
		return options;
	}
	
	public QuestionType getType(){
		return type;
	}

	
	public boolean hasDefault(){
		return defaultResponse != null;
	}
	
	public Option getDefault(){
		return defaultResponse;
	}
	
	public Option getSelectedOption(){
		List<Option> selected = getSelectedOptions();
		Option first = null;
		
		if(selected.size() > 0){
			first = selected.get(0);
		}
		
		return first;
	}
	
	public List<Option> getSelectedOptions(){		
		ArrayList<Option> selected = new ArrayList<Option>();
		
		for(Option option:options){
			if(option.isSelected()){
				selected.add(option);
			}
		}
		
		return selected;
	}
	

	public Object getSelectedValue(){
		List<Object> selected = getSelectedValues();		
		Object first = null;
		
		if(selected.size() > 0){
			first = selected.get(0);
		}
		
		return first;
	}
	
	public List<Object> getSelectedValues(){
		ArrayList<Object> responseList = new ArrayList<Object>();
		
		for(Option opt:options){
			if(opt.isSelected()){
				responseList.add(opt.getValue());
			}
		}
		
		return responseList;
	}
	
	public boolean hasMinimumResponses(){
		boolean hasMin = false;
		
		if(type == QuestionType.MULTIPLE){
			hasMin =  true;
		} else if (getSelectedValues().size() >= 1){
			hasMin = true;
		}
		
		return hasMin;
	}
	
	public void optionSelected(Option opt){
		if(type == QuestionType.MULTIPLE){
			opt.setSelected(!opt.isSelected());
		} else if(type == QuestionType.SINGLE){
			deselectAll();
			opt.setSelected(true);
		} else if(type == QuestionType.AT_LEAST_ONE){
			if(hasDefault()){
				if(opt == defaultResponse){
					deselectAll();
					opt.setSelected(true);
				} else {
					opt.setSelected(!opt.isSelected());
					defaultResponse.setSelected(false);
				}
			} else {
				opt.setSelected(!opt.isSelected());
			}
			if(getSelectedOptions().size() == 0){
				opt.setSelected(true);
			}
			
			
		}
	}
			
	/**
	 * @return the isSorted
	 */
	public boolean isSorted() {
		return isSorted;
	}

	/**
	 * @param isSorted the isSorted to set
	 */
	public void setSorted(boolean isSorted) {
		this.isSorted = isSorted;
	}

	private void deselectAll(){
		for(Option option:options){
			option.setSelected(false);
		}
	}
	
	@Override
	public String toString(){
		return String.format("%s:%s", id, prompt);
	}
}
