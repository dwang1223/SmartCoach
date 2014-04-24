package edu.wpi.smartcoach.model;

import android.util.Log;

public class ProblemOption extends SimpleOption{

	private static final String TAG = ProblemOption.class.getSimpleName();
	
	private QuestionModel nextQuestion;
	
	public ProblemOption(int id, String name, QuestionModel next) {
		super(id, name);
		this.nextQuestion = next;
	}
	
	public QuestionModel getNextQuestion(){
		return nextQuestion;
	}	
	
	public String toString(){
		return getName() + " -> "+ nextQuestion == null?"NONE":nextQuestion.toString();
	}
}
