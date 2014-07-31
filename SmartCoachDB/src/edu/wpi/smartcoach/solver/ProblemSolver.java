package edu.wpi.smartcoach.solver;

import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;

public interface ProblemSolver {
	
	public QuestionModel getNextQuestion();
	public void submitResponse(QuestionModel response);

	public boolean isFirstQuestion();
	public boolean hasNextQuestion();
	public boolean isBackAllowed();
	public void back();

	public List<Solution> getSolution(Context ctx);
}
