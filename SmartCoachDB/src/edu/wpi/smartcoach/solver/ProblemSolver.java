package edu.wpi.smartcoach.solver;

import edu.wpi.smartcoach.model.QuestionModel;

public interface ProblemSolver {
	
	public QuestionModel getNextQuestion();
	public void submitResponse(QuestionModel response);

	public boolean hasNextQuestion();

	public QuestionModel getSolution();
}
