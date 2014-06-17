package edu.wpi.smartcoach.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.util.Log;
import android.util.StateSet;
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

public class BaseProblemSolver implements ProblemSolver {
	
	private static final String TAG = BaseProblemSolver.class.getSimpleName();
	
	public static final int YES = 1;
	public static final int NO = 0;
	
	public static final int EXERCISE = 0;
	public static final int TIME = 1;
	public static final int LOCATION = 2;
	
	protected ArrayList<Exercise> exercises;
	protected HashMap<Exercise, ArrayList<QuestionModel>> questions;
	protected HashMap<Exercise, ExerciseState> state;

	protected boolean exercisesSubmitted = false;
	protected Exercise current = null;
	protected int questionIndex = 0;
	protected QuestionModel currentQuestion = null;
	
	private ArrayList<String> clearIDs = new ArrayList<String>(){{
		add("like");
		add("increase");
		add("aspect");
		add("location_we");	
	}};
	
	private boolean done = false;
	
	public BaseProblemSolver(){
		init();
	}
	
	public void init(){
		exercises = new ArrayList<Exercise>();
		questions = new HashMap<Exercise, ArrayList<QuestionModel>>();
		state = new HashMap<Exercise, ExerciseState>();
		
		exercisesSubmitted = false;
		questionIndex = 0;
		done = false;
	}
	
	@Override
	public QuestionModel getNextQuestion() {
		QuestionModel next;
		if (!exercisesSubmitted) {
			
			next = ExerciseQuestionBuilder.getExerciseListQuestion();

		} else {
			
			next = currentQuestion;
		}
		return next;
	}

	@Override
	public void submitResponse(QuestionModel response) {
		
		boolean lastQuestion = false;
		
		if (!exercisesSubmitted) {
			exercisesSubmitted = true;
			
			List<OptionModel> responseList = ((OptionQuestionModel)response).getSelectedResponses();
			for (OptionModel op : responseList) {
				Exercise e = (Exercise)op.getValue();
				exercises.add(e);
				ExerciseState eState = new ExerciseState();
				eState.setExercise(e);
				state.put(e, eState);
				questions.put(e, ExerciseQuestionBuilder.buildBasicQuestionList(e));
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
				
				
			} else if(id.equals("frequency")){
				
				OptionQuestionModel freqResponse = (OptionQuestionModel)response;
				state.get(current).setFrequency(freqResponse.getSelectedResponse().getId());
				
			} else if (id.equals("duration")){
				
				TimeQuestionModel durationResponse = (TimeQuestionModel)response;
				state.get(current).setDuration(durationResponse.getResponse());
			} else if (id.equals("checkweekend")){

				OptionQuestionModel we = (OptionQuestionModel)response;		
				ExerciseState es = state.get(current);
				
				if(we.getSelectedResponse().getId() != YES){
					es.setWeekendDifferent(false);
					es.setWeekendLocation(es.getLocation());
					es.setWeekendTime(es.getTime());
					es.setWeekendDuration(es.getDuration());
					addLikeQuestion("like", es.getExercise(), es.getLocation(), es.getTime());
				} else {
					es.setWeekendDifferent(true);
					addWeekendQuestions();
				}
			
			} else if(id.equals("location_we")){
				
				OptionQuestionModel locationResponse =  (OptionQuestionModel)response;
				state.get(current).setWeekendLocation((ExerciseLocation)locationResponse.getSelectedResponse().getValue());
				
			} else if(id.equals("time_we")){
				
				OptionQuestionModel timeResponse = (OptionQuestionModel)response;
				state.get(current).setWeekendTime((ExerciseTime)timeResponse.getSelectedResponse().getValue());				
				
			}  else if (id.equals("duration_we")){
				
				TimeQuestionModel durationResponse = (TimeQuestionModel)response;
				state.get(current).setWeekendDuration(durationResponse.getResponse());
				
				ExerciseState es = state.get(current);
				addLikeQuestion("like",  es.getExercise(), es.getLocation(), es.getTime());
				
			} else if (id.equals("like")){
			
				OptionQuestionModel likeResponse = (OptionQuestionModel)response;
				boolean liked = likeResponse.getSelectedResponse().getId() == YES;
				state.get(current).setExerciseLiked(true);
				state.get(current).setLocationLiked(true);
				state.get(current).setTimeLiked(true);
				if(liked){
					addIncreaseExerciseQuestion();
				} else {
					addAspectQuestion("aspect");
				}
							
			} else if(id.equals("increase")){
				
				OptionQuestionModel increaseResponse = (OptionQuestionModel)response;
				boolean wouldIncrease = increaseResponse.getSelectedResponse().getId() == YES;
				state.get(current).setWouldIncrease(wouldIncrease);
				
				ExerciseState es = state.get(current);
				if(es.isWeekendDifferent() && (es.getWeekendLocation() != es.getLocation() || es.getWeekendTime() != es.getTime())){
					addLikeQuestion("like_we",  es.getExercise(), es.getWeekendLocation(), es.getWeekendTime());
				} else {
					lastQuestion = true;
				}
				
			} else if(id.equals("aspect")){
				
				int problem = ((OptionQuestionModel)response).getSelectedResponse().getId();
				
				state.get(current).setExerciseLiked(true);
				state.get(current).setLocationLiked(true);
				state.get(current).setTimeLiked(true);
				
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
				
				ExerciseState es = state.get(current);
				if(es.isWeekendDifferent() && (es.getWeekendLocation() != es.getLocation() || es.getWeekendTime() != es.getTime())){
					addLikeQuestion("like_we",  es.getExercise(), es.getWeekendLocation(), es.getWeekendTime());;
				} else {
					lastQuestion = true;
				}
				
			} else if (id.equals("like_we")){
				
				OptionQuestionModel likeResponse = (OptionQuestionModel)response;
				boolean liked = likeResponse.getSelectedResponse().getId() == YES;
				state.get(current).setWeekendExerciseLiked(true);
				state.get(current).setWeekendLocationLiked(true);
				state.get(current).setWeekendTimeLiked(true);
				if(!liked){
					addAspectQuestion("aspect_we");
				} else {
					lastQuestion = true;
				}
				
			} else if (id.equals("aspect_we")){
				int problem = ((OptionQuestionModel)response).getSelectedResponse().getId();
				
				state.get(current).setWeekendExerciseLiked(true);
				state.get(current).setWeekendLocationLiked(true);
				state.get(current).setWeekendTimeLiked(true);
				
				switch (problem) {
					case EXERCISE:
						state.get(current).setWeekendExerciseLiked(false);
						break;
					case TIME:
						state.get(current).setWeekendTimeLiked(false);
						break;
					case LOCATION:
						state.get(current).setWeekendLocationLiked(false);
						break;
					default:
						break;
				}
				lastQuestion = true;
			}
				
		}
		
		Log.d(TAG, "questionIndex="+questionIndex+", size="+questions.get(current).size());
		Log.d(TAG, questions.get(current).toString());
		if(lastQuestion){
			if(exercises.indexOf(current) < exercises.size()-1){
				current = exercises.get(exercises.indexOf(current)+1);
				questionIndex = 0;
			} else {
				done = true;
			}
		}
		
		if(!done){
			currentQuestion = questions.get(current).get(questionIndex);
			questionIndex++;
		}
	}
	
