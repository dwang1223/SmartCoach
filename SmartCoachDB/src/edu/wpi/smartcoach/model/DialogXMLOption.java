package edu.wpi.smartcoach.model;

import edu.wpi.smartcoach.view.Option;

public class DialogXMLOption extends Option {
	
	String condition;	

	public DialogXMLOption(String id, String text, String condition) {
		super(id, text);
		
		this.condition = condition;
		
	}
	
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	
	
	

}
