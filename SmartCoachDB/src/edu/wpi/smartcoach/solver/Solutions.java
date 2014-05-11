package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import edu.wpi.smartcoach.model.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseProfile;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class Solutions {
	
	private static final int MIN_FREQUENCY = 2; //at least two days/week
	private static final int MAX_FREQUENCY = 7;
	
	private static final int MIN_DURATION = 10; //at least 10 minutes per day
	private static final float DURATION_CHANGE_FACTOR = 0.1f; // increase by 10%
	private static final int MIN_DURATION_CHANGE = 5; //change by at least 5 minutes
	private static final int MAX_DURATION = 60*24; //can't exercise more than 24hrs in a day
	
	public static List<ExerciseSolution> getIncreaseTimeSolutions(List<ExerciseState> states){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();
		
		for(ExerciseState state:states){
			if(state.isLiked() && state.wouldIncrease()){
				ExerciseSolution s = new ExerciseSolution(state);
				
				int duration = s.getDuration(); //10% increase
				duration += Math.max(MIN_DURATION_CHANGE, s.getDuration()*DURATION_CHANGE_FACTOR);
				duration = Math.max(MIN_DURATION, Math.min(MAX_DURATION, duration));
				
				int frequency = s.getFrequency();
				frequency = Math.max(MIN_FREQUENCY, Math.max(MAX_FREQUENCY, frequency));
				
				s.setDuration(duration);
				s.setFrequency(frequency);
				
				String durationFormat = String.format("%d:%02d", (duration/60), (duration%60));
				String message = String.format("Continue to %s %s in the %s %d days per week, and increase the duration to %s.",
						s.getExercise().getFormInfinitive(),
						s.getLocation().getPreposition(),
						s.getTime().getTime().toLowerCase(),
						s.getFrequency(),
						durationFormat);
				
				s.setMessage(message);
				
				solutionList.add(s);				
			}
		}
		
		return solutionList;
		
	}
	
	public static List<ExerciseSolution> getNewExerciseSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState state:states){
			if(!state.isExerciseLiked()){
				ExerciseSolution s = new ExerciseSolution(state);
				
				String[] ids = prefs.getString("profile_exercise_enjoy", "").split(",");

				ArrayList<Exercise> liked = new ArrayList<Exercise>();
				for(String sId:ids){
					try {
						int id = Integer.parseInt(sId);
						if(ExerciseToLocationService.getInstance().getLocationListByExercise(id).contains(state.getLocation())){ //if it can be done in the same location
							liked.add(ExerciseService.getInstance().getExercise(id));	
						}
					} catch(Exception e){}
					
				}
				
				for(ExerciseState existing:states){ //remove exercises patient is already doing
					liked.remove(existing.getExercise());
				}
								
				if(liked.size() > 0){
					Exercise newExercise = liked.get((int)(Math.random()*liked.size()));
					s.setExercise(newExercise);
					
					String message = String.format("Instead of %s, try to %s.");
					s.setMessage(message);

					solutionList.add(s);	
				}
							
			}
		}		
		return solutionList;
	}
	
	public static List<ExerciseSolution> getNewLocationSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState state:states){
			if(!state.isLocationLiked()){
				ExerciseSolution s = new ExerciseSolution(state);
				
				String[] ids = prefs.getString("profile_exercise_location", "").split(",");
				ArrayList<ExerciseLocation> liked = new ArrayList<ExerciseLocation>();
				for(String id:ids){
					try {
						liked.add(ExerciseLocationService.getInstance().getLocation(Integer.parseInt(id)));
					} catch(Exception e){
						
					}
				}
				List<ExerciseLocation> possible = ExerciseToLocationService.getInstance().getLocationListByExercise(s.getExercise().getId());					
				
				possible.remove(s.getLocation());
				
				ArrayList<ExerciseLocation> intersect = new ArrayList<ExerciseLocation>(); //locations the user likes, and can do the exercise at
				for(ExerciseLocation l:liked){
					if(possible.contains(l)){
						intersect.add(l);
					}
				}
				
				ExerciseLocation newLoc = null;
				
				if(intersect.size() > 0){//get a liked location if possible
					newLoc = intersect.get(0);
				} else if(possible.size() > 0) {
					newLoc = possible.get((int)(Math.random()*possible.size()));//get the next possible location
				}
				
				if(newLoc != null){
					s.setLocation(newLoc);
					
					String message = String.format("Try to %s %s instead of %s.", 
							s.getExercise().getFormInfinitive(),
							s.getLocation().getPreposition(),
							state.getLocation().getPreposition()							
							);
					s.setMessage(message);
					solutionList.add(s);
				}
							
			}
		}		
		return solutionList;
	}
	
	public static List<ExerciseSolution> getNewTimeSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState state:states){
			if(!state.isLocationLiked()){
				ExerciseSolution s = new ExerciseSolution(state);
				
				String[] ids = prefs.getString("profile_exercise_when", "").split(",");
				ArrayList<ExerciseTime> liked = new ArrayList<ExerciseTime>();
				for(String id:ids){
					try{
						liked.add(ExerciseTimeService.getInstance().getExerciseTime(Integer.parseInt(id)));
					} catch(Exception e){}
				}
				liked.remove(s.getTime());
				
				ExerciseTime newTime = null;
				
				if(liked.size() > 0){
					newTime = liked.get(0);
				} else {
					List<ExerciseTime> allTimes = ExerciseTimeService.getInstance().getAllDataFromTable(); //pick a time that is 2 after the current one (warp around)
					newTime = allTimes.get((allTimes.indexOf(s.getTime())+2)%allTimes.size());
				}
				
				s.setTime(newTime);
				
				String message = String.format("Try to %s %s in the %s instead of in the %s.",
						s.getExercise().getFormInfinitive(),
						s.getLocation().getPreposition(),
						s.getTime().getTime().toLowerCase(),
						state.getTime().getTime().toLowerCase());
				
				s.setMessage(message);
							
			}
		}		
		return solutionList;
	}
	
	public static List<ExerciseSolution> getBoredomSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		int groupPreference = Integer.parseInt(prefs.getString("profile_exercise_who", "-1"));
		
		
		for(ExerciseState state:states){

			String message = "";
			
			switch(groupPreference){
				case ExerciseProfile.ALONE:
					if(state.getLocation().getLocationType().equals("Outdoors")){
						message = String.format("Try listening to some music while you %s.",
								state.getExercise().getFormInfinitive());
					} else {
						message = String.format("Try watching some T.V. while you %s %s.", 
								state.getExercise().getFormInfinitive(),
								state.getLocation().getPreposition());
					}
					break;
				case ExerciseProfile.FRIEND:
					message = String.format("Try to find a friend to %s with.",
							state.getExercise().getFormInfinitive());
					break;
				default:
					message = String.format("Try to find a group of friends to %s with.",
							state.getExercise().getFormInfinitive());
					break;					
			}
			
			ExerciseSolution solution = new ExerciseSolution(state);
			solution.setMessage(message);
			solutionList.add(solution);
			
		}
		
		return solutionList;
	}

}
