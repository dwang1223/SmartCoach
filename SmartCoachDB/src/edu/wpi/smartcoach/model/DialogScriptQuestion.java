package edu.wpi.smartcoach.model;

import java.util.ArrayList;

public class DialogScriptQuestion extends OptionQuestionModel {
	
	private String id;
	private ArrayList<DialogScriptOption> options;
	
	public DialogScriptQuestion(String id, String text, ArrayList<DialogScriptOption> options){
		super(id, "Question", text, options, QuestionType.SINGLE,1, 1, false);
		this.id = id;
		this.options = options; 
	}


}
