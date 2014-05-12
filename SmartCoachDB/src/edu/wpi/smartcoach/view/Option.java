package edu.wpi.smartcoach.view;

import edu.wpi.smartcoach.model.OptionModel;

public class Option {
	
	private OptionModel model;
	
	private boolean selected;
	
	public Option(OptionModel op){
		this.model = op;
	}
	
	public int getId(){
		return model.getId();
	}
	
	public String getText(){
		return model.getText();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public OptionModel getModel(){
		return model;
	}
	
	public void setSelected(boolean select){
		selected = select;
	}

}
