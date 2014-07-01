package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class TimeProblemSolver extends BaseProblemSolver {

	@Override
	public QuestionModel getSolution(Context ctx) {
		
		ArrayList<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();
		List<ExerciseState> states = new ArrayList<ExerciseState>();
		
		solutions.addAll(Solutions.getNewExerciseSolutions(states, ctx));
		solutions.addAll(Solutions.getNewLocationSolutions(states, ctx));
		solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));

		solutions.addAll(Solutions.getNewExerciseRecommendation(states, ctx));
		//solutions.addAll(Solutions.getWeekendIncreaseRecommendation(states, ctx));
		
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
