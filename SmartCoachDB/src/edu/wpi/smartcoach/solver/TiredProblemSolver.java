package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class TiredProblemSolver extends BaseProblemSolver {
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try{
			solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));
		}catch(Exception e){
			
		}
		
		solutions.add(new Solution("Download an app such as MyFitnessPal to track your exercises."));
		solutions.add(new Solution("Start by finding just 1 day that you can exercise over the next week for 10 minutes."));
		solutions.add(new Solution("Be physically active while doing chores (squats, stretching, situps)."));
		ArrayList<Option> options = new ArrayList<Option>();
		
		for(Solution soln:solutions){
			options.add(new Option(solutions.indexOf(soln)+"", soln));
		}
		
		
		if(options.isEmpty()){
			options.add(new Option("DEFAULT", "No Solutions found..."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", "Here are some things you can try:", 
				options, QuestionType.MULTIPLE, false, false);
	}

}