	@Override
	public void back(){
		String id = currentQuestion.getId();
		
		boolean clear = false;
		if(clearIDs.contains(id)){
			clear = true;
		}
		
		questionIndex = questions.get(current).indexOf(currentQuestion)-1;
		
		if(clear){
			int index = questions.get(current).indexOf(currentQuestion);
			while(questions.get(current).size() > index){
				questions.get(current).remove(index);
			}
		}
		
		if(questionIndex == -1){
			if(exercises.indexOf(current) == 0){
				init();
				return;
			} else {
				current = exercises.get(exercises.indexOf(current)-1);
				questionIndex = questions.get(current).size()-1;
			}
		}

		currentQuestion = questions.get(current).get(questionIndex);
		questionIndex++;
		
	}
	
	
	
	@Override
	public boolean isBackAllowed(){
		return exercisesSubmitted;
	}
	
	private void addWeekendQuestions() {
		questions.get(current).add(ExerciseQuestionBuilder.getLocationQuestion(current, true));
		questions.get(current).add(ExerciseQuestionBuilder.getTimeQuestion(current, true));
		questions.get(current).add(ExerciseQuestionBuilder.getDurationQuestion(current, true));
		
	}
	
	private void addLikeQuestion(String id, Exercise e, ExerciseLocation l, ExerciseTime t){
		Log.d(TAG, "adding like question");
		String prompt = String.format("Did you enjoy %s %s in the %s?", e.getName().toLowerCase(),
				l.getPreposition(), 
				t.getTime().toLowerCase());
		
		ArrayList<OptionModel> yesNoOptions = new ArrayList<OptionModel>();
		yesNoOptions.add(new SimpleOption(YES, "Yes"));
		yesNoOptions.add(new SimpleOption(NO, "No"));
		
		questions.get(current).add(
				new OptionQuestionModel(id, "Liked", prompt,
					yesNoOptions,
					QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT, false));	
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
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT, false));
	}

	private void addAspectQuestion(String id){
		Log.d(TAG, "adding aspect question");
		ExerciseState cState = state.get(current);
		ArrayList<OptionModel> options = new ArrayList<OptionModel>();
		options.add(new SimpleOption(EXERCISE, "Exercise"));
		options.add(new SimpleOption(TIME, "Time"));
		options.add(new SimpleOption(LOCATION, "Location"));
		
		String prompt = String.format("Which of the following was the problem with %s?", cState.getExercise().getName().toLowerCase());
		
		questions.get(current).add(new OptionQuestionModel(id, "Problem", prompt, 
				options,
				QuestionType.SINGLE, 1, OptionQuestionModel.NO_LIMIT, false));
	}
	
	@Override
	public boolean hasNextQuestion() {
		return !done;
	}
	
	@Override
	public QuestionModel getSolution(Context ctx) {
		return null;
	}
}
