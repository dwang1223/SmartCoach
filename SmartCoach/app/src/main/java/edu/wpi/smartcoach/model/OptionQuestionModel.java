package edu.wpi.smartcoach.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A question with a list of options to pick from.
 * @author Akshay
 *
 */
public class OptionQuestionModel implements QuestionModel{
	
	private static final String TAG = OptionQuestionModel.class.getSimpleName();
	
	// Selection modes
	public enum QuestionType {
		SINGLE, MULTIPLE, AT_LEAST_ONE
	};
	
	public static final String DEFAULT = "DEFAULT";
	
	private String id;
	private String title;
	private String prompt;
	public List<Option> options;
	private Option defaultResponse = null;
	
	private boolean searchable = false;
	private boolean isSorted;
	
	private QuestionType type;
	
	/**
	 * Constructor
	 * @param id The id of the question
	 * @param title The question's title
	 * @param prompt The actual question
	 * @param options A list of Options to display with the question
	 * @param type The question's option selection mode
	 * @param sort Whether the options should be sorted alphabetically
	 * @param search Whether the question should display a search filter box
	 */
	public OptionQuestionModel(String id, String title, String prompt, List<Option> options, QuestionType type, boolean sort, boolean search){
		this.id = id;
		this.title = title;
		this.prompt = prompt;
		this.type = type;
		this.isSorted = sort;
		this.searchable = search;
		setOptions(options);
		
	}
	
	/**
	 * Set the id
	 */
	public void setId(String newID){
		this.id = newID;
	}
	
	/**
	 * Get the id
	 */
	public String getId(){
		return id;
	}
	
	/**
	 * Get the title
	 * @return the title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Get the prompt
	 */
	public String getPrompt(){
		return prompt;  
	}
	
	/**
	 * Get the list of options
	 * @return list of options
	 */
	public List<Option> getOptions(){
		return options;
	}
	
	/**
	 * Get the selection mode type
	 * @return selection type
	 */
	public QuestionType getType(){
		return type;
	}

	/**
	 * Get whether the question had a pre-selected default option
	 * @return whether there is a default selected option
	 */
	public boolean hasDefault(){
		return defaultResponse != null;
	}
	
	/**
	 * Get the default initially selected option
	 * @return default selected option
	 */
	public Option getDefault(){
		return defaultResponse;
	}
	
	/**
	 * Get the selected option, or the first selected option in the case of multiple selection
	 * @return The first selected option
	 */
	public Option getSelectedOption(){
		List<Option> selected = getSelectedOptions();
		Option first = null;
		
		if(selected.size() > 0){
			first = selected.get(0);
		}
		
		return first;
	}
	
	/**
	 * Get all of the selected options
	 * @return List of selected options
	 */
	public List<Option> getSelectedOptions(){		
		ArrayList<Option> selected = new ArrayList<Option>();
		
		for(Option option:options){
			if(option.isSelected()){
				selected.add(option);
			}
		}
		
		return selected;
	}
	
	/**
	 * Returns the value (Option.getValue()) of the selected options, or the value
	 * of the first selected option if multiple options are selected. This equivalent to getSelectedOption().getValue()
	 * @return value of the selected option
	 */
	public Object getSelectedValue(){
		List<Object> selected = getSelectedValues();		
		Object first = null;
		
		if(selected.size() > 0){
			first = selected.get(0);
		}
		
		return first;
	}
	
	/**
	 * Returns a list of the values of the selected options
	 * @return
	 */
	public List<Object> getSelectedValues(){
		ArrayList<Object> responseList = new ArrayList<Object>();
		
		for(Option opt:options){
			if(opt.isSelected()){
				responseList.add(opt.getValue());
			}
		}
		
		return responseList;
	}
	
	/**
	 * Returns whether the question has enough selected options to be considered 
	 * adequatley answered accoring to the selection model.
	 * @return whether there are enough responses
	 */
	public boolean hasMinimumResponses(){
		boolean hasMin = false;
		
		if(type == QuestionType.MULTIPLE){
			hasMin =  true;
		} else if (getSelectedValues().size() >= 1){
			hasMin = true;
		}
		
		return hasMin;
	}
	
	/**
	 * Set the list of options to the given list
	 * @param new options
	 */
	public void setOptions(List<Option> newOptions){
		this.options = newOptions;
		
		for(Option opt:options){
			if(opt.getId().equals(DEFAULT)){
				defaultResponse = opt;
				//this.options.remove(defaultResponse);
				break;
			}
		}
				
		if(isSorted){		
			Collections.sort(this.options, new Comparator<Option>() {
				
				@Override
				public int compare(Option a, Option b){
					return a.getText().compareTo(b.getText());
				}				
			});
		}
		
		if(defaultResponse != null){
			//this.options.add(defaultResponse);
			defaultResponse.setSelected(true);
		}	
	}
	
	/**
	 * Called by the list adapter when an option is selected. Applies/enforces selection rules
	 * @param opt The option the user tried to select
	 */
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

	/**
	 * de-select all options
	 */
	private void deselectAll(){
		for(Option option:options){
			option.setSelected(false);
		}
	}
	
	/**
	 * returns whether this question has the search filter enabled
	 * @return
	 */
	public boolean isSearchable(){
		return searchable;
	}
	
	@Override
	public String toString(){
		return String.format("%s:%s", id, prompt);
	}

	@Override
	public OptionQuestionModel clone(){
		List<Option> optionClone = new ArrayList<Option>();
		for(Option opt:options){
			optionClone.add(new Option(opt.getId(), opt.getValue()));
		}
		return new OptionQuestionModel(id, title, prompt, optionClone, type, isSorted, searchable);
	}
}
