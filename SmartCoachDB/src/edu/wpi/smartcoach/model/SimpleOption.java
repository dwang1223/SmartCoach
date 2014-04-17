package edu.wpi.smartcoach.model;

public class SimpleOption implements OptionModel{
	
	int id;
	String name;
	
	public SimpleOption(int id, String name){
		this.id = id;
		this.name = name;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

}
