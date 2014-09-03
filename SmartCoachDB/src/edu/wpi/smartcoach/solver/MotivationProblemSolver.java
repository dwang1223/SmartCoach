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
		solutions.addAll(Solutions.getAddToWeekendSolutions(states));
		
		solutions.add(new Solution("Download an app such as MyFitnessPal to track your exercises.","http://www.cnn.com/2014/02/18/health/health-fitness-apps/"));
		solutions.add(new Solution("Plan a fun goal that will motivate you to start training  (e.g., climb a mountain, bike to a certain destination, etc)"));
		solutions.add(new Solution("Reward yourself with money or a gift when you achieve a goal for your exercise."));
		solutions.add(new Solution("Find a buddy to report your exercise to each week, and he/she will do the same to you for accountability"));
		solutions.add(new Solution("Find some free exercise videos on Youtube", "https://www.youtube.com/watch?v=OQ6NfFIr2jw"));
		solutions.add(new Solution("Start with very light exercise that doesn't feel overwhelming or exhausting, e.g., leisurely walk, light yoga"));
		solutions.add(new Solution("Plan your exercise with a schedule. Write it in your calendar at the beginning of the week.",
				"http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		solutions.add(new Solution("Start a Couch to 5K plan to have a goal to strive for.",
				"http://www.coolrunning.com/engine/2/2_3/181.shtml"));
		ArrayList<Option> options = new ArrayList<Option>();
		
		return solutions;
	}


}
