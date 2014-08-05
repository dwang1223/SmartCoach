package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.view.Option;

public class TimeProblemSolver extends BaseProblemSolver {

	@Override
	public List<Solution> getSolution(Context ctx) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		boolean children = prefs.getString("profile_kids", "no").equalsIgnoreCase("yes");
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		List<ExerciseState> states = new ArrayList<ExerciseState>();
		
		solutions.addAll(Solutions.getNewExerciseSolutions(states, ctx));
		solutions.addAll(Solutions.getNewLocationSolutions(states, ctx));
		solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));

		solutions.addAll(Solutions.getNewExerciseRecommendation(states, ctx));
		solutions.addAll(Solutions.getAddToWeekendSolutions(states));
		//solutions.addAll(Solutions.getWeekendIncreaseRecommendation(states, ctx));
		
		for(Solution s:solutions){
			if(s instanceof ExerciseSolution){
				ExerciseSolution es = (ExerciseSolution)s;
				
				String message = es.getMessage();
				String newMessage = String.format("%s Increase the intensity of the %s to help reduce time.", 
						message,
						es.getExercise().getName().toLowerCase());
				es.setMessage(newMessage);
				
				Iterator<ExerciseState> it = states.iterator();
				while(it.hasNext()){
					if(it.next().getExercise().equals(es.getExercise())){
						it.remove();
					}
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
		
		if(children){
			solutions.add(new Solution("Play with the children when you go out with them"));
			solutions.add(new Solution("Ask your partner or a sitter to watch your child while you exercise."));
			solutions.add(new Solution("Exercise at night once your child goes to bed."));

		}
		
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Try using the stairs instead of the elevator."));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Get up and walk around after a long time at work."));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Wake up early to exercise."));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Be physically active while doing chores (squats, stretching, situps)"));
		
		return solutions;
	}
	
}
