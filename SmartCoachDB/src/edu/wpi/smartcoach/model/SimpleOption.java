package edu.wpi.smartcoach.model;

public class SimpleOption implements OptionModel{
	
	int id;
	Object value;
	
	public SimpleOption(int id, Object val){
		this.id = id;
		this.value = val;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getText(){
		return value.toString();
	}

	@Override
	public Object getValue() {
		return value;
	}

}
