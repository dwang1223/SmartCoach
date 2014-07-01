package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class MotivationProblemSolver extends BaseProblemSolver {
	
	private static final String TAG = MotivationProblemSolver.class.getSimpleName();
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		
		ArrayList<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();
		List<ExerciseState> states = new ArrayList<ExerciseState>();
		
		solutions.addAll(Solutions.getIncreaseTimeSolutions(states));
		solutions.addAll(Solutions.getNewExerciseSolutions(states, ctx));
		solutions.addAll(Solutions.getNewLocationSolutions(states, ctx));
		solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));
		solutions.addAll(Solutions.getNewExerciseRecommendation(states, ctx));
		ArrayList<Option> options = new ArrayList<Option>();
		
		for(ExerciseSolution soln:solutions){
			options.add(new Option(solutions.indexOf(soln)+"", soln));
		}
		
		if(options.isEmpty()){
			options.add(new Option("DEFAULT", "No Solutions found..."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", "Here are some things you can try:", 
				options, QuestionType.MULTIPLE, false);
	}


}
