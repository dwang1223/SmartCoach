package edu.wpi.smartcoach.model;

public class Solution {
	
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_COMMUNITY = 1;
	
	protected int type;
	protected String message;
	protected String info;
	
	public Solution(String message){
		this(TYPE_DEFAULT, message);
	}
	
	public Solution(int type, String message){
		this(type, message, null);
	}
	
	public Solution(int type, String message, String info){
		this.type = type;
		this.message = message;
		this.info = info;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
	
	public String getInfo(){
		return info;
	}
	
	public String toString(){
		return message;
	}

}
