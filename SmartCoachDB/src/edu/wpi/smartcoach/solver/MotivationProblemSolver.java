package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import edu.wpi.smartcoach.model.OptionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel;
import edu.wpi.smartcoach.model.OptionQuestionModel.QuestionType;
import edu.wpi.smartcoach.model.QuestionModel;
import edu.wpi.smartcoach.model.SimpleOption;
import edu.wpi.smartcoach.model.TimeQuestionModel;
import edu.wpi.smartcoach.model.exercise.Exercise;
import edu.wpi.smartcoach.model.exercise.ExerciseLocation;
import edu.wpi.smartcoach.model.exercise.ExerciseState;
import edu.wpi.smartcoach.model.exercise.ExerciseTime;
import edu.wpi.smartcoach.service.ExerciseLocationService;
import edu.wpi.smartcoach.service.ExerciseService;
import edu.wpi.smartcoach.service.ExerciseTimeService;
import edu.wpi.smartcoach.service.ExerciseToLocationService;

public class MotivationProblemSolver implements ProblemSolver {
	
	private static final String TAG = MotivationProblemSolver.class.getSimpleName();
	
	public static final int YES = 1;
	public static final int NO = 0;
	
	public static final int EXERCISE = 0;
	public static final int TIME = 1;
	public static final int LOCATION = 2;
	
	protected ArrayList<Exercise> exercises;
	protected HashMap<Exercise, Queue<QuestionModel>> questions;
	protected HashMap<Exercise, ExerciseState> state;

	protected boolean exercisesSubmitted = false;
	protected Exercise current = null;
	protected QuestionModel nextQuestion = null;
	
