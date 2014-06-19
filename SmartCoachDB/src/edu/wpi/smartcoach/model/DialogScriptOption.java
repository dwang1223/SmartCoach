package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class DialogScriptOption implements OptionModel {
	
	private String text;
	private String next;
	private ArrayList<String> solutions;
	
	public DialogScriptOption(String text, String next, ArrayList<String> solns){
		this.text = text;
		this.next = next;
		this.solutions = solns;
	}	
	
	public DialogScriptOption(){
		this("default", "default", null);
	}

	
	/**
	 * @return the next
	 */
	public String getNext() {
		return next;
	}


	/**
	 * @return the solutions
	 */
	public ArrayList<String> getSolutions() {
		return solutions;
	}


	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}


	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}


	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(ArrayList<String> solutions) {
		this.solutions = solutions;
	}


	@Override
	public int getId() {
		return 0;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
