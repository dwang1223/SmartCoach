package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.wpi.smartcoach.model.ExerciseQuestions;
import edu.wpi.smartcoach.model.Solution;
import edu.wpi.smartcoach.model.exercise.Equipment;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseSolution;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class Solutions {
	
	private static final String TAG = Solutions.class.getSimpleName();
	
	private static final int MIN_FREQUENCY = 2; //at least two days/week
	private static final int MAX_FREQUENCY = 7;
	
	private static final int MIN_DURATION = 10; //at least 10 minutes per day
	private static final float DURATION_CHANGE_FACTOR = 0.1f; // increase by 10%
	private static final int MIN_DURATION_CHANGE = 5; //change by at least 5 minutes
	private static final int MAX_DURATION = 60*24; //can't exercise more than 24hrs in a day

	/*
	public static List<ExerciseSolution> getIncreaseTimeSolutions(List<ExerciseState> states){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();
		
		for(ExerciseState state:states){
			if(state.isIncreaseDuration()){
				ExerciseSolution s = new ExerciseSolution(state);
				
				int duration = s.getDuration(); //10% increase
				duration += Math.max(MIN_DURATION_CHANGE, s.getDuration()*DURATION_CHANGE_FACTOR);
				duration = Math.max(MIN_DURATION, Math.min(MAX_DURATION, duration));
				
				int frequency = s.getFrequency();
				frequency++;
				frequency = Math.max(MIN_FREQUENCY, Math.min(MAX_FREQUENCY, frequency));
				
				s.setDuration(duration);
				s.setFrequency(frequency);
				
				String durationFormat = String.format("%d:%02d", (duration/60), (duration%60));
				String message = String.format("Continue to %s %s in the %s %d days per week, and increase the duration to %s.",
						s.getExercise().getFormPresent(),
						s.getLocation().getPreposition(),
						s.getTime().getTime().toLowerCase(),
						s.getFrequency(),
						durationFormat);
				
				String reminder = String.format("%s in the %s %s", 
						s.getExercise().getFormPresent(),
						s.getLocation().getPreposition(),
						s.getTime().getTime().toLowerCase());
				
				s.setMessage(message);
				s.setReminder(reminder);
				solutionList.add(s);				
			}
		}
		
		return solutionList;
		
	}
*/
	
	public static List<Solution> getAddToWeekendSolutions(List<ExerciseState> states){
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		
		for(ExerciseState state:states){
			if(!state.isOnWeekends()){
				String message	= String.format("Add %s to your weekend activity plan.", 
						state.getExercise().getFormContinuous().toLowerCase());
				solutions.add(new Solution(message));
			}
		}
		
		
		return solutions;
	}
	
	public static List<ExerciseSolution> getIncreaseDurationSolutions(List<ExerciseState> states){
		List<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();
		
		for(ExerciseState state:states){
			if(state.isOnWeekdays() && state.isIncreaseDuration()){
				solutions.add(increaseDuration(state, false));
			}
			
			if(state.isOnWeekends() && state.isWeekendIncreaseDuration()){
				solutions.add(increaseDuration(state, true));
			}
		}
		
		return solutions;
	}
	
	private static ExerciseSolution increaseDuration(ExerciseState state, boolean weekend){
		int originalDuration = weekend?state.getWeekendDuration():state.getDuration();
		int duration = originalDuration; //10% increase
		duration += Math.max(MIN_DURATION_CHANGE, state.getDuration()*DURATION_CHANGE_FACTOR);
		duration = Math.max(MIN_DURATION, Math.min(MAX_DURATION, duration));
		String weekendStr = ExerciseQuestions.getInstance().getWeekendString(state, weekend);
		
		
		String message = String.format("%s for %d minutes instead of %d%s.", 
				capitalize(state.getExercise().getFormPresent()),
				duration,
				originalDuration,
				weekendStr);
		
		String reminder = String.format("%s for %d minutes.",
				capitalize(state.getExercise().getFormPresent()),
				duration);
		
		ExerciseSolution solution = new ExerciseSolution(state);
		solution.setDuration(duration);
		solution.setMessage(message);
		solution.setReminder(reminder);
		
		return solution;
	}
	
	public static List<ExerciseSolution> getIncreaseFrequencySolutions(List<ExerciseState> states){
		List<ExerciseSolution> solutions = new ArrayList<ExerciseSolution>();
		
		for(ExerciseState state:states){
			if(state.isOnWeekdays() && state.isIncreaseDuration()){
				solutions.add(increaseFrequency(state, false));
			}
			
			if(state.isOnWeekends() && state.isWeekendIncreaseDuration()){
				solutions.add(increaseFrequency(state, true));
			}
		}
		
		return solutions;
	}
	
	private static ExerciseSolution increaseFrequency(ExerciseState state, boolean weekend){
		int originalFrequency = weekend?state.getWeekendFrequency():state.getFrequency();
		int frequency = originalFrequency; //10% increase
		frequency++;
		int max = weekend?2:5;
		frequency = Math.max(1, Math.min(MAX_FREQUENCY, frequency));
		String weekendStr = ExerciseQuestions.getInstance().getWeekendString(state, weekend);
		
		
		String message = String.format("%s on %d days instead of %d%s.", 
				capitalize(state.getExercise().getFormPresent()),
				frequency,
				originalFrequency,
				weekendStr);
		
		String reminder = String.format("%s on %d days.",
				capitalize(state.getExercise().getFormPresent()),
				frequency);
		
		ExerciseSolution solution = new ExerciseSolution(state);
		solution.setFrequency(frequency);
		solution.setMessage(message);
		solution.setReminder(reminder);
		
		return solution;
	}
	
	public static List<ExerciseSolution> getNewExerciseSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState state:states){
			if(!state.isExerciseLiked() || !state.isWeekendExerciseLiked()){
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
					
					String message = String.format("Instead of %s, try to %s.", state.getExercise().getName().toLowerCase(), s.getExercise().getFormPresent().toLowerCase());
					String reminder = String.format("Try %s", s.getExercise().getName().toLowerCase());
					
					s.setMessage(message);
					s.setReminder(reminder);
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
			
			if(state.isOnWeekdays()  && !state.isLocationLiked()){
				ExerciseSolution s = newLocation(state, false, prefs);
				if(s != null){
					solutionList.add(s);
				}
			}
			
			if(state.isOnWeekends() && !state.isWeekendLocationLiked()){
				ExerciseSolution s = newLocation(state, true, prefs);
				if(s != null){
					solutionList.add(s);
				}
			}
		}
		return solutionList;
	}
	
	private static ExerciseSolution newLocation(ExerciseState state, boolean weekend, SharedPreferences prefs){
		ExerciseLocation location = weekend?state.getWeekendLocation():state.getLocation();
		Log.d(TAG, "location="+location);
		ExerciseSolution solution = new ExerciseSolution(state);
		
		String[] ids = prefs.getString("profile_exercise_location", "").split(",");
		ArrayList<ExerciseLocation> liked = new ArrayList<ExerciseLocation>();
		for(String id:ids){
			try {
				liked.add(ExerciseLocationService.getInstance().getLocation(Integer.parseInt(id)));
			} catch(Exception e){
				
			}
		}
		List<ExerciseLocation> possible = ExerciseToLocationService.getInstance().getLocationListByExercise(state.getExercise().getId());					
		
		possible.remove(location);
		
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
			solution.setLocation(newLoc);
			
			String weekendStr = ExerciseQuestions.getInstance().getWeekendString(state, weekend);
			
			String message = String.format("Try to %s %s instead of %s%s.", 
					capitalize(solution.getExercise().getFormPresent()),
					solution.getLocation().getPreposition(),
					location.getPreposition(),
					weekendStr
					);
			
			String reminder = String.format("%s %s instead of %s%s.", 
					capitalize(solution.getExercise().getFormPresent()),
					solution.getLocation().getPreposition(),
					location.getPreposition(),
					weekendStr
					);
			
			
			
			solution.setMessage(message);
			solution.setReminder(reminder);
			
			return solution;
		} else {
			return null;
		}
		
		
	}
	
	public static List<ExerciseSolution> getNewTimeSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState state:states){
			if(state.isOnWeekdays() && !state.isTimeLiked()){
				ExerciseSolution s = newTime(state, false, prefs);
				if(s != null){
					solutionList.add(s);
				}
			}
			
			if(state.isOnWeekdays() && !state.isWeekendTimeLiked()){
				ExerciseSolution s = newTime(state, false, prefs);
				if(s != null){
					solutionList.add(s);
				}
			}
			
		}		
		return solutionList;
	}
	
	private static ExerciseSolution newTime(ExerciseState state, boolean weekend, SharedPreferences prefs){
		ExerciseTime time = weekend?state.getWeekendTime():state.getTime();
		ExerciseSolution s = new ExerciseSolution(state);
		
		String[] ids = prefs.getString("profile_exercise_when", "").split(",");
		ArrayList<ExerciseTime> liked = new ArrayList<ExerciseTime>();
		for(String id:ids){
			try{
				liked.add(ExerciseTimeService.getInstance().getExerciseTime(Integer.parseInt(id)));
			} catch(Exception e){}
		}
		liked.remove(time);
		
		ExerciseTime newTime = null;
		
		if(liked.size() > 0){
			newTime = liked.get(0);
		} else {
			List<ExerciseTime> allTimes = ExerciseTimeService.getInstance().getAllDataFromTable(); //pick a time that is 2 after the current one (warp around)
			newTime = allTimes.get((allTimes.indexOf(time)+2)%allTimes.size());
		
			s.setTime(newTime);
			
			String message = String.format("Try to %s %s in the %s instead of in the %s.",
					s.getExercise().getFormPresent(),
					s.getLocation().getPreposition(),
					s.getTime().getTime().toLowerCase(),
					time.getTime().toLowerCase());
			
			String reminder = String.format("%s in the %s", capitalize(s.getExercise().getFormPresent()), time.getTime().toLowerCase());
			
			s.setMessage(message);
			s.setReminder(reminder);	
		}			
		return s;
		
	}
	
	public static List<ExerciseSolution> getNewExerciseRecommendation(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		String[] ids = prefs.getString("profile_exercise_enjoy", "").split(",");
		String[] eqIds = prefs.getString("profile_exercise_equip", "").split(",");
		
		ArrayList<Exercise> liked = new ArrayList<Exercise>();
		for(String sId:ids){
			try {
				int id = Integer.parseInt(sId);
				liked.add(ExerciseService.getInstance().getExercise(id));				
			} catch(Exception e){}
			
		}
		
		ArrayList<Equipment> equip = new ArrayList<Equipment>();
		for(String sId:eqIds){
			try {
				int id = Integer.parseInt(sId);
				equip.add(Equipment.getEquipmentById(id));				
			} catch(Exception e){}
		}		
		
		for(ExerciseState state:states){ //remove exercises they are already doing
			liked.remove(state.getExercise());
		}
		
		HashMap<Exercise, Equipment> likedEquiped = new HashMap<Exercise, Equipment>();
		for(Exercise l:liked){
			for(Equipment eq:equip){
				for(int id:eq.getExercises()){
					if(l.getId() == id){
						if(!likedEquiped.containsKey(l)){
							likedEquiped.put(l, eq);
						}
					}
				}
			}
		}
		
		Exercise newExercise = null;
		Equipment newEquip = null;
		String message = null;
		String reminder = null;
		if(likedEquiped.size() > 0){
			Entry<Exercise, Equipment> entry = likedEquiped.entrySet().toArray(new Entry[]{})[((int)(Math.random()*likedEquiped.size()))];
			newExercise = entry.getKey();
			newEquip = entry.getValue();
			message = String.format("Consider starting to %s using your %s.", 
					newExercise.getFormPresent(),
					newEquip.getName().toLowerCase());
		} else if(liked.size() > 0){
			newExercise = liked.get((int)(Math.random()*liked.size()));
			message = String.format("Consider starting to %s.", newExercise.getFormPresent());
		}
		
		
		if(newExercise != null){

			reminder = newExercise.getFormPresent();
			ExerciseSolution solution = new ExerciseSolution(Solution.TYPE_DEFAULT, newExercise, null, null, 2, 30, message, reminder, 0);
			
			solutionList.add(solution);
		}
		
		return solutionList;
		
	}
	
	public static List<ExerciseSolution> getBoredomSolutions(List<ExerciseState> states, Context ctx){
		ArrayList<ExerciseSolution> solutionList = new ArrayList<ExerciseSolution>();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		int groupPreference = -1;
		try{
			Integer.parseInt(prefs.getString("profile_exercise_who", "-1"));
		}catch(Exception e){}
		Log.d(TAG, "Group Preference: "+groupPreference);
		
		for(ExerciseState state:states){

			String message = "";
			
			switch(groupPreference){
				case 4:
					if(state.getLocation().getLocationType().equals("Outdoor")){
						message = String.format("Try listening to some music while you %s.",
								state.getExercise().getFormPresent());
					} else {
						message = String.format("Try watching some T.V. while you %s %s.", 
								state.getExercise().getFormPresent(),
								state.getLocation().getPreposition());
					}
					break;
				case 2:
					message = String.format("Try to find a friend to %s with.",
							state.getExercise().getFormPresent());
					break;
				default:
					message = String.format("Try to find a group of friends to %s with.",
							state.getExercise().getFormPresent());
					break;					
			}
			
			ExerciseSolution solution = new ExerciseSolution(state);
			solution.setMessage(message);
			solution.setReminder(message);
			solutionList.add(solution);
			
		}
		
		return solutionList;
	}
	
	
	private List<ExerciseLocation> getAllLikedLocations(Context ctx){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		String[] ids = prefs.getString("profile_exercise_location", "").split(",");
		ArrayList<ExerciseLocation> liked = new ArrayList<ExerciseLocation>();
		for(String id:ids){
			try {
				liked.add(ExerciseLocationService.getInstance().getLocation(Integer.parseInt(id)));
			} catch(Exception e){
				
			}
		}
		return liked;
	}
	
	private List<ExerciseTime> getAllLikedTimes(Context ctx){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		String[] ids = prefs.getString("profile_exercise_when", "").split(",");
		ArrayList<ExerciseTime> liked = new ArrayList<ExerciseTime>();
		for(String id:ids){
			try{
				liked.add(ExerciseTimeService.getInstance().getExerciseTime(Integer.parseInt(id)));
			} catch(Exception e){}
		}
		return liked;
	}
	
	private static String capitalize(String s){
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

}
