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
				es.setInfo("http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/");
				
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
			solution.setInfo("http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/");
			solutions.add(solution);
		}
		
		if(children){
			solutions.add(new Solution("Play active games with your children.","http://www.eatright.org/kids/article.aspx?id=6442469404"));
			solutions.add(new Solution("Ask your partner or a sitter to watch your child while you exercise."));
			solutions.add(new Solution("Exercise at night once your child goes to bed."));
			solutions.add(new Solution("Keep a pair of sneakers at work so you are prepared to go for a walk."));

		}
		
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Try using the stairs instead of the elevator."));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Make a plan to exercise on days you are not working (e.g., weekends)","http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, 
				"Set a timer to get up and walk for 5 mins after sitting for 2 hours or more", 
				"http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Wake up early to exercise."));
		solutions.add(new Solution(Solution.TYPE_DEFAULT, "Be physically active while doing chores (squats, stretching, situps)"));
		solutions.add(new Solution("Plan your exercise with a schedule. Write it in your calendar at the beginning of the week.",
				"http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		solutions.add(new Solution("Keep a journal of how you spend your time to identify where you might be able to fit in more exercise",
				"http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		return solutions;
	}
	
}
