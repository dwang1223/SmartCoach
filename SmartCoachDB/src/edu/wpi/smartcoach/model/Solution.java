package edu.wpi.smartcoach.model;

public class Solution {
	
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_COMMUNITY = 1;
	
	protected int type;
	protected String message;
	
	public Solution(String message){
		this(TYPE_DEFAULT, message);
	}
	
	public Solution(int type, String message){
		this.type = type;
		this.message = message;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	public String toString(){
		return message;
	}

}
