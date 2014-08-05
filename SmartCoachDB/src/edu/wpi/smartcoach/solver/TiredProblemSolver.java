package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.view.Option;

public class TiredProblemSolver extends BaseProblemSolver {
	
	@Override
	public List<Solution> getSolution(Context ctx) {
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try{
			solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));
		}catch(Exception e){
			
		}

		solutions.addAll(Solutions.getAddToWeekendSolutions(states));
		
		solutions.add(new Solution("Download an app such as MyFitnessPal to track your exercises."));
		solutions.add(new Solution("Start by finding just 1 day that you can exercise over the next week for 10 minutes."));
		solutions.add(new Solution("Be physically active while doing chores (squats, stretching, situps)."));
		solutions.add(new Solution("Reward yourself with money or a gift when you achieve a goal for your exercise."));
		ArrayList<Option> options = new ArrayList<Option>();
				
		return solutions;
	}

}
