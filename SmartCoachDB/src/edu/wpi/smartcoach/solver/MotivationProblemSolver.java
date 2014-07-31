package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.view.Option;

public class MotivationProblemSolver extends BaseProblemSolver {
	
	private static final String TAG = MotivationProblemSolver.class.getSimpleName();
	
	@Override
	public List<Solution> getSolution(Context ctx) {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		//List<ExerciseState> states = new ArrayList<ExerciseState>();

		solutions.addAll(Solutions.getIncreaseDurationSolutions(states));
		solutions.addAll(Solutions.getIncreaseFrequencySolutions(states));
		solutions.addAll(Solutions.getNewExerciseSolutions(states, ctx));
		solutions.addAll(Solutions.getNewLocationSolutions(states, ctx));
		solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));
		solutions.addAll(Solutions.getNewExerciseRecommendation(states, ctx));
		
		solutions.add(new Solution("Download an app such as MyFitnessPal to track your exercises."));
		
		ArrayList<Option> options = new ArrayList<Option>();
		
		return solutions;
	}


}
