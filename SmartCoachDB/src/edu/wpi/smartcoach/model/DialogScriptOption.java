package edu.wpi.smartcoach.model;

import java.util.ArrayList;

import edu.wpi.smartcoach.view.Option;

public class DialogScriptOption extends Option {
	
	private String text;
	private String next;
	private ArrayList<String> solutions;
	
	public DialogScriptOption(String text, String next, ArrayList<String> solns){
		super(text, text);
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
	public String getText() {
		// TODO Auto-generated method stub
		return text;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public String toString(){
		return text;
	}
	
	

}
