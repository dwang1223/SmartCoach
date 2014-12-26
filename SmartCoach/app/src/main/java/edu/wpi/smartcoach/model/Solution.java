package edu.wpi.smartcoach.model;

/**
 * A solution that is given to the user after a problem solving session
 * @author Akshay
 *
 */
public class Solution {
	
	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_COMMUNITY = 1;
	
	protected int type;
	protected String message;
	protected String link;
	
	/**
	 * Constructor with specified type & info link
	 * @param type Either TYPE_COMMUNITY or TYPE_DEFAULT
	 * @param message the solution's message
	 * @param info The url of the info site
	 */
	public Solution(int type, String message, String info){
		this.type = type;
		this.message = message;
		this.link = info;
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