	public MotivationProblemSolver(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, Queue<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			ArrayList<SimpleOption> exerciseOptions = new ArrayList<SimpleOption>();
			List<Exercise> exerciseList = ExerciseService.getInstance().getAllDataFromTable();
			for(Exercise e:exerciseList){
				exerciseOptions.add(new SimpleOption(e.getId(), e));
			}
			next = new OptionQuestionModel("exercises", "Exercises",
					"Which exercises did you try to do?", exerciseOptions,
					QuestionType.MULTIPLE);

		} else {
			
			next = nextQuestion;
		}
		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			
			List<OptionModel> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (OptionModel op : responseList) {
				Exercise e = (Exercise)op.getValue();
				exercises.add(e);
				ExerciseState eState = new ExerciseState();
				eState.setExercise(e);
				state.put(e, eState);
				questions.put(e, ExerciseQuestionListBuilder.buildBasicQuestionList(e));
			}
			current = exercises.get(0);
		} else {
			String id = response.getId();
			if(id.equals("location")){
				OptionQuestionModel locationResponse =  (OptionQuestionModel)response;
				state.get(current).setLocation((ExerciseLocation)locationResponse.getSelectedResponse().getValue());
			} else if(id.equals("time")){
				OptionQuestionModel timeResponse = (OptionQuestionModel)response;
				state.get(current).setTime((ExerciseTime)timeResponse.getSelectedResponse().getValue());
				addLikeQuestion();
			} else if(id.equals("frequency")){
				OptionQuestionModel freqResponse = (OptionQuestionModel)response;
				state.get(current).setFrequency(freqResponse.getSelectedResponse().getId());
			} else if (id.equals("duration")){
				TimeQuestionModel durationResponse = (TimeQuestionModel)response;
				state.get(current).setDuration(durationResponse.getResponse());
			} else if (id.equals("like")){
				OptionQuestionModel likeResponse = (OptionQuestionModel)response;
				boolean liked = likeResponse.getSelectedResponse().getId() == YES;
				state.get(current).setExerciseLiked(true);
				state.get(current).setLocationLiked(true);
				state.get(current).setTimeLiked(true);
				if(liked){
					addIncreaseExerciseQuestion();
				} else {
					addAspectQuestion();
				}
			} else if(id.equals("increase")){
				OptionQuestionModel increaseResponse = (OptionQuestionModel)response;
				boolean wouldIncrease = increaseResponse.getSelectedResponse().getId() == YES;
				state.get(current).setWouldIncrease(wouldIncrease);
			} else if(id.equals("aspect")){
				int problem = ((OptionQuestionModel)response).getSelectedResponse().getId();
				switch (problem) {
					case EXERCISE:
						state.get(current).setExerciseLiked(false);
						break;
					case TIME:
						state.get(current).setTimeLiked(false);
						break;
					case LOCATION:
						state.get(current).setLocationLiked(false);
						break;
					default:
						break;
				}
			}
			
		}
		
		//Log.d(TAG, questions.get(current).size()+"  PEEK  "+ questions.get(current).peek().toString());
		if(questions.get(current).isEmpty()){
			if(exercises.indexOf(current)+1 < exercises.size()){
				current = exercises.get(exercises.indexOf(current)+1);
			} 
		}
		nextQuestion = questions.get(current).poll();	
		
	}
	
	private void addLikeQuestion(){
		ExerciseState cState = state.get(current);
		Log.d(TAG, "adding like question");
		String prompt = String.format("Did you enjoy %s %s in the %s", cState.getExercise().getName(),
				cState.getLocation().getSpecificLocation(), 
				cState.getTime().getTime().toLowerCase());
		
		ArrayList<OptionModel> yesNoOptions = new ArrayList<OptionModel>();
		yesNoOptions.add(new SimpleOption(YES, "Yes"));
		yesNoOptions.add(new SimpleOption(NO, "No"));
		
		questions.get(current).add(
				new OptionQuestionModel("like", "Liked", prompt,
					yesNoOptions,
					QuestionType.SINGLE));	
	}
	
	private void addIncreaseExerciseQuestion(){
		ExerciseState cState = state.get(current);
		Log.d(TAG, "adding increase question");
		String prompt = String.format("Would you consider %s more often?", cState.getExercise().getName().toLowerCase());

		ArrayList<OptionModel> yesNoOptions = new ArrayList<OptionModel>();
		yesNoOptions.add(new SimpleOption(YES, "Yes"));
		yesNoOptions.add(new SimpleOption(NO, "No"));
		
		questions.get(current).add(	new OptionQuestionModel("increase", "Increase", prompt,
				yesNoOptions,
				QuestionType.SINGLE));
	}

	private void addAspectQuestion(){
		Log.d(TAG, "adding aspect question");
		ExerciseState cState = state.get(current);
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		options.add(new SimpleOption(EXERCISE, "Exercise"));
		options.add(new SimpleOption(TIME, "Time"));
		options.add(new SimpleOption(LOCATION, "Location"));
		
		String prompt = String.format("Which of the following was the problem with %s?", cState.getExercise().getName().toLowerCase());
		
		questions.get(current).add(new OptionQuestionModel("aspect", "Problem", prompt, 
				options,
				QuestionType.SINGLE));
	}
	
	@Override
	public boolean hasNextQuestion() {
		if(!exercisesSubmitted){
			return true;
		} else {
		if(current == exercises.get(exercises.size()-1) && questions.get(current).isEmpty() && nextQuestion == null){
				return false;
			} else {
				return true;
			}
		}
	}

	
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		ArrayList<ExerciseState> solutions = new ArrayList<ExerciseState>();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		for(ExerciseState s:state.values()){
			if(s.isLiked()){
				if(s.wouldIncrease()){
					ExerciseState increaseTimeSolution = s.clone();
					increaseTimeSolution.setDuration((int)(s.getDuration()*1.1f));
					solutions.add(increaseTimeSolution);
				}
			} else {
				
				if(!s.isExerciseLiked()){
					
					String[] ids = prefs.getString("profile_exercise_enjoy", "").split(",");
					ArrayList<Exercise> liked = new ArrayList<Exercise>();
					for(String id:ids){
						liked.add(ExerciseService.getInstance().getExercise(Integer.parseInt(id)));
					}
					
					for(ExerciseState existing:state.values()){
						liked.remove(existing.getExercise());
					}
					
					
					if(liked.size() > 0){
						Exercise newExercise = liked.get((int)(Math.random()*liked.size()));
						ExerciseState newExerciseSolution = s.clone();
						newExerciseSolution.setExercise(newExercise);
					}
					
					
				} else if (!s.isLocationLiked()){
					String[] ids = prefs.getString("profile_exercise_location", "").split(",");
					ArrayList<ExerciseLocation> liked = new ArrayList<ExerciseLocation>();
					for(String id:ids){
						liked.add(ExerciseLocationService.getInstance().getLocation(Integer.parseInt(id)));
					}
					List<ExerciseLocation> possible = ExerciseToLocationService.getInstance().getLocationListByExercise(s.getExercise().getId());					
					possible.remove(s.getLocation());
					ArrayList<ExerciseLocation> intersect = new ArrayList<ExerciseLocation>();
					for(ExerciseLocation l:liked){
						if(possible.contains(l)){
							intersect.add(l);
						}
					}
					intersect.remove(s.getLocation()); //remove current one
					
					ExerciseLocation newLoc = null;
					
					if(intersect.size() > 0){//get a liked location if possible
						newLoc = intersect.get(0);
					} else if(possible.size() > 0) {
						newLoc = possible.get((int)(Math.random()*possible.size()));//get the next possible location
					}
					
					if(newLoc != null){
						ExerciseState locationSolution = s.clone();
						locationSolution.setLocation(newLoc);
						solutions.add(locationSolution);
					}
					
				} else if (!s.isTimeLiked()){
					
					String[] ids = prefs.getString("profile_exercise_when", "").split(",");
					ArrayList<ExerciseTime> liked = new ArrayList<ExerciseTime>();
					for(String id:ids){
						liked.add(ExerciseTimeService.getInstance().getExerciseTime(Integer.parseInt(id)));
					}
					liked.remove(s.getTime());
					ExerciseTime newTime = null;
					if(liked.size() > 0){
						newTime = liked.get(0);
					} else {
						List<ExerciseTime> allTimes = ExerciseTimeService.getInstance().getAllDataFromTable();
						newTime = allTimes.get((allTimes.indexOf(s.getTime())+2)%allTimes.size());
					}
					
					ExerciseState timeSolution = s.clone();
					timeSolution.setTime(newTime);
					solutions.add(timeSolution);
					
				}
			}
		}
		
		
		
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		
		for(ExerciseState soln:solutions){
			options.add(new SimpleOption(solutions.indexOf(soln), soln));
		}
		
		if(options.isEmpty()){
			options.add(new SimpleOption(0, "No Solutions found..."));
		}
		
		return new OptionQuestionModel("solutions", "Solutions", "Here are some things you can try:", 
				options, QuestionType.SINGLE);
	}


}
