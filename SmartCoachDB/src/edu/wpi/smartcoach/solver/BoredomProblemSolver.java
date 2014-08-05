package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import edu.wpi.smartcoach.model.ExerciseQuestions;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class BoredomProblemSolver extends BaseProblemSolver{

	private static final String TAG = BoredomProblemSolver.class.getSimpleName();

	
	@Override
	protected void addStateQuestions(ExerciseState state, boolean weekend){
		Log.d(TAG, "ovveridden state questions");
		OptionQuestionModel location = ExerciseQuestions.getInstance().getOptionQuestion("exercise_location");
		OptionQuestionModel time = ExerciseQuestions.getInstance().getOptionQuestion("exercise_time");
		OptionQuestionModel like = ExerciseQuestions.getInstance().getOptionQuestion("exercise_like");

		questions.add(new QuestionStateEntry(location, state, weekend));
		questions.add(new QuestionStateEntry(time, state, weekend));
		questions.add(new QuestionStateEntry(like, state, weekend));
	}
	
	
	
	@Override
	public List<Solution> getSolution(Context ctx) {
		List<Solution> solutions = new ArrayList<Solution>();
		solutions.addAll(Solutions.getBoredomSolutions(states, ctx));

		solutions.addAll(Solutions.getNewExerciseRecommendation(new ArrayList<ExerciseState>(), ctx));
		ArrayList<Option> options = new ArrayList<Option>();

		solutions.add(new Solution("Try to exercise at home when you are watching TV."));
		solutions.add(new Solution("Schedule a different exercise each week to change things up."));
		solutions.add(new Solution("Join a gym that also offers physical training services to add variety to workout."));
		
		for(Solution s:solutions){
			options.add(new Option(solutions.size()+"", s));
		}
				
		return solutions;
	}

}
