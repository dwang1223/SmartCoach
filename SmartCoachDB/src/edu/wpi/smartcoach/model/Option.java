package edu.wpi.smartcoach.model;

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
		return model.getName();
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean select){
		selected = select;
	}

}
