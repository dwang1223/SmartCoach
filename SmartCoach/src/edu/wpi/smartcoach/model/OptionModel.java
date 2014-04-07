package edu.wpi.smartcoach.model;

public class OptionModel {
	
	private String id;
	private String text;
	
	private boolean selected;
	
	public OptionModel(String id, String text){
		this.id = id;
		this.text = text;		
	}
	
	public String getId(){
		return id;
	}
	
	public String getText(){
		return text;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean select){
		selected = select;
	}

}
