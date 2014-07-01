package edu.wpi.smartcoach.solver;

import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class QuestionStateEntry {

	private QuestionModel question;
	private ExerciseState state;
	
	private boolean isWeekend;
	
	public QuestionStateEntry(QuestionModel question, ExerciseState state, boolean weekend) {
		this.question = question;
		this.state = state;
		this.isWeekend = weekend;
	}

	/**
	 * @return the question
	 */
	public QuestionModel getQuestion() {
		return question;
	}

	/**
	 * @return the state
	 */
	public ExerciseState getState() {
		return state;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(QuestionModel question) {
		this.question = question;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(ExerciseState state) {
		this.state = state;
	}

	/**
	 * @return the isWeekend
	 */
	public boolean isWeekend() {
		return isWeekend;
	}

	/**
	 * @param isWeekend the isWeekend to set
	 */
	public void setWeekend(boolean isWeekend) {
		this.isWeekend = isWeekend;
	}
	
	
	
	

}
