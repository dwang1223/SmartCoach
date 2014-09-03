package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.view.Option;

public class TiredProblemSolver extends BaseProblemSolver {
	
	@Override
	public List<Solution> getSolution(Context ctx) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		boolean children = prefs.getString("profile_kids", "no").equalsIgnoreCase("yes");
		
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		try{
			solutions.addAll(Solutions.getNewTimeSolutions(states, ctx));
		}catch(Exception e){
			
		}

		solutions.addAll(Solutions.getAddToWeekendSolutions(states));
		
		
		if(children){

			solutions.add(new Solution("Play active games with your children.","http://www.eatright.org/kids/article.aspx?id=6442469404"));
			solutions.add(new Solution("Ask your partner or a sitter to watch your child while you exercise."));

		}
		
		solutions.add(new Solution("Download an app such as MyFitnessPal to track your exercises.","http://www.cnn.com/2014/02/18/health/health-fitness-apps/"));
		solutions.add(new Solution("Start by finding just 1 day that you can exercise over the next week for 10 minutes."));
		solutions.add(new Solution("Be physically active while doing chores (squats, stretching, situps)."));
		solutions.add(new Solution("Turn on upbeat music right when you get home from work to increase your energy and motivation to be active."));
		solutions.add(new Solution("If you find you generally don't feel rested during the day, call your doctor to rule out a sleep disorder","http://www.fudiet.com/2014/03/its-time-to-do-something-about-your-sleep/"));
		solutions.add(new Solution("Reward yourself with money or a gift when you achieve a goal for your exercise."));
		solutions.add(new Solution(
				"Set a timer to get up and walk for 5 mins after sitting for 2 hours or more", 
				"http://www.fudiet.com/2012/04/four-words-we-should-never-say-again-i-dont-have-time/"));
		ArrayList<Option> options = new ArrayList<Option>();
				
		return solutions;
	}

}
