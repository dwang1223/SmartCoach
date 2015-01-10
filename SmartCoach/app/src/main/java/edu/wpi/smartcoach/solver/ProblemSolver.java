package edu.wpi.smartcoach.solver;

import android.content.Context;

import java.util.List;

import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;

public interface ProblemSolver {

    public QuestionModel getNextQuestion();

    public void submitResponse(QuestionModel response);

    public boolean isFirstQuestion();

    public boolean hasNextQuestion();

    public boolean isBackAllowed();

    public void back();

    public List<String> getConditions();

    public List<String> getConditionsRecursive();

    public List<Solution> getSolution(Context ctx);

}
