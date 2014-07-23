package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.ExerciseQuestions;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class BoredomProblemSolver extends BaseProblemSolver{

	private static final String TAG = BoredomProblemSolver.class.getSimpleName();

	
	@Override
	protected void addStateQuestions(ExerciseState state, boolean weekend){
		Log.d(TAG, "ovveridden state questions");
		OptionQuestionModel location = ExerciseQuestions.getInstance().getOptionQuestion("exercise_location");
		OptionQuestionModel like = ExerciseQuestions.getInstance().getOptionQuestion("exercise_like");

		questions.add(new QuestionStateEntry(location, state, weekend));
		questions.add(new QuestionStateEntry(like, state, weekend));
	}
	
	
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		List<ExerciseSolution> solutions = Solutions.getBoredomSolutions(states, ctx);

		solutions.addAll(Solutions.getNewExerciseRecommendation(new ArrayList<ExerciseState>(), ctx));
		ArrayList<Option> options = new ArrayList<Option>();
		
		for(ExerciseSolution s:solutions){
			options.add(new Option(solutions.size()+"", s));
		}
				
		return new OptionQuestionModel("solutions", "Solutions", 
				"Try some of these to make exercise a little more interesting",
				options, 
				QuestionType.MULTIPLE, false, false);
	}

}
