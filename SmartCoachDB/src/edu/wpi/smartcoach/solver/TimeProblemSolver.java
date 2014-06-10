package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.ExerciseSolution;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.exercise.ExerciseState;

public class TimeProblemSolver extends BaseProblemSolver {

	@Override
	public QuestionModel getSolution(Context ctx) {
		
		ArrayList<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();
		List<ExerciseState> states = new ArrayList<ExerciseState>(state.values());
		
		solutions.addAll(Solutions.getNewExerciseSolutions(states, ctx));
		solutions.addAll(Solutions.getNewLocationSolutions(states, ctx));
		solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));

		solutions.addAll(Solutions.getNewExerciseRecommendation(states, ctx));
		
		for(ExerciseSolution s:solutions){
			String message = s.getMessage();
			String newMessage = String.format("%s Increase the intensity of the %s to help reduce time.", 
					message,
					s.getExercise().getName().toLowerCase());
			s.setMessage(newMessage);
			
			Iterator<ExerciseState> it = states.iterator();
			while(it.hasNext()){
				if(it.next().getExercise().equals(s.getExercise())){
					it.remove();
				}
			}
		}
		
		for(ExerciseState state:states){
			ExerciseSolution solution = new ExerciseSolution(state);
			String message = String.format("Increase the intensity of the %s to help reduce time.",
					state.getExercise().getName().toLowerCase());
			solution.setMessage(message);
			solutions.add(solution);
		}
		
		
		
		
		
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		
		for(ExerciseSolution soln:solutions){
			options.add(new SimpleOption(solutions.indexOf(soln), soln));
		}
		
		if(options.isEmpty()){
			options.add(new SimpleOption(0, "No Solutions found..."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", "Here are some things you can try:", 
				options, QuestionType.SINGLE, false);
	}
	
}
