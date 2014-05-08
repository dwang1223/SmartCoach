package edu.wpi.smartcoach.solver;

import edu.wpi.smartcoach.model.OptionQuestionModel;

public interface ProblemSolver {
	
	public OptionQuestionModel getNextQuestion();
	public void submitResponse(OptionQuestionModel response);

	public boolean hasNextQuestion();

	public OptionQuestionModel getSolution();
}
