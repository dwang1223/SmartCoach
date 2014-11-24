package edu.wpi.smartcoach.model;

public class Solution {
	
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_COMMUNITY = 1;
	
	protected int type;
	protected String message;
	protected String link;
	
	public Solution(String message){
		this(TYPE_DEFAULT, message);
	}
	
	public Solution(int type, String message){
		this(type, message, null);
	}
	
	public Solution(int type, String message, String info){
		this.type = type;
		this.message = message;
		this.link = info;
	}
	
	public Solution(String message, String info){
		this(TYPE_DEFAULT, message, info);
	}
	
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(int type){
		this.type = type;
	}
	
	public void setLink(String info){
		this.link = info;
	}
	
	public int getType(){
		return type;
	}
	
	public String getLink(){
		return link;
	}
	
	public String toString(){
		return message;
	}
	
	public Solution clone(){
		return new Solution(type, message, link);
	}

}
